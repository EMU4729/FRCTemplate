package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.Constants;
import frc.robot.utils.logger.Logger;

/**
 * The Teleop Command.
 */
public class TeleopDrive extends CommandBase {
  private final Variables variables = Variables.getInstance();
  private final Constants constants = Constants.getInstance();
  private final Subsystems subsystems = Subsystems.getInstance();
  private final OI oi = OI.getInstance();

  public TeleopDrive() {
    addRequirements(subsystems.drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Speed Input Curve: s = c^x
    // Where s is output speed, c is joystick value and x is a input curve exponent
    // constant.

    double throttle = getThrottle();
    double steering = getSteering();
    double speedMultiplier = variables.teleopSpeedMultiplier;
    int reversalMultiplier = variables.invertDriveDirection ? 1 : -1;

    double speed = throttle * speedMultiplier * reversalMultiplier;

    // If needed, make the teleop speed multiplier affect steering, too
    subsystems.drive.arcade(speed, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  private double getThrottle(){
    double throttle = oi.controller.getLeftY();
    if(Math.abs(throttle) < constants.CONTROLLER_AXIS_DEADZONE){return 0;}
    throttle = Math.pow(throttle, variables.speedCurveExponent);
    throttle = Math.copySign(
        Math.abs(throttle)*(1.0-variables.robotMinThrottle) + variables.robotMinThrottle,
        throttle);
    return throttle;
  }

  private double getSteering(){
    double steering = oi.controller.getRightX();
    if(Math.abs(steering) < constants.CONTROLLER_AXIS_DEADZONE){return 0;}
    steering = Math.pow(steering, variables.turnCurveExponent);
    steering = Math.copySign(
        Math.abs(steering)*(1.0-variables.robotMinThrottle) + variables.robotMinThrottle,
        steering);
    return steering;
  }
}
