package frc.robot;

import java.util.Optional;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.DriveSub2;
import frc.robot.subsystems.Navigation;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> instance = Optional.empty();

  private Subsystems() {
  }

  public static Subsystems getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new Subsystems());
    }
    return instance.get();
  }

  public final DriveSub drive = new DriveSub();
  public final DriveSub2 drive2 = new DriveSub2();
  public final Navigation nav = new Navigation();
}
