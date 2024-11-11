// Originally from https://github.com/REVrobotics/MAXSwerve-Java-Template/blob/main/src/main/java/frc/robot/subsystems/MAXSwerveModule.java

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.utils;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkAbsoluteEncoder;
import com.revrobotics.SparkPIDController;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.constants.SwerveDriveConstants;

public class SwerveModule {
  private final TalonFX driveMotor;
  private final CANSparkMax turnMotor;

  private final SparkAbsoluteEncoder turnEncoder;

  private final VelocityVoltage driveController;
  private final SparkPIDController turnController;

  private SwerveModuleState desiredState = new SwerveModuleState();

  public SwerveModule(int driveMotorId, int turningMotorId) {
    driveMotor = new TalonFX(driveMotorId);
    turnMotor = new CANSparkMax(turningMotorId, MotorType.kBrushless);

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
    turnEncoder = turnMotor.getAbsoluteEncoder();
    turnController = turnMotor.getPIDController();
    turnController.setFeedbackDevice(turnEncoder);

    // Apply position and velocity conversion factors for the turning encoder. We
    // want these in radians and radians per second to use with WPILib's swerve
    // APIs.
    turnEncoder.setPositionConversionFactor(SwerveDriveConstants.TURNING_ENCODER_POSITION_FACTOR);
    turnEncoder.setVelocityConversionFactor(SwerveDriveConstants.TURNING_ENCODER_VELOCITY_FACTOR);

    // Invert the turning encoder, since the output shaft rotates in the opposite
    // direction of
    // the steering motor in the MAXSwerve Module.
    turnEncoder.setInverted(SwerveDriveConstants.TURNING_ENCODER_INVERTED);

    // Enable PID wrap around for the turning motor. This will allow the PID
    // controller to go through 0 to get to the setpoint i.e. going from 350 degrees
    // to 10 degrees will go through 0 rather than the other direction which is a
    // longer route.
    turnController.setPositionPIDWrappingEnabled(true);
    turnController.setPositionPIDWrappingMinInput(-Math.PI);
    turnController.setPositionPIDWrappingMaxInput(Math.PI);

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

    resetEncoders();

  }

  /** @return the module's drive motor position, in meters */
  public double getDrivePosition() {
    return driveMotor.getPosition().getValueAsDouble() * SwerveDriveConstants.WHEEL_CIRCUMFERENCE_METERS;
  }

  /** @return the module's drive motor velocity, in meters per second */
  public double getDriveVelocity() {
    return driveMotor.getVelocity().getValueAsDouble() * SwerveDriveConstants.WHEEL_CIRCUMFERENCE_METERS;
  }

  /** @return the module's turn motor position, in radians */
  public double getTurnPosition() {
    return turnEncoder.getPosition();
  }

  public Rotation2d getTurnRotation2d() {
    return new Rotation2d(getTurnPosition());
  }

  /** @return the module's turn motor velocity, in radians per second */
  public double getTurnVelocity() {
    return turnEncoder.getVelocity();
  }

  /** resets the drive encoder */
  public void resetEncoders() {
    driveMotor.setPosition(0);
  }

  public SwerveModuleState getState() {
    return new SwerveModuleState(getDriveVelocity(), getTurnRotation2d());
  }

  public SwerveModulePosition getPosition() {
    return new SwerveModulePosition(getDrivePosition(), getTurnRotation2d());
  }

  public void setDesiredState(SwerveModuleState state) {
    if (Math.abs(state.speedMetersPerSecond) < 0.001) {
      desiredState = state;
      stop();
      return;
    }

    state = SwerveModuleState.optimize(state, getState().angle);
    driveMotor.setControl(
        driveController.withVelocity(state.speedMetersPerSecond / SwerveDriveConstants.WHEEL_CIRCUMFERENCE_METERS));
    turnController.setReference(state.angle.getRadians(), ControlType.kPosition);
    desiredState = state;
  }

  public SwerveModuleState getDesiredState() {
    return desiredState;
  }

  public void stop() {
    driveMotor.set(0);
    turnMotor.set(0);
  }
}