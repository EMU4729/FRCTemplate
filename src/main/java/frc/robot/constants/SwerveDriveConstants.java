
package frc.robot.constants;

import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.robot.utils.RangeMath.RangeSettings;

public class SwerveDriveConstants {
  // NEO Motor Constants
  /** Free speed of the driving motor in rpm */
  public static final double FREE_SPEED_RPM = 6380;

  // Driving Parameters - Note that these are not the maximum capable speeds of
  // the robot, rather the allowed maximum speeds
  /** Max speed of robot in meters per second */
  public static final double MAX_SPEED = 2.0; // TODO check this
  /** Max acceleration of robot in meters per second squared */
  public static final double MAX_ACCELERATION = 1; // TODO check this
  /** Max angular speed of robot in radians per second */
  public static final double MAX_ANGULAR_SPEED = 2 * Math.PI;
  /** Max angular acceleration of robot in radians per second squared */
  public static final double MAX_ANGULAR_ACCELERATION = MAX_ANGULAR_SPEED / 60;

  /** Gear ratio of the MAX Swerve Module driving motor */
  public static final double DRIVE_GEAR_RATIO = 4.71;

  // Chassis configuration
  /** Distance between front and back wheel on robot in meters */
  public static final double TRACK_WIDTH = Units.inchesToMeters(20.7);
  /** Distance between centers of left and right wheels on robot in meters */
  public static final double WHEEL_BASE = Units.inchesToMeters(20.7);

  /**
   * Position of front left swerve module relative to robot center. Mainly for sim
   * purposes.
   */
  public static final Translation2d FRONT_LEFT_MODULE_TRANSLATION = new Translation2d(-TRACK_WIDTH / 2, WHEEL_BASE / 2);
  /**
   * Position of front right swerve module relative to robot center. Mainly for
   * sim purposes.
   */
  public static final Translation2d FRONT_RIGHT_MODULE_TRANSLATION = new Translation2d(TRACK_WIDTH / 2, WHEEL_BASE / 2);
  /**
   * Position of back left swerve module relative to robot center. Mainly for sim
   * purposes.
   */
  public static final Translation2d BACK_LEFT_MODULE_TRANSLATION = new Translation2d(-TRACK_WIDTH / 2, -WHEEL_BASE / 2);
  /**
   * Position of back right swerve module relative to robot center. Mainly for sim
   * purposes.
   */
  public static final Translation2d BACK_RIGHT_MODULE_TRANSLATION = new Translation2d(TRACK_WIDTH / 2, -WHEEL_BASE / 2);

  /** Swerve Kinematics */
  public static final SwerveDriveKinematics DRIVE_KINEMATICS = new SwerveDriveKinematics(
      FRONT_LEFT_MODULE_TRANSLATION,
      FRONT_RIGHT_MODULE_TRANSLATION,
      BACK_LEFT_MODULE_TRANSLATION,
      BACK_RIGHT_MODULE_TRANSLATION);

  // SPARK MAX CAN IDs
  /** CAN ID for Front Left Module Driving Motor */
  public static final int FRONT_LEFT_DRIVING_CAN_ID = 1;
  /** CAN ID for Front Right Module Driving Motor */
  public static final int FRONT_RIGHT_DRIVING_CAN_ID = 2;
  /** CAN ID for Back Left Module Driving Motor */
  public static final int BACK_LEFT_DRIVING_CAN_ID = 3;
  /** CAN ID for Back Right Module Driving Motor */
  public static final int BACK_RIGHT_DRIVING_CAN_ID = 4;

  /** CAN ID for Front Left Module Turning Motor */
  public static final int FRONT_LEFT_TURNING_CAN_ID = 1;
  /** CAN ID for Front Right Module Turning Motor */
  public static final int FRONT_RIGHT_TURNING_CAN_ID = 2;
  /** CAN ID for Back Left Module Turning Motor */
  public static final int BACK_LEFT_TURNING_CAN_ID = 3;
  /** CAN ID for Back Right Module Turning Motor */
  public static final int BACK_RIGHT_TURNING_CAN_ID = 4;

  /** IMU Gyro Inversion */
  public static final boolean GYRO_REVERSED = false;

  // Module Constants

  /** Drive motor inversion. */
  public static final InvertedValue DRIVE_MOTOR_INVERTED = InvertedValue.Clockwise_Positive;

  /**
   * Whether the turning encoder is inverted or not.
   * 
   * In the MAXSwerve module, this should be set to `true`, since the output shaft
   * rotates in the opposite direction of the steering motor.
   */
  public static final boolean TURNING_ENCODER_INVERTED = true;

  // Calculations required for driving motor conversion factors and feed forward
  /** Wheel diameter in meters */
  public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(3);
  /** Wheel circumference in meters */
  public static final double WHEEL_CIRCUMFERENCE_METERS = WHEEL_DIAMETER_METERS * Math.PI;

  /** Turning encoder position factor */
  public static final double TURNING_ENCODER_POSITION_FACTOR = (2 * Math.PI); // radians
  /** Turning encoder velocity factor */
  public static final double TURNING_ENCODER_VELOCITY_FACTOR = TURNING_ENCODER_POSITION_FACTOR / 60.0; // rad/s

  // TODO tune PID
  public static final double DRIVE_P = 0.7;
  public static final double DRIVE_I = 0.0;// 5;
  public static final double DRIVE_D = 0.05;

  public static final double TURNING_P = 0.25;
  public static final double TURNING_I = 0.001;
  public static final double TURNING_D = 0.02;
  public static final double TURNING_FF = 0;// .1;

  public static RangeSettings PILOT_SETTINGS = RangeSettings.InitSwerveBot(0, 1, 4, 0.1, true,
      0, 1, 4, 0.1, false,
      0, 1, 3, 0.1, false,
      0.85);

  public static RangeSettings PILOT_DEMO_SETTINGS = RangeSettings.InitSwerveBot(0, 0.2, 1, 0.1, true,
      0, 0.2, 1, 0.1, false,
      0, 0.2, 1, 0.1, false,
      0.6);

}
