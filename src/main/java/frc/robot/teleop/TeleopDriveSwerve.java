package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Subsystems;

public class TeleopDriveSwerve extends Command {
  public TeleopDriveSwerve() {
    addRequirements(Subsystems.swerveDrive);
  }

  @Override
  public void execute() {
    var translateX = -OI.applyAxisDeadband(OI.pilot.getLeftY());
    var translateY = -OI.applyAxisDeadband(OI.pilot.getLeftX());
    var rotate = -OI.applyAxisDeadband(OI.pilot.getRightX());

    Subsystems.swerveDrive.drive(translateX, translateY, rotate, true, true);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
