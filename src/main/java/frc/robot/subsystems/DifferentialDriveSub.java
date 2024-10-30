package frc.robot.subsystems;

import java.util.Dictionary;
import java.util.List;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.ADIS16470_IMU;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.ADIS16470_IMU.IMUAxis;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.ADIS16470_IMUSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DifferentialDriveConstants;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.constants.SimConstants;
import frc.robot.utils.PhotonBridge;

/**
 * Drive Subsystem.
 * Handles all drive functionality.
 */
public class DifferentialDriveSub extends SubsystemBase {
  private final WPI_TalonSRX leftMaster = DifferentialDriveConstants.MOTOR_ID_LM.get();
  private final WPI_TalonSRX leftSlave = DifferentialDriveConstants.MOTOR_ID_LS.get();

  private final WPI_TalonSRX rightMaster = DifferentialDriveConstants.MOTOR_ID_RM.get();
  private final WPI_TalonSRX rightSlave = DifferentialDriveConstants.MOTOR_ID_RS.get();

  private final List<WPI_TalonSRX> motors = List.of(leftMaster, leftSlave, rightMaster, rightSlave);

  private final ADIS16470_IMU imu = new ADIS16470_IMU();
  private final Encoder leftEncoder = DifferentialDriveConstants.ENCODER_ID_L.get();
  private final Encoder rightEncoder = DifferentialDriveConstants.ENCODER_ID_R.get();
  private final PhotonBridge photon = new PhotonBridge();

  public final DifferentialDrive drive; // pub for shuffleboard
  public final DifferentialDrivePoseEstimator poseEstimator = new DifferentialDrivePoseEstimator(
      DifferentialDriveConstants.KINEMATICS,
      Rotation2d.fromDegrees(imu.getAngle(IMUAxis.kZ)),
      0, 0, new Pose2d());

  // Simulation Variables
  /** @wip add corrected values */
  private final LinearSystem<N2, N2, N2> drivetrainSystem = LinearSystemId.identifyDrivetrainSystem(
      SimConstants.KV_LINEAR,
      SimConstants.KA_LINEAR,
      SimConstants.KV_ANGULAR,
      SimConstants.KA_ANGULAR);

  public final ADIS16470_IMUSim imuSim = new ADIS16470_IMUSim(imu);
  public final EncoderSim leftEncoderSim = new EncoderSim(leftEncoder);
  public final EncoderSim rightEncoderSim = new EncoderSim(rightEncoder);

  public final DifferentialDrivetrainSim drivetrainSimulator = new DifferentialDrivetrainSim(
      drivetrainSystem, DCMotor.getCIM(2), 10.71, SwerveDriveConstants.WHEEL_BASE,
      SwerveDriveConstants.WHEEL_DIAMETER_METERS / 2, null);

  private boolean Condition;
  private long startTime;
  private double totalCurrent;
  public DifferentialDriveSub() {
    
    leftSlave.follow(leftMaster);
    rightSlave.follow(rightMaster);

    drive = new DifferentialDrive(leftMaster, rightMaster);
    

    addChild("Differential Drive", drive);
  }
  public double OptimiseSteeringAngle(double currentAngle, double targetAngle, double currentTurnSpeed ){

    double AngleDifference = targetAngle - currentAngle;

    //Normalise angle diff between -180 to 180
    AngleDifference = (AngleDifference +180) %360-180;

    //Determine if it needs to be target + 180
    if (Math.abs(AngleDifference) > DifferentialDriveConstants.SNAP_THRESHOLD){
      AngleDifference = AngleDifference -180;
    }

    //Adjust the steering speed based on proximity to the snap threshold
    double adjustedTurnSpeed = adjustTurnSpeed(currentTurnSpeed, AngleDifference);
    return adjustedTurnSpeed;
  }

