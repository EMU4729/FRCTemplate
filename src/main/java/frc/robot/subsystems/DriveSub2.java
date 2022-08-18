package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.Clamper;
import frc.robot.utils.logger.Logger;
import frc.robot.Variables;

/**
 * Drive Subsystem.
 * Handles all drive functionality.
 */
public class DriveSub2 extends SubsystemBase {
  private final Constants constants = Constants.getInstance();
  private final Variables vars = Variables.getInstance();

  private final MotorController leftMaster = constants.DRIVE_MOTOR_ID_LM.createMotorController();
  private final MotorController leftSlave = constants.DRIVE_MOTOR_ID_LS.createMotorController();
  public final Encoder leftEncoder = constants.DRIVE_MOTOR_ID_LM.createEncoder();
  private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftMaster, leftSlave);

  private final MotorController rightMaster = constants.DRIVE_MOTOR_ID_RM.createMotorController();
  private final MotorController rightSlave = constants.DRIVE_MOTOR_ID_RS.createMotorController();
  public final Encoder rightEncoder = constants.DRIVE_MOTOR_ID_RM.createEncoder();
  private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightMaster, rightSlave);

  private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  public DriveSub2() { 

    addChild("Differential Drive", drive);
  }

  /** Gets the left encoder rate. @return The speed. m/s */
  public double getLeftEncoderRate() {return leftEncoder.getRate();}
  /** Gets the right encoder rate. @return The speed. m/s*/
  public double getRightEncoderRate() {return rightEncoder.getRate();}

  /** Gets the average encoder rate. @return speed of COM. m/s*/
  public double getCOMSpeed() {
    Logger.info("Speed : L - "+getLeftEncoderRate()+", R - "+getRightEncoderRate()+" T - "+(getLeftEncoderRate() + getRightEncoderRate()) / 2);
    return (getLeftEncoderRate() + getRightEncoderRate()) / 2;
  }
  /** gets the average turn rate. @return rate of turn. deg/s */
  public double getTurnRate(){
    double vL = getLeftEncoderRate();
    double vR = getRightEncoderRate();
    
    if(vL == vR){
      double centRad = (vL*constants.robotWheelWidth)/(vR - vL) + 0.5*constants.robotWheelWidth;
      return Math.toDegrees(getCOMSpeed()/centRad);
    }
    return 0;
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

  /** Stop all motors. */
  public void off() {drive.stopMotor();}
}
