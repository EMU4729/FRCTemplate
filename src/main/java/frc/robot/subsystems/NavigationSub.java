package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// import frc.robot.utils.logger.Logger;

public class NavigationSub extends SubsystemBase {
  private final Constants cnst = Constants.getInstance();

  public final ADIS16470_IMU imu = new ADIS16470_IMU();
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(
      Rotation2d.fromDegrees(imu.getAngle()));

  public final Encoder drvLeftEncoder = cnst.DRIVE_MOTOR_ID_LM.createEncoder();
  public final Encoder drvRightEncoder = cnst.DRIVE_MOTOR_ID_RM.createEncoder();

  /** m from start pos in x rel to start angle @WIP not implimented */
  public double xPos = 0;
  /** m from start pos in y rel to start angle @WIP not implimented */
  public double yPos = 0;
  /** m/s of speed */
  public double speed = 0;
  /** deg/s of rotation (CW = pos) */
  public double turn = 0;

  @Override
  public void periodic() {
    odometry.update(Rotation2d.fromDegrees(imu.getAngle()), drvLeftEncoder.getDistance(),
        drvRightEncoder.getDistance());
    speed = getCOMSpeed();
    turn = getTurnRate();
  }

  /**
   * Returns the currently-estimated pose of the robot.
   * 
   * @return The pose.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  /** Gets the left encoder rate. @return The speed. m/s */
  public double getLeftEncoderRate() {
    return drvLeftEncoder.getRate();
  }

  /** Gets the right encoder rate. @return The speed. m/s */
  public double getRightEncoderRate() {
    return drvRightEncoder.getRate();
  }

  /** Gets the average encoder rate. @return speed of COM. m/s */
  private double getCOMSpeed() {
    return (getLeftEncoderRate() + getRightEncoderRate()) / 2;
  }

  /** gets the average turn rate. @return rate of turn. deg/s */
  private double getTurnRate() {
    double vL = getLeftEncoderRate();
    double vR = getRightEncoderRate();

    if (vL == vR) {
      double centRad = (vL * cnst.robotWheelWidth) / (vR - vL) + 0.5 * cnst.robotWheelWidth;
      return Math.toDegrees(getCOMSpeed() / centRad);
    }
    return 0;
  }

  public void resetEncoders() {
    drvLeftEncoder.reset();
    drvRightEncoder.reset();
  }

  /**
   * Resets the odometry to the specified pose.
   * 
   * @param pose The pose.
   */
  public void resetOdometry(Pose2d pose) {
    // subsystems.drive2.resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  private double getHeading() {
    return 0;
  }
}
