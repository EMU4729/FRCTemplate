package frc.robot;

import java.util.Optional;

import frc.robot.auto.AutoDriveStraight;
import frc.robot.teleop.Test1Backward;
import frc.robot.teleop.Test1Forward;
import frc.robot.teleop.Test2Backward;
import frc.robot.teleop.Test2Forward;

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
  public final Test1Forward test1Forward = new Test1Forward();
  public final Test1Backward test1Backward = new Test1Backward();
  public final Test2Forward test2Forward = new Test2Forward();
  public final Test2Backward test2Backward = new Test2Backward();
}
