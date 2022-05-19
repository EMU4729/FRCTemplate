package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems;

/**
 * Command to use DriveSub#arcade.
 * 
 * Only to be used in autonomous.
 */
public class AutoDriveArcade extends CommandBase {
  private final Subsystems subsystems = Subsystems.getInstance();

  private double speed;
  private double steering;

  public AutoDriveArcade() {
    addRequirements(subsystems.drive);
  }

  public void run(double speed, double steering) {
    this.speed = speed;
    this.steering = steering;
    this.schedule(true);
  }

  @Override
  public void initialize() {
    subsystems.drive.arcade(speed, steering);

  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    subsystems.drive.off();
  }

}
