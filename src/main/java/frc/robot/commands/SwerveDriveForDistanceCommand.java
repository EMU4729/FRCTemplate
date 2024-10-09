package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems;

public class SwerveDriveForDistanceCommand extends InstantCommand {
  double startP;
  double xSpeed = 0;
  double ySpeed = 0;
  double dist = 0.1;

  public SwerveDriveForDistanceCommand(double xspeed, double ySpeed, double dist) {
    this.xSpeed = xspeed;
    this.ySpeed = ySpeed;
    this.dist = dist;
    addRequirements(Subsystems.swerveDrive);
  }

  @Override
  public void initialize() {
    this.startP = Subsystems.swerveDrive.estimateDist();
    Subsystems.swerveDrive.drive(this.xSpeed, this.ySpeed, 0, true, false);
  }

  @Override
  public boolean isFinished() {
    System.out.println(Math.abs(Subsystems.swerveDrive.estimateDist() - this.startP) > this.dist);
    return (Math.abs(Subsystems.swerveDrive.estimateDist() - this.startP)) > this.dist;
  }

  @Override
  public void end(boolean interupted) {
    Subsystems.swerveDrive.setX();
  }
}
