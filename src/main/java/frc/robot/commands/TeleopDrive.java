package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;

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
    double speed = Math.pow(oi.controller.getLeftY(), variables.inputCurveExponent);
    double steering = oi.controller.getRightX();
    double speedMultiplier = variables.teleopSpeedMultiplier;
    int reversalMultiplier = variables.invertDriveDirection ? 1 : -1;
    speed = speed * speedMultiplier * reversalMultiplier;

    // If needed, make the teleop speed multiplier affect steering, too
    subsystems.drive.arcade(speed, steering);

  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
