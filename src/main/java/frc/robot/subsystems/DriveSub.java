package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Subsystems;
import frc.robot.utils.Clamper;
import frc.robot.utils.logger.Logger;
import frc.robot.Variables;

/**
 * Drive Subsystem.
 * Handles all drive functionality.
 */
public class DriveSub extends SubsystemBase {
  private final Constants constants = Constants.getInstance();
  private final Subsystems subs = Subsystems.getInstance();
  private final Variables vars = Variables.getInstance();

  private final MotorController leftMaster = constants.DRIVE_MOTOR_ID_LM.createMotorController();
  private final MotorController leftSlave = constants.DRIVE_MOTOR_ID_LS.createMotorController();
  private final MotorControllerGroup leftMotors = new MotorControllerGroup(leftMaster, leftSlave);

  private final MotorController rightMaster = constants.DRIVE_MOTOR_ID_RM.createMotorController();
  private final MotorController rightSlave = constants.DRIVE_MOTOR_ID_RS.createMotorController();
  private final MotorControllerGroup rightMotors = new MotorControllerGroup(rightMaster, rightSlave);

  private final DifferentialDrive drive = new DifferentialDrive(leftMotors, rightMotors);

  private final PIDController PIDthrot = new PIDController(0,0,0);
  private final PIDController PIDsteer = new PIDController(0,0,0);

  public DriveSub() {
    addChild("Differential Drive", drive);
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
   * runs a pid loop to drive at set speed and turn rate
   * 
   * @param speed     speed to drive at m/s
   * @param turnRate  rate to turn at deg/s
   */
  public void pidArcade(double speed, double turnRate){
    PIDthrot.setPID(vars.TELEOP_THROTTLE_KP, vars.TELEOP_THROTTLE_KI, vars.TELEOP_THROTTLE_KD);
    PIDsteer.setPID(vars.TELEOP_STEERING_KP, vars.TELEOP_STEERING_KI, vars.TELEOP_STEERING_KD);
    
    double throttle = PIDthrot.calculate(subs.nav.speed,speed);

    System.out.println("PID Throttle : " + throttle);
    //arcade(throttle, turnRate);
  }

  /**
   * Activates arcade drive. Similar to MoveSteering from ev3dev.
   * 
   * @param throttle The speed
   * @param steering The steering
   */
  public void arcade(double throttle, double steering) {
    throttle = Clamper.absUnit(throttle);
    steering = Clamper.absUnit(steering);
    drive.arcadeDrive(throttle, steering);
    
  }

  /** Stop all motors. */
  public void off() {drive.stopMotor();}
}
