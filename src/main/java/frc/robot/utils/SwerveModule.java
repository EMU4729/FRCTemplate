// Originally from https://github.com/REVrobotics/MAXSwerve-Java-Template/blob/main/src/main/java/frc/robot/subsystems/MAXSwerveModule.java

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.SparkAbsoluteEncoder.Type;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import frc.robot.Subsystems;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.utils.logger.Logger;

public class SwerveModule {
  private final TalonFX driveMotor;
  private final CANSparkMax turnMotor;

  private final AbsoluteEncoder turnEncoder;

  private final VelocityVoltage driveController;
  private final SparkPIDController turnController;

  private int lastOptimise = 0;

  private boolean isFlipped = false;

  private Rotation2d angularOffset = new Rotation2d(0); // radians
  private SwerveModuleState desiredState = new SwerveModuleState(0.0, new Rotation2d());

  private Logger logger;

  /**
   * Constructs a new SwerveModule for a MAX Swerve Module housing a Falcon
   * driving motor and a Neo 550 Turning Motor.
   * 
   * @param drivingCANId  CAN ID for the driving motor
   * @param turningCANId  CAN ID for the turning motor
   * @param angularOffset Angular offset of the module in radians.
   */
  public SwerveModule(int drivingCANId, int turningCANId, double angularOffset) {

    // create loggger
    logger = new Logger("swerve-" + drivingCANId, new String[] { "Pos", "Vel", "Ang", "Ang Rate" });

    driveMotor = new TalonFX(drivingCANId);
    turnMotor = new CANSparkMax(turningCANId, MotorType.kBrushless);
    this.angularOffset = Rotation2d.fromRadians(angularOffset);

    // Configure Drive Motor
    final var driveMotorConfig = new TalonFXConfiguration();
    /* Motor Inverts and Neutral Mode */
    driveMotorConfig.MotorOutput.Inverted = SwerveDriveConstants.DRIVE_MOTOR_INVERTED;
    driveMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

    /* Gear Ratio Config */
    driveMotorConfig.Feedback.SensorToMechanismRatio = SwerveDriveConstants.DRIVE_GEAR_RATIO;

    /* Current Limiting */
    driveMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    driveMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;

    /* PID Config */
    driveMotorConfig.Slot0.kP = SwerveDriveConstants.DRIVE_P;
    driveMotorConfig.Slot0.kI = SwerveDriveConstants.DRIVE_I;
    driveMotorConfig.Slot0.kD = SwerveDriveConstants.DRIVE_D;

    driveMotor.getConfigurator().apply(driveMotorConfig);
    driveController = new VelocityVoltage(0).withSlot(0);

    // Configure Turn Motor
    turnMotor.restoreFactoryDefaults();

    // Setup encoders and PID controllers for the driving and turning SPARKS MAX.
    turnEncoder = turnMotor.getAbsoluteEncoder(Type.kDutyCycle);
    turnController = turnMotor.getPIDController();
    turnController.setFeedbackDevice(turnEncoder);

    // Apply position and velocity conversion factors for the turning encoder. We
    // want these in radians and radians per second to use with WPILib's swerve
    // APIs.
    turnEncoder.setPositionConversionFactor(SwerveDriveConstants.TURNING_ENCODER_POSITION_FACTOR);
    turnEncoder.setVelocityConversionFactor(SwerveDriveConstants.TURNING_ENCODER_VELOCITY_FACTOR);
    // turnEncoder.setZeroOffset(angularOffset); dont use

    // Invert the turning encoder, since the output shaft rotates in the opposite
    // direction of
    // the steering motor in the MAXSwerve Module.
    turnEncoder.setInverted(SwerveDriveConstants.TURNING_ENCODER_INVERTED);

    // Enable PID wrap around for the turning motor. This will allow the PID
    // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
    // to 10 degrees will go through 0 rather than the other direction which is a
    // longer route.
    turnController.setPositionPIDWrappingEnabled(true);
    turnController.setPositionPIDWrappingMinInput(SwerveDriveConstants.TURNING_ENCODER_POSITION_PID_MIN_INPUT);
    turnController.setPositionPIDWrappingMaxInput(SwerveDriveConstants.TURNING_ENCODER_POSITION_PID_MAX_INPUT);

    // Set the PID gains for the turning motor. Note these are example gains, and
    // you
    // may need to tune them for your own robot!
    turnController.setP(SwerveDriveConstants.TURNING_P);
    turnController.setI(SwerveDriveConstants.TURNING_I);
    turnController.setD(SwerveDriveConstants.TURNING_D);
    turnController.setFF(SwerveDriveConstants.TURNING_FF);
    turnController.setOutputRange(-1, 1);

    turnMotor.setIdleMode(IdleMode.kBrake);
    turnMotor.setSmartCurrentLimit(30);

    // Save the SPARK MAX configurations. If a SPARK MAX browns out during
    // operation, it will maintain the above configurations.
    turnMotor.burnFlash();

    desiredState.angle = new Rotation2d(turnEncoder.getPosition());
    driveMotor.setPosition(0);
  }

