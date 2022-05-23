package frc.robot.subsystems;

import frc.robot.Constants;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Navigation Subsystem.
 * Handles all navigation logic.
 */
public class NavigationSub extends SubsystemBase {
  private final Constants constants = Constants.getInstance();
  private final ADIS16470_IMU imu = new ADIS16470_IMU();
  private final Encoder leftEncoder = new Encoder(
      constants.DRIVE_ENCODER_PORT_LA, constants.DRIVE_ENCODER_PORT_LB);
  private final Encoder rightEncoder = new Encoder(
      constants.DRIVE_ENCODER_PORT_RA, constants.DRIVE_ENCODER_PORT_RB);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(
      Rotation2d.fromDegrees(getAngle()));

  @Override
  public void periodic() {
    odometry.update(
        Rotation2d.fromDegrees(getAngle()),
        leftEncoder.getDistance(), rightEncoder.getDistance());
  }

  /**
   * Set the odometry to a new pose.
   * 
   * @param newPose The new pose for odometry.
   */
  public void setOdometry(Pose2d newPose) {
    odometry.resetPosition(newPose, Rotation2d.fromDegrees(getAngle()));
  }

  /**
   * Gets the angle of the IMU.
   * 
   * @return the yaw angle of the IMU.
   */
  public double getAngle() {
    return imu.getAngle();
  }

  /**
   * Gets the rate from the left drive encoder.
   * 
   * @return The rate of the left drive encoder.
   */
  public double getLeftEncoderRate() {
    return leftEncoder.getRate();
  }

  /**
   * Gets the rate of the right drive encoder.
   * 
   * @return The rate of the right drive encoder.
   */
  public double getRightEncoderRate() {
    return rightEncoder.getRate();
  }

  /**
   * Gets the average rate of the drive encoders.
   * 
   * @return The average rate of the drive encoders.
   */
  public double getAverageEncoderRate() {
    return (getLeftEncoderRate() + getRightEncoderRate()) / 2;
  }

  /**
   * Gets the odometry of the robot
   * 
   * @return the odometry of the robot
   */
  public Pose2d getOdometry() {
    return odometry.getPoseMeters();
  }

  /**
   * Resets all navigation values to their defaults.
   */
  public void reset() {
    imu.reset();
    odometry.resetPosition(new Pose2d(), new Rotation2d());
  }
}
