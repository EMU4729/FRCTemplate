package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems;

/**
 * Command to use DriveSub#tank.
 * 
 * Only to be used in autonomous.
 */
public class AutoDriveTank extends CommandBase {
  private final Subsystems subsystems = Subsystems.getInstance();

  private double leftSpeed;
  private double rightSpeed;

  public AutoDriveTank() {
    addRequirements(subsystems.drive);
  }

  public void run(double leftSpeed, double rightSpeed) {
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    this.schedule(true);
  }

  @Override
  public void initialize() {
    subsystems.drive.tank(leftSpeed, rightSpeed);
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
