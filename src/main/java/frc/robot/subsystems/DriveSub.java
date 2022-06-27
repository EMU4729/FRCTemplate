package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.simulation.ADIS16470_IMUSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.Clamper;

/**
 * Drive Subsystem.
 * Handles all drive functionality.
 */
public class DriveSub extends SubsystemBase {
  private final Constants constants = Constants.getInstance();

  private final WPI_TalonSRX leftMaster = new WPI_TalonSRX(constants.DRIVE_MOTOR_PORT_LM);
  private final WPI_TalonSRX leftSlave = new WPI_TalonSRX(constants.DRIVE_MOTOR_PORT_LS);
  private final Encoder leftEncoder = new Encoder(
      constants.DRIVE_ENCODER_PORT_LA, constants.DRIVE_ENCODER_PORT_LB);
  private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftMaster, leftSlave);

  private final WPI_TalonSRX rightMaster = new WPI_TalonSRX(constants.DRIVE_MOTOR_PORT_RM);
  private final WPI_TalonSRX rightSlave = new WPI_TalonSRX(constants.DRIVE_MOTOR_PORT_RS);
  private final Encoder rightEncoder = new Encoder(
      constants.DRIVE_ENCODER_PORT_RA, constants.DRIVE_ENCODER_PORT_RB);
  private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightMaster, rightSlave);

  private final ADIS16470_IMU imu = new ADIS16470_IMU();

  private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);
  private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(
      Rotation2d.fromDegrees(imu.getAngle()));

  private final DifferentialDrivetrainSim drivetrainSim;
  private final EncoderSim leftEncoderSim;
  private final EncoderSim rightEncoderSim;
  private final ADIS16470_IMUSim imuSim;
  private final Field2d fieldSim;

  public DriveSub() {
    if (RobotBase.isSimulation()) {
      drivetrainSim = new DifferentialDrivetrainSim(
          constants.DRIVESIM_DRIVETRAIN_PLANT,
          constants.DRIVESIM_GEARBOX,
          constants.DRIVESIM_GEARING,
          constants.DRIVESIM_TRACK_WIDTH_METERS,
          constants.DRIVESIM_WHEEL_DIAMETER_METERS / 2,
          VecBuilder.fill(0, 0, 0.0001, 0.1, 0.1, 0.005, 0.005));
      leftEncoderSim = new EncoderSim(leftEncoder);
      rightEncoderSim = new EncoderSim(rightEncoder);
      imuSim = new ADIS16470_IMUSim(imu);
      fieldSim = new Field2d();
      SmartDashboard.putData("Field", fieldSim);
    } else {
      drivetrainSim = null;
      leftEncoderSim = null;
      rightEncoderSim = null;
      imuSim = null;
      fieldSim = null;
    }
    addChild("Differential Drive", drive);
  }

  @Override
  public void periodic() {
    odometry.update(Rotation2d.fromDegrees(imu.getAngle()), leftEncoder.getDistance(), rightEncoder.getDistance());
  }

  @Override
  public void simulationPeriodic() {
    drivetrainSim.setInputs(
        leftMotors.get() * RobotController.getBatteryVoltage(),
        rightMotors.get() * RobotController.getBatteryVoltage());
    drivetrainSim.update(0.020);

    leftEncoderSim.setDistance(drivetrainSim.getLeftPositionMeters());
    leftEncoderSim.setRate(drivetrainSim.getLeftVelocityMetersPerSecond());
    rightEncoderSim.setDistance(drivetrainSim.getRightPositionMeters());
    rightEncoderSim.setRate(drivetrainSim.getRightVelocityMetersPerSecond());
    imuSim.setGyroAngleZ(-drivetrainSim.getHeading().getDegrees());

    fieldSim.setRobotPose(odometry.getPoseMeters());
  }

  /**
   * Returns the currently-estimated pose of the robot.
   * 
   * @return The pose.
   */
  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  /**
   * Resets the odometry to the specified pose.
   * 
   * @param pose The pose.
   */
  public void resetOdometry(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getHeading()));
  }

  /**
   * Gets the left encoder rate.
   * 
   * @return The distance.
   */
  public double getLeftEncoderRate() {
    return leftEncoder.getRate();
  }

  /**
   * Gets the right encoder rate.
   * 
   * @return The distance.
   */
  public double getRightEncoderRate() {
    return rightEncoder.getRate();
  }

  /**
   * Gets the average encoder rate.
   * 
   * @return The distance.
   */
  public double getAverageEncoderRate() {
    return (getLeftEncoderRate() + getRightEncoderRate()) / 2;
  }

  /**
   * Resets the encoders.
   */
  public void resetEncoders() {
    leftEncoder.reset();
    rightEncoder.reset();
  }

  /**
   * Zeroes out the heading (resets the IMU).
   */
  public void zeroHeading() {
    imu.reset();
  }

  /**
   * Gets the heading of the robot.
   * 
   * @return The heading in degrees.
   */
  public double getHeading() {
    return imu.getAngle();
  }

  /**
   * Activates tank drive. Similar to MoveTank from ev3dev.
   * 
   * @param leftSpeed  The left speed.
   * @param rightSpeed The right speed.
   */
  public void tank(double leftSpeed, double rightSpeed) {
    leftSpeed = Clamper.absUnit(leftSpeed);
    rightSpeed = Clamper.absUnit(rightSpeed);
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  /**
   * Activates arcade drive. Similar to MoveSteering from ev3dev.
   * 
   * @param speed    The speed
   * @param steering The steering
   */
  public void arcade(double speed, double steering) {
    speed = Clamper.absUnit(speed);
    steering = Clamper.absUnit(steering);
    drive.arcadeDrive(speed, steering);
  }

  /**
   * Stop all motors.
   */
  public void off() {
    drive.stopMotor();
  }
}
