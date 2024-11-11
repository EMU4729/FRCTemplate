// Originally from https://github.com/REVrobotics/MAXSwerve-Java-Template/blob/main/src/main/java/frc/robot/subsystems/DriveSubsystem.java

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Twist2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.ADIS16470_IMUSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.utils.SwerveModule;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SwerveDriveSub extends SubsystemBase {
  // Swerve Modules
  public final SwerveModule frontLeft = new SwerveModule(
      SwerveDriveConstants.FRONT_LEFT_DRIVING_CAN_ID,
      SwerveDriveConstants.FRONT_LEFT_TURNING_CAN_ID);

  private final SwerveModule frontRight = new SwerveModule(
      SwerveDriveConstants.FRONT_RIGHT_DRIVING_CAN_ID,
      SwerveDriveConstants.FRONT_RIGHT_TURNING_CAN_ID);

  private final SwerveModule backLeft = new SwerveModule(
      SwerveDriveConstants.BACK_LEFT_DRIVING_CAN_ID,
      SwerveDriveConstants.BACK_LEFT_TURNING_CAN_ID);

  private final SwerveModule backRight = new SwerveModule(
      SwerveDriveConstants.BACK_RIGHT_DRIVING_CAN_ID,
      SwerveDriveConstants.BACK_RIGHT_TURNING_CAN_ID);

  private final ADIS16470_IMU imu = new ADIS16470_IMU();
  private final ADIS16470_IMUSim imuSim = new ADIS16470_IMUSim(imu);

  private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(SwerveDriveConstants.DRIVE_KINEMATICS,
      getRotation2d(), getModulePositions());

  private final Field2d field = new Field2d();

  private Pose2d poseSim = new Pose2d();

  /** Creates a new DriveSubsystem. */
  public SwerveDriveSub() {
    zeroHeading();
    SmartDashboard.putData("Field", field);
  }

  @Override
  public void periodic() {
    updateOdometry();
  }

  /**
   * Sets the wheels into an X formation to prevent movement.
   */
  public void setX() {
    frontLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
    frontRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    backLeft.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(-45)));
    backRight.setDesiredState(new SwerveModuleState(0, Rotation2d.fromDegrees(45)));
  }

  /**
   * Sets the swerve ModuleStates.
   *
   * @param desiredStates The desired SwerveModule states.
   */
  public void setModuleStates(SwerveModuleState[] desiredStates) {
    SwerveDriveKinematics.desaturateWheelSpeeds(
        desiredStates, SwerveDriveConstants.MAX_SPEED);
    frontLeft.setDesiredState(desiredStates[0]);
    frontRight.setDesiredState(desiredStates[1]);
    backLeft.setDesiredState(desiredStates[2]);
    backRight.setDesiredState(desiredStates[3]);
  }

  public SwerveModuleState[] getModuleStates() {
    return new SwerveModuleState[] {
        frontLeft.getState(),
        frontRight.getState(),
        backLeft.getState(),
        backRight.getState()
    };
  }

  public SwerveModuleState[] getModuleDesiredStates() {
    return new SwerveModuleState[] {
        frontLeft.getDesiredState(),
        frontRight.getDesiredState(),
        backLeft.getDesiredState(),
        backRight.getDesiredState()
    };
  }

  public SwerveModulePosition[] getModulePositions() {
    return new SwerveModulePosition[] {
        frontLeft.getPosition(),
        frontRight.getPosition(),
        backLeft.getPosition(),
        backRight.getPosition()
    };
  }

  /** Resets the drive encoders to currently read a position of 0. */
  public void resetEncoders() {
    frontLeft.resetEncoders();
    backLeft.resetEncoders();
    frontRight.resetEncoders();
    backRight.resetEncoders();
  }

  /** Zeroes the heading of the robot. */
  public void zeroHeading() {
    imu.reset();
  }

  /** @return the heading of the robot, in degrees */
  public double getHeading() {
    return imu.getAngle();
  }

  /** @return the heading of the robot as a {@link Rotation2d} */
  public Rotation2d getRotation2d() {
    return Rotation2d.fromDegrees(getHeading());
  }

  public void stopModules() {
    frontLeft.stop();
    frontRight.stop();
    backLeft.stop();
    backRight.stop();
  }

  public void updateOdometry() {
    odometry.update(getRotation2d(), getModulePositions());
    field.setRobotPose(getPose());
  }

  public Pose2d getPose() {
    return RobotBase.isSimulation() ? poseSim : odometry.getPoseMeters();
  }

  @Override
  public void simulationPeriodic() {
    final var simSpeeds = SwerveDriveConstants.DRIVE_KINEMATICS.toChassisSpeeds(getModuleDesiredStates());

    final var gyroRateDegPerSec = simSpeeds.omegaRadiansPerSecond * (180 / Math.PI);

    // TODO: fix swerve simulation
    // right now translation works fine, but the imu isn't being updated for some
    // reason, which breaks field-relative drive

    // why no work? shouldn't setting imuSim affect imu?
    imuSim.setGyroAngleZ(getHeading() + 0.02 * gyroRateDegPerSec);
    imuSim.setGyroRateZ(gyroRateDegPerSec);
    // imu.setGyroAngleZ(getHeading() + 0.02 * gyroRateDegPerSec);
    // System.out.println(getHeading());

    poseSim = poseSim.exp(
        new Twist2d(
            simSpeeds.vxMetersPerSecond,
            simSpeeds.vyMetersPerSecond,
            simSpeeds.omegaRadiansPerSecond));
  }
}