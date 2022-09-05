// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;
import java.util.Optional;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import frc.robot.utils.MotorInfo;

/**
 * Constants - use this class to store any port ids, file paths, or basically
 * anything that will not change.
 */
public final class Constants {
        private static Optional<Constants> inst = Optional.empty();

        private Constants() {
        }

        public static Constants getInstance() {
                if (!inst.isPresent()) {
                        inst = Optional.of(new Constants());
                }
                return inst.get();
        }

        // Envars
        public final Map<String, String> env = System.getenv();

        // Drive
        /**
         * Information for left master drive [Port,controller type,
         * {invert,brake,connectionSaftey}]
         */
        public final MotorInfo DRIVE_MOTOR_ID_LM = new MotorInfo(4, MotorInfo.Type.TalonSRX)
                        .withSafety().encoder(new int[] { 0, 1 }, 60.078 / 256. / 1000);
        /**
         * Information for right master drive [Port,controller type,
         * {invert,brake,connectionSaftey}]
         */
        public final MotorInfo DRIVE_MOTOR_ID_RM = new MotorInfo(1, MotorInfo.Type.TalonSRX)
                        .withInvert().withSafety().encoder(new int[] { 2, 3 }, 59.883 / 256. / 1000);
        /**
         * Information for left slave drive [Port,controller type,
         * {invert,brake,connectionSaftey}]
         */
        public final MotorInfo DRIVE_MOTOR_ID_LS = new MotorInfo(5, MotorInfo.Type.TalonSRX)
                        .withSafety();
        /**
         * Information for right slave drive [Port,controller type,
         * {invert,brake,connectionSaftey}]
         */
        public final MotorInfo DRIVE_MOTOR_ID_RS = new MotorInfo(2, MotorInfo.Type.TalonSRX)
                        .withInvert().withSafety();

        // Drive Simulation Constants
        public final double DRIVESIM_TRACK_WIDTH_METERS = 0.69;
        public final DifferentialDriveKinematics DRIVESIM_KINEMATICS = new DifferentialDriveKinematics(
                        DRIVESIM_TRACK_WIDTH_METERS);
        public final double DRIVESIM_WHEEL_DIAMETER_METERS = 0.15;

        public final double DRIVESIM_VOLTS = 0.22;
        public final double DRIVESIM_V_VOLT_SECONDS_PER_METER = 1.98;
        public final double DRIVESIM_A_VOLT_SECONDS_SQUARED_PER_METER = 0.2;

        public final double DRIVESIM_V_VOLT_SECONDS_PER_RADIAN = 1.5;
        public final double DRIVESIM_A_VOLT_SECONDS_SQUARED_PER_RADIAN = 0.3;

        public final LinearSystem<N2, N2, N2> DRIVESIM_DRIVETRAIN_PLANT = LinearSystemId.identifyDrivetrainSystem(
                        DRIVESIM_V_VOLT_SECONDS_PER_METER,
                        DRIVESIM_A_VOLT_SECONDS_SQUARED_PER_METER,
                        DRIVESIM_V_VOLT_SECONDS_PER_RADIAN,
                        DRIVESIM_A_VOLT_SECONDS_SQUARED_PER_RADIAN);

        public final DCMotor DRIVESIM_GEARBOX = DCMotor.getCIM(2);
        public final double DRIVESIM_GEARING = 8;

