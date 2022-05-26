package frc.robot;

import java.util.Optional;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.MotorSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> instance = Optional.empty();

  private Subsystems() {
    System.out.println("subsystes init");
  }

  public static Subsystems getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new Subsystems());
    }
    return instance.get();
  }

  public final MotorSub motor = new MotorSub();
  public final DriveSub drive = new DriveSub();
}
