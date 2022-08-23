package frc.robot;

import java.util.Optional;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.Navigation;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> instance = Optional.empty();

  public static final Navigation nav = new Navigation(); //leave it static (crash)
  public final DriveSub drive = new DriveSub();

  private Subsystems() {
  }

  public static Subsystems getInstance() {
    if (instance.isEmpty()) {
      instance = Optional.of(new Subsystems());
    }
    return instance.get();
  }
}
