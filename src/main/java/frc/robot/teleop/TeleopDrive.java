package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.utils.logger.Logger;

/**
 * The Teleop Command.
 */
public class TeleopDrive extends CommandBase {
  private final Variables variables = Variables.getInstance();
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

    double throttle = Math.pow(oi.controller.getLeftY(), variables.inputCurveExponent);
    double steering = oi.controller.getRightX();
    double speedMultiplier = variables.teleopSpeedMultiplier;
    int reversalMultiplier = variables.invertDriveDirection ? 1 : -1;

    double speed = throttle * speedMultiplier * reversalMultiplier;

    subsystems.drive.getDist();

    // If needed, make the teleop speed multiplier affect steering, too
    subsystems.drive.arcade(speed, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
