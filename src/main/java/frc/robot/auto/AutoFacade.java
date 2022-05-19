package frc.robot.auto;

import frc.robot.Commands;
import frc.robot.Subsystems;
import frc.robot.utils.logger.Logger;
import frc.robot.utils.AsyncTimer;

public class AutoFacade {
  private final Subsystems subsystems = Subsystems.getInstance();
  private final Commands commands = Commands.getInstance();

  private AsyncTimer waitTimer;

  public AutoFacade() {
  }

  public void driveTank(AutoLine currentCommand) {
    double leftSpeed = currentCommand.getDouble(0);
    double rightSpeed = currentCommand.getDouble(0);
    Logger.info("Auto : DriveTank : Left Speed=" + leftSpeed + ", Right Speed=" + rightSpeed);
    commands.autoDriveTank.run(leftSpeed, rightSpeed);
  }

  public void driveArcade(AutoLine currentCommand) {
    double speed = currentCommand.getDouble(0);
    double steering = currentCommand.getDouble(1);
    Logger.info("Auto : DriveArcade : Speed=" + speed + ", steer=" + steering);
    commands.autoDriveArcade.run(speed, steering);
  }

  public void driveStraight(AutoLine currentCommand) {
    double speed = currentCommand.getDouble(0);
    Logger.info("Auto : Drive Straight : Speed = " + speed);
    commands.autoDriveStraight.run(speed, subsystems.navigation.getAngle());
  }

  public void driveOff() {
    String stopped = "";
    if (commands.autoDriveArcade.isScheduled()) {
      commands.autoDriveArcade.end(true);
      stopped = "Arcade";
    } else if (commands.autoDriveTank.isScheduled()) {
      commands.autoDriveTank.end(true);
      stopped = "Tank";
    } else if (commands.autoDriveStraight.isScheduled()) {
      commands.autoDriveStraight.end(true);
      stopped = "Straight";
    }
    Logger.info("Auto : Drive Off : Stopped = " + stopped);
  }

  public boolean waitFor(AutoLine currentCommand) {
    if (waitTimer == null) {
      int duration = currentCommand.getInt(0);
      Logger.info("Auto : Wait For : Time=" + duration);
      waitTimer = new AsyncTimer(duration);
      return false;
    }
    if (waitTimer.isFinished()) {
      Logger.info("Auto : Wait For : Finished");
      waitTimer = null;
      return true;
    }
    return false;
  }
}