// Originally from https://github.com/REVrobotics/MAXSwerve-Java-Template/blob/main/src/main/java/frc/robot/Constants.java

package frc.robot.constants;

import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;

public class SwerveDriveConstants {
  protected SwerveDriveConstants() {
  }

  // NEO Motor Constants
  public final double kFreeSpeedRpm = 5676;

  // Drive Constants

  // Driving Parameters - Note that these are not the maximum capable speeds of
  // the robot, rather the allowed maximum speeds
  public final double kMaxSpeedMetersPerSecond = 4.8;
  public final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

  public final double kDirectionSlewRate = 1.2; // radians per second
  public final double kMagnitudeSlewRate = 1.8; // percent per second (1 = 100%)
  public final double kRotationalSlewRate = 2.0; // percent per second (1 = 100%)

  // Chassis configuration
  public final double kTrackWidth = Units.inchesToMeters(26.5);
  // Distance between centers of right and left wheels on robot
  public final double kWheelBase = Units.inchesToMeters(26.5);
  // Distance between front and back wheels on robot
  public final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      new Translation2d(kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
      new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

  // Angular offsets of the modules relative to the chassis in radians
  public final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
  public final double kFrontRightChassisAngularOffset = 0;
  public final double kBackLeftChassisAngularOffset = Math.PI;
  public final double kBackRightChassisAngularOffset = Math.PI / 2;

  // SPARK MAX CAN IDs
  public final int kFrontLeftDrivingCanId = 11;
  public final int kRearLeftDrivingCanId = 13;
  public final int kFrontRightDrivingCanId = 15;
  public final int kRearRightDrivingCanId = 17;

  public final int kFrontLeftTurningCanId = 10;
  public final int kRearLeftTurningCanId = 12;
  public final int kFrontRightTurningCanId = 14;
  public final int kRearRightTurningCanId = 16;

  public final boolean kGyroReversed = false;

  // Module Constants

  // The MAXSwerve module can be configured with one of three pinion gears: 12T,
  // 13T, or 14T.
  // This changes the drive speed of the module (a pinion gear with more teeth
  // will result in a
  // robot that drives faster).
  public final int kDrivingMotorPinionTeeth = 14;

  // Invert the turning encoder, since the output shaft rotates in the opposite
  // direction of
  // the steering motor in the MAXSwerve Module.
  public final boolean kTurningEncoderInverted = true;

  // Calculations required for driving motor conversion factors and feed forward
  public final double kDrivingMotorFreeSpeedRps = kFreeSpeedRpm / 60;
  public final double kWheelDiameterMeters = 0.0762;
  public final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
  // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
  // teeth on the bevel pinion
  public final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
  public final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
      / kDrivingMotorReduction;

  public final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
      / kDrivingMotorReduction; // meters
  public final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
      / kDrivingMotorReduction) / 60.0; // meters per second

  public final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
  public final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

  public final double kTurningEncoderPositionPIDMinInput = 0; // radians
  public final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

  public final double kDrivingP = 0.04;
  public final double kDrivingI = 0;
  public final double kDrivingD = 0;
  public final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
  public final double kDrivingMinOutput = -1;
  public final double kDrivingMaxOutput = 1;

  public final double kTurningP = 1;
  public final double kTurningI = 0;
  public final double kTurningD = 0;
  public final double kTurningFF = 0;
  public final double kTurningMinOutput = -1;
  public final double kTurningMaxOutput = 1;

  public final IdleMode kDrivingMotorIdleMode = IdleMode.kBrake;
  public final IdleMode kTurningMotorIdleMode = IdleMode.kBrake;

  public final int kDrivingMotorCurrentLimit = 50; // amps
  public final int kTurningMotorCurrentLimit = 20; // amps

}
