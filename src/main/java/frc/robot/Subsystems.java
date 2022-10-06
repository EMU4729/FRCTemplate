package frc.robot;

import java.util.Optional;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.NavigationSub;
// import frc.robot.subsystems.TurretSub;
import frc.robot.subsystems.TurretSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> inst = Optional.empty();
  // private final Constants cnst = Constants.getInstance();

  public NavigationSub nav;
  public DriveSub drive;
  public TurretSub turret;

  public static Subsystems getInstance() {
    if (inst.isEmpty()) {
      inst = Optional.of(new Subsystems());
      inst.get().init();
    }
    return inst.get();
  }

  private Subsystems() {
  }

  private void init() {
    nav = new NavigationSub();
    drive = new DriveSub();
    turret = new TurretSub();
  }

}
