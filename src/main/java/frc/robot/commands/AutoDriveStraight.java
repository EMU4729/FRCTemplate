package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems;

/**
 * Command to drive straight with help of the IMU.
 * 
 * Only to be used in autonomous.
 */
public class AutoDriveStraight extends CommandBase {
  private final Subsystems subsystems = Subsystems.getInstance();

  private double targetAngle;
  private double speed;

  public AutoDriveStraight() {
    addRequirements(subsystems.drive, subsystems.navigation);
  }

  public void run(double targetAngle, double speed) {
    this.targetAngle = targetAngle;
    this.speed = speed;
    this.schedule(true);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    subsystems.drive.arcade(
        speed, subsystems.navigation.proportionalStraightAdjustment(targetAngle));
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
