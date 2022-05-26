package frc.robot;

import java.util.Optional;

import frc.robot.commands.DriveInvert;

/**
 * Commands - Use this class to initialize and access commands globally.
 */
public class Commands {
  private static Optional<Commands> instance = Optional.empty();

  private Commands() {
    System.out.println("commands init");
  }

  public static Commands getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new Commands());
    }
    return instance.get();
  }

  public final DriveInvert driveInvert = new DriveInvert();
}
