package frc.robot.teleop;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.utils.logger.Logger;

/**
 * The PID Teleop Command.
 */
public class PIDTeleopDrive extends CommandBase {
  private final Variables variables = Variables.getInstance();
  private final Constants constants = Constants.getInstance();
  private final Subsystems subsystems = Subsystems.getInstance();
  private final PIDController pid = new PIDController(
      constants.TELEOP_THROTTLE_KP,
      constants.TELEOP_THROTTLE_KI,
      constants.TELEOP_THROTTLE_KD);
  private final OI oi = OI.getInstance();

  public PIDTeleopDrive() {
    addRequirements(subsystems.drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Speed Input Curve: s = c^x
    // Where s is output speed, c is joystick value and x is a input curve
    // exponent
    // constant.

    double throttle = Math.pow(oi.controller.getLeftY(),
        variables.inputCurveExponent);
    double steering = oi.controller.getRightX();
    double speedMultiplier = variables.teleopSpeedMultiplier;
    int reversalMultiplier = variables.invertDriveDirection ? 1 : -1;
    
    pid.setSetpoint(throttle);
    double speed = pid.calculate(-subsystems.drive.distToTrottle()) *
        speedMultiplier * reversalMultiplier;

    // If needed, make the teleop speed multiplier affect steering, too
    debug(speed, steering, throttle);
    subsystems.drive.arcade(speed, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private void debug(double speed, double steering, double throttle){
    SmartDashboard.putNumber("speed", speed);


    Logger.info("PID Teleop : avg speed : "+subsystems.drive.distToTrottle());
    Logger.info("PID Teleop : left speed : "+subsystems.drive.getLeftEncoderRate());
    Logger.info("PID Teleop : right speed : "+subsystems.drive.getRightEncoderRate());
    Logger.info("PID Teleop : desired speed : "+ throttle);
    Logger.info("PID Teleop : Setpoint : + " + speed);
    Logger.info("PID Teleop : desired steering : "+ steering);
    Logger.info("PID Teleop : current pos error : "+ pid.getPositionError());
    Logger.info("PID Teleop : current vel error : "+ pid.getVelocityError());
  }
}
