package frc.robot;

import java.util.Optional;

import frc.robot.auto.AutoDriveStraight;

/**
 * Commands - Use this class to initialize and access commands globally.
 */
public class Commands {
  private static Optional<Commands> instance = Optional.empty();

  private Commands() {
  }

  public static Commands getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new Commands());
    }
    return instance.get();
  }

  public final AutoDriveStraight autoDriveStraight = new AutoDriveStraight();
}
