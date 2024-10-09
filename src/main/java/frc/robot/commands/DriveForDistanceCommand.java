package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems;

public class DriveForDistanceCommand extends InstantCommand {
  double startP;
  double xSpeed = 0;
  double ySpeed = 0;
  double dist = 0.1;

  public DriveForDistanceCommand(double xspeed, double ySpeed, double dist) {
    this.xSpeed = xspeed;
    this.ySpeed = ySpeed;
    this.dist = dist;
    addRequirements(Subsystems.drive);
  }
  
  @Override
  public void initialize(){
    this.startP = Subsystems.drive.estimateDist();
    Subsystems.drive.drive(this.xSpeed,this.ySpeed,0,true,false);
  }

  @Override
  public boolean isFinished() {
    System.out.println(Math.abs(Subsystems.drive.estimateDist() - this.startP) > this.dist);
    return (Math.abs(Subsystems.drive.estimateDist() - this.startP)) > this.dist;
  }
  
  @Override
  public void end(boolean interupted) {
    Subsystems.drive.setX();
  }
}