        // Turret
        /** Limit switch for turret slew angle */
        public final int TURRET_SLEW_LIMIT = -1;
        /** Limit switch for turret slew angle */
        public final int TURRET_HOOD_LIMIT = -1;
        /**
         * Information for turret slew motor [Port,controller type,
         * {invert,brake,connectionSaftey}, Encoder]
         */
        public final MotorInfo TURRET_SLEW_MOTOR_ID = new MotorInfo(-1, MotorInfo.Type.Never).withBrake()
                        .encoder(new int[] { -1, -1 }, -1);
        /**
         * Information for turret slew motor [Port,controller type,
         * {invert,brake,connectionSaftey}, Encoder]
         */
        public final MotorInfo TURRET_HOOD_MOTOR_ID = new MotorInfo(-1, MotorInfo.Type.Never).withBrake()
                        .encoder(new int[] { -1, -1 }, -1);
        /** min max degree range for turret slew */
        public final double[] TURRET_SLEW_RANGE = { 0, 270 };
        /** min max degree range for turret hood */
        public final double[] TURRET_HOOD_RANGE = { 0, 0 };
        /** throttle limits for turret slew {>30,>10,<10} */
        public final double[] TURRET_SLEW_THROT_LIMS = { 0.5, 0.2, 0.05 };
        /** throttle limits for turret hood */
        public final double[] TURRET_HOOD_THROT_LIMS = { 0.5, 0.2, 0.05 };

        // Test
        /**
         * Information for turret test motor [Port,controller type,
         * {invert,brake,connectionSaftey}, Encoder]
         */
        public final MotorInfo TEST_1_MOTOR_ID = new MotorInfo(6, MotorInfo.Type.VictorSPX).withBrake();
        /**
         * Information for turret test motor [Port,controller type,
         * {invert,brake,connectionSaftey}, Encoder]
         */
        public final MotorInfo TEST_2_MOTOR_ID = new MotorInfo(7, MotorInfo.Type.VictorSPX).withBrake();

        // Controller
        /** Port Number for xbox controller input device */
        public final int DEVICE_PORT_XBOX_CONTROLLER = 0; // WORKING
        /** Threshold for triggering the controller right and left triggers */
        public final double CONTROLLER_TRIGGER_THRESHOLD = 0.5;
        /** deadband for controller axies either side of 0 */
        public final double CONTROLLER_AXIS_DEADZONE = 0.1;

        // File Paths
        /** file path header for files on usb storage */
        public final String[] PATH_USB = { "u//", "v//" };
        /** file path header for files on internal storage */
        public final String PATH_INTERNAL = env.get("HOME");
        /** file name for default auto @deprecated */
        public final String PATH_AUTO_FILE_NAME = "autoCommands.txt";

        // Error Retry Limit
        /** limit for repeated attempts to create log file on USB storage */
        public final int REPEAT_LIMIT_LOGGER_CREATION = 10;
        /** limit for repeated attempts to read auto from internal storage */
        public final int REPEAT_LIMIT_AUTO_READ = 10;
        /** save attempts per second for the logger */
        public int LOGGER_SAVE_RATE = 10;

        // Robot features
        /** distance between wheel center side to side (m) */
        public final double robotWheelWidth = 0.870;

        // Auto Straight PID Constants
        /** Proportional constant for driving straight during auto */
        public double AUTO_STRAIGHT_KP = 0.2; // UPDATE
        /** Integral constant for driving straight during auto */
        public double AUTO_STRAIGHT_KI = 0.8; // UPDATE
        /** Derivative constant for driving straight during auto */
        public double AUTO_STRAIGHT_KD = 0.0; // UPDATE

        // Teleop Throttle PID Constants
        /** Proportional constant for throttle during teleop */
        public double TELEOP_THROTTLE_KP = 0.2; // UPDATE
        /** Integral constant for throttle during teleop */
        public double TELEOP_THROTTLE_KI = 0.0; // UPDATE
        /** Derivative constant for throttle during teleop */
        public double TELEOP_THROTTLE_KD = 0.8; // UPDATE
        /** Proportional constant for steering during teleop */
        public double TELEOP_STEERING_KP = 0.2; // UPDATE
        /** Integral constant for steering during teleop */
        public double TELEOP_STEERING_KI = 0.0; // UPDATE
        /** Derivative constant for steering during teleop */
        public double TELEOP_STEERING_KD = 0.8; // UPDATE
        /** Encoder max rate for PID loop */
        public double DRIVE_ENCODER_MAX_RATE = 1; // UPDATE
}
