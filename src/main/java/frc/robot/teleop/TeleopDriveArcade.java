package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.constants.DifferentialDriveConstants;
import frc.robot.shufflecontrol.ShuffleControl;
import frc.robot.utils.RangeMath.RangeSettings;

/**
 * The Arcade Teleop command
 */
public class TeleopDriveArcade extends Command {
  private final RangeSettings settings;

  public TeleopDriveArcade() {
    this(DifferentialDriveConstants.PILOT_SETTINGS);
  }

  public TeleopDriveArcade(RangeSettings settings) {
    this.settings = settings;

    addRequirements(Subsystems.diffDrive);
  }

  @Override
  public void execute() {
    double throttle = 0;
    double steering = 0;

    // TODO: apply curve fit to throttle and steering - make compatible with arcade drive
    throttle = OI.pilot.getLeftY();
    steering = OI.pilot.getRightX();

    // Invert steering when throttle >= 0 to mimic car controls
    // if (throttle > 0) {
    // steering *= -1;
    // }

    // flips the direction of forward based on controller button
    if (Variables.invertDriveDirection) {
      throttle *= -1;
    }

    ShuffleControl.driveTab.setControlAxis(-OI.pilot.getLeftY(), OI.pilot.getRightX());

    Subsystems.diffDrive.arcade(throttle, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
