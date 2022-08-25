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

  public Navigation nav;
  public DriveSub drive;
  //public final Turret turret;

  private Subsystems() {
  }
  private void init(){
    nav = new Navigation();
    drive = new DriveSub();
    //turret = new Turret();
  }

  public static Subsystems getInstance() {
    if (inst.isEmpty()) {
      inst = Optional.of(new Subsystems());
      inst.get().init();
    }
    return inst.get();
  }
}
