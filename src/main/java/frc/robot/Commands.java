package frc.robot;

import java.util.Optional;

import frc.robot.auto.AutoDriveStraight;

/**
 * Commands - Use this class to initialize and access commands globally.
 */
public class Commands {
  private static Optional<Commands> inst = Optional.empty();

  private Commands() {
  }

  public static Commands getInstance() {
    if (!inst.isPresent()) {
      inst = Optional.of(new Commands());
    }
    return inst.get();
  }

  public final AutoDriveStraight autoDriveStraight = new AutoDriveStraight();
}
