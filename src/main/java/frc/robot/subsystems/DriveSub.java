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
import frc.robot.utils.CrvFt;
import frc.robot.utils.logger.Logger;
import frc.robot.Variables;
import frc.robot.teleop.PIDTeleopDrive;

/**
 * Drive Subsystem.
 * Handles all drive functionality.
 */
public class DriveSub extends SubsystemBase {
  private final Constants             cnst        = Constants.getInstance();
  private final Subsystems            subs        = Subsystems.getInstance();
  private final Variables             vars        = Variables.getInstance();

  private final MotorController       leftMaster  = cnst.DRIVE_MOTOR_ID_LM.createMotorController();
  private final MotorController       leftSlave   = cnst.DRIVE_MOTOR_ID_LS.createMotorController();
  private final MotorControllerGroup  leftMotors  = new MotorControllerGroup(leftMaster, leftSlave);

  private final MotorController       rightMaster = cnst.DRIVE_MOTOR_ID_RM.createMotorController();
  private final MotorController       rightSlave  = cnst.DRIVE_MOTOR_ID_RS.createMotorController();
  private final MotorControllerGroup  rightMotors = new MotorControllerGroup(rightMaster, rightSlave);

  private final DifferentialDrive     drive       = new DifferentialDrive(leftMotors, rightMotors);

  private final PIDController         PIDthrot    = new PIDController(0,0,0);
  private final PIDController         PIDsteer    = new PIDController(0,0,0);

  private       CrvFt                 PIDThrotCurve;
  private       CrvFt                 PIDTurnCurve;

  public DriveSub() {
    PIDthrot.setPID(cnst.TELEOP_THROTTLE_KP, cnst.TELEOP_THROTTLE_KI, cnst.TELEOP_THROTTLE_KD);
    PIDsteer.setPID(cnst.TELEOP_STEERING_KP, cnst.TELEOP_STEERING_KI, cnst.TELEOP_STEERING_KD);
    addChild("Differential Drive", drive);
  }


  /**
   * Activates tank drive. Similar to MoveTank from ev3dev.
   * 
   * @param leftSpeed  The left speed.
   * @param rightSpeed The right speed.
   */
  public void tank(double leftSpeed, double rightSpeed) {
    leftSpeed = MathUtil.clamp(leftSpeed,-1,1);
    rightSpeed = MathUtil.clamp(rightSpeed,-1,1);
    drive.tankDrive(leftSpeed, rightSpeed);
  }

  public void pidArcadeSetup(double[][] settings){
    PIDThrotCurve = new CrvFt(settings[1][1], settings[1][2], settings[1][3]);
    PIDTurnCurve = new CrvFt(settings[2][1], settings[2][2], settings[2][3]);
  }
  /**
   * runs a pid loop to drive at set speed and turn rate
   * 
   * @param speed     speed to drive at m/s
   * @param turnRate  rate to turn at deg/s
   */
  public void pidArcade(double speed, double turnRate){
    
    double throttle = PIDthrot.calculate(subs.nav.speed,speed);
    throttle = PIDThrotCurve.fit(throttle);
    double steering = 0;
    arcade(throttle, steering);
  }

  /**
   * Activates arcade drive. Similar to MoveSteering from ev3dev.
   * 
   * @param throttle The speed
   * @param steering The steering
   */
  public void arcade(double throttle, double steering) {
    throttle = MathUtil.clamp(throttle, -1, 1);
    steering = MathUtil.clamp(steering, -1, 1);
    drive.arcadeDrive(throttle, steering);
    
  }

  /** Stop all motors. */
  public void off() {drive.stopMotor();}
}
