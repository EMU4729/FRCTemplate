package frc.robot.commands;

import java.util.ArrayList;
import java.util.Iterator;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.auto.AutoLine;
import frc.robot.utils.logger.Logger;
import frc.robot.auto.AutoFacade;
import frc.robot.auto.AutoFiles;

/**
 * Command for Autonomous.
 */
public class Auto extends CommandBase {
  private final AutoFacade autoFacade = new AutoFacade();

  private ArrayList<AutoLine> lines = new ArrayList<>();
  private Iterator<AutoLine> lineIterator;
  private AutoLine currentLine;
  private boolean isFinished = false;

  public Auto() {
  }

  @Override
  public void initialize() {
    lines = AutoFiles.updateAndGetAuto();
    lineIterator = lines.iterator();
    currentLine = lineIterator.next();
    Logger.info("Auto code read : Backup");
  }

  public void nextCommand() {
    if (!lineIterator.hasNext()) {
      isFinished = true;
      return;
    }
    currentLine = lineIterator.next();
  }

  @Override
  public void execute() {
    if (currentLine == null) {
      isFinished = true;
      Logger.error("Auto : Command equal to Null");
      return;
    }
    switch (currentLine.name) {
      case "driveTank":
        autoFacade.driveTank(currentLine);
        nextCommand();
        break;
      case "driveArcade": // drive robot arcade
        autoFacade.driveArcade(currentLine);
        nextCommand();
        break;
      case "driveStraight":
        autoFacade.driveStraight(currentLine);
        nextCommand();
        break;
      case "driveOff": // stop robot
        autoFacade.driveOff();
        Logger.info("Auto : Drive Stop");
        nextCommand();
        break;
      case "wait": // wait for time
        if (autoFacade.waitFor(currentLine)) {
          nextCommand();
        }
        break;
      default:
        Logger.warn("Auto : Invalid command name " + currentLine.name);
        nextCommand();
        break;
    }
  }

  @Override
  public boolean isFinished() {
    return isFinished;
  }

  @Override
  public void end(boolean interrupted) {
    Logger.info("Auto code : End");
    autoFacade.driveOff();
  }
}