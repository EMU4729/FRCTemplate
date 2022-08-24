package frc.robot;

import java.util.Optional;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.Navigation;
import frc.robot.subsystems.Turret;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> inst = Optional.empty();

  public static final Navigation nav = new Navigation(); //leave it static (crash)
  public final DriveSub drive = new DriveSub();
  //public final Turret turret = new Turret();

  private Subsystems() {
  }

  public static Subsystems getInstance() {
    if (inst.isEmpty()) {
      inst = Optional.of(new Subsystems());
    }
    return inst.get();
  }
}