  private double adjustTurnSpeed(double currentTurnSpeed, double AngleDifference){
    double targetTurnSpeed;

    if (Math.abs(AngleDifference) > DifferentialDriveConstants.SNAP_THRESHOLD - 10 && Math.abs(AngleDifference) < DifferentialDriveConstants.SNAP_THRESHOLD +10){
      targetTurnSpeed = Math.signum(AngleDifference) * DifferentialDriveConstants.MIN_TURN_SPEED;
    }else{
      targetTurnSpeed = Math.signum(AngleDifference) * DifferentialDriveConstants.MAX_TURN_SPEED;
    }

    //Limit acc to avoid susden jumps
    double speedDifference = targetTurnSpeed - currentTurnSpeed;
    if (Math.abs(speedDifference) > DifferentialDriveConstants.ACCEL_LIMIT){
      targetTurnSpeed = currentTurnSpeed + Math.signum(speedDifference);
    }
    return targetTurnSpeed;
  }
  @Override
  public void periodic() {
    poseEstimator.update(
        Rotation2d.fromDegrees(imu.getAngle(IMUAxis.kZ)),
        leftEncoder.getDistance(), rightEncoder.getDistance());
    
  }

  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
  }

  public void addVisionMeasurement(Pose2d visionRobotPoseMeters, double timestampSeconds) {
    poseEstimator.addVisionMeasurement(visionRobotPoseMeters, timestampSeconds);
  }

  /**
   * Tank drive.
   * 
   * @param leftSpeed  The left speed.
   * @param rightSpeed The right speed.
   */
  public void tank(double leftSpeed, double rightSpeed) {
    leftSpeed = MathUtil.clamp(leftSpeed, -1, 1);
    rightSpeed = MathUtil.clamp(rightSpeed, -1, 1);
    
    if (Condition && !IsMotorRunning()){
        drive.tankDrive(leftSpeed, rightSpeed, false);
        startTime = System.currentTimeMillis();
      } else if(!Condition || MotorTimedOut() || isOverCurrent()){
          drive.tankDrive(0, 0);
      }
    
  }

  public void CheckandLimitCurrent(){
    if (getTotalMotorCurrent() > DifferentialDriveConstants.MAX_CURRENT_IN_MOTOR){
      for (WPI_TalonSRX motor: motors){
        motor.neutralOutput(); //stops motor to prevent overcurrent
      }
      System.out.println("Current limit exceeded");
    }
  }

  private boolean MotorTimedOut(){
    return System.currentTimeMillis() - startTime > DifferentialDriveConstants.TIMED_OUT_MS; 
   }
  
  private double getTotalMotorCurrent(){
    totalCurrent = 0.0;
    for (WPI_TalonSRX motor :motors){
      totalCurrent += motor.getStatorCurrent();
    }
    return totalCurrent;
  }
  private boolean isOverCurrent(){
    return totalCurrent > DifferentialDriveConstants.MAX_CURRENT_IN_MOTOR;
  }

  public boolean IsMotorRunning(){
    if(drive.isAlive()){
      return true;
    }
    else{
      return false;
    }
  }
  
  

  /**
   * Tank drives the robot using the specified voltages.
   * <strong>Highly unsafe.</strong> Values are uncapped, so use with caution.
   * 
   * @param leftVoltage  The left output
   * @param rightVoltage The right output
   */
  public void tankVoltage(double leftVoltage, double rightVoltage) {
    leftMaster.setVoltage(leftVoltage);
    rightMaster.setVoltage(rightVoltage);
    drive.feed();
  }

  /**
   * Arcade drive.
   * 
   * @param throttle The speed
   * @param steering The steering
   */
  public void arcade(double throttle, double steering) {
    throttle = MathUtil.clamp(throttle, -1, 1);
    steering = -MathUtil.clamp(steering, -1, 1);
    drive.arcadeDrive(throttle, -steering, true); // squared input fix later
  }

  /** Stops all motors. */
  public void off() {
    tank(0, 0);
  }

  @Override
  public void simulationPeriodic() {
    // set sim motor volts to cur motor throt * bat volts

    // the order is reversed because otherwise, simulation direction is the opposite
    // of real life
    // this is probably a result of a deeper issue with the code that i don't want
    // to fix right now
    drivetrainSimulator.setInputs(
        rightMaster.get() * RobotController.getInputVoltage(),
        leftMaster.get() * RobotController.getInputVoltage());
    drivetrainSimulator.update(0.02);

    leftEncoderSim.setDistance(drivetrainSimulator.getLeftPositionMeters());
    leftEncoderSim.setRate(drivetrainSimulator.getLeftVelocityMetersPerSecond());
    rightEncoderSim.setDistance(drivetrainSimulator.getRightPositionMeters());
    rightEncoderSim.setRate(drivetrainSimulator.getRightVelocityMetersPerSecond());

    photon.simulationPeriodic(drivetrainSimulator.getPose());
    imuSim.setGyroAngleZ(drivetrainSimulator.getHeading().getDegrees());
  }
}
