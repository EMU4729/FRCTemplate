package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
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
  private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftMaster, leftSlave);

  private final WPI_TalonSRX rightMaster = new WPI_TalonSRX(constants.DRIVE_MOTOR_PORT_RM);
  private final WPI_TalonSRX rightSlave = new WPI_TalonSRX(constants.DRIVE_MOTOR_PORT_RS);
  private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightMaster, rightSlave);

  private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  public DriveSub() {
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
    tank(0, 0);
  }
}