  /**
   * Returns the current state of the module.
   *
   * @return The current state of the module.
   */
  public SwerveModuleState getState() {
    // Apply chassis angular offset to the encoder position to get the position
    // relative to the chassis.
    return new SwerveModuleState(
        driveMotor.getVelocity().getValue(),
        getRotation2d());
  }

  /**
   * Returns the current desired state of the module
   * 
   * @return The current desired state of the module
   */
  public SwerveModuleState getDesiredState() {
    return desiredState;
  }

  /**
   * Returns the current position of the module.
   *
   * @return The current position of the module.
   */
  public SwerveModulePosition getPosition() {
    // Apply chassis angular offset to the encoder position to get the position
    // relative to the chassis.
    Rotation2d angle = getRotation2d().minus(angularOffset);
    // if the wheel is flipped, flip the returned angle too
    if (isFlipped) angle = angle.minus(Rotation2d.fromDegrees(180));
    return new SwerveModulePosition(Math.abs(driveMotor.getPosition().getValue()), angle);
  }

  /**
   * Sets the desired state for the module.
   *
   * @param desiredState Desired state with speed and angle.
   */
  public void setDesiredState(SwerveModuleState desiredState) {
    // Apply chassis angular offset to the desired state.
    SwerveModuleState correctedDesiredState = new SwerveModuleState();
    correctedDesiredState.speedMetersPerSecond = desiredState.speedMetersPerSecond;
    correctedDesiredState.angle = desiredState.angle.plus(angularOffset);

    // Optimize the reference state to avoid spinning further than 90 degrees.
    SwerveModuleState optimizedDesiredState = /* SwerveModuleState. */optimize(
        correctedDesiredState,
        getRotation2d());

    driveMotor.setControl(
        driveController
            .withVelocity(
                // withVelocity accepts rps, not mps
                optimizedDesiredState.speedMetersPerSecond / SwerveDriveConstants.WHEEL_CIRCUMFERENCE_METERS));// .withFeedForward(DriveConstants.DRIVING_FF));
    turnController.setReference(
        optimizedDesiredState.angle.getRadians() + Math.PI,
        ControlType.kPosition);

    this.desiredState = desiredState;

    logger.log(new double[] {
        driveMotor.getPosition().getValue(),
        driveMotor.getVelocity().getValue(),
        getRotation2d().getDegrees(),
        turnEncoder.getVelocity() * (180 / Math.PI)
    });

  }
  /*
  public SwerveModuleState optimize(SwerveModuleState desiredState, Rotation2d currentAngle) {
    var delta = desiredState.angle.minus(currentAngle);
    int limit = lastOptimise == 0 ? 90 : (lastOptimise > 0 ? 135 : 45);

    double error = Math.abs(delta.getDegrees());
    if (error < limit) {
      lastOptimise = error < 20 ? 0 : 1;
      return new SwerveModuleState(
          -desiredState.speedMetersPerSecond,
          desiredState.angle.rotateBy(Rotation2d.fromDegrees(180.0)));
    } else {
      lastOptimise = error > 160 ? 0 : -1;
      return new SwerveModuleState(desiredState.speedMetersPerSecond, desiredState.angle);
    }
  }*/

  private static final double intertia = 45;

  /* Optimises wheel angle, using an inertia based system */
  public SwerveModuleState optimize(SwerveModuleState desiredState, Rotation2d currentAngle) {
    var turnSpeed = 0;//Subsystems.swerveDrive.getTurnRate();
    double threshold = 0;//90 + MathUtil.clamp(turnSpeed * intertia, -80, 80);

    var delta = desiredState.angle.minus(currentAngle);
    if (delta.getDegrees() > threshold || delta.getDegrees() < 180-threshold) {
      isFlipped = true;
      return new SwerveModuleState(
          -desiredState.speedMetersPerSecond,
          desiredState.angle.rotateBy(Rotation2d.fromDegrees(180.0)));
    } else {
      isFlipped = false;
      return new SwerveModuleState(desiredState.speedMetersPerSecond, desiredState.angle);
    }
  }

  /** Zeroes the drive encoder. */
  public void resetEncoders() {
    driveMotor.setPosition(0);
  }

  /** reset turn motor pid I accumulation to 0 */
  public void resetIntegral() {
    turnController.setIAccum(0);
  }

  public Rotation2d getRotation2d() {
    // take care, get position only returns as rotations when a scale factor is not set
    return Rotation2d.fromRadians(turnEncoder.getPosition());
  }
}