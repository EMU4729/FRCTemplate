package frc.robot;

import java.util.Optional;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.NavigationSub;
import frc.robot.subsystems.TurretSub;
import frc.robot.subsystems.TestSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> inst = Optional.empty();
  private final Constants cnst = Constants.getInstance();

  public TestSub test1;
  public TestSub test2;
  public NavigationSub nav;
  public DriveSub drive;
  // public final Turret turret;

  private Subsystems() {
  }

  private void init() {
    test1 = new TestSub(cnst.TEST_1_MOTOR_ID);
    test2 = new TestSub(cnst.TEST_2_MOTOR_ID);
    nav = new NavigationSub();
    drive = new DriveSub();
    // turret = new Turret();
  }

  public static Subsystems getInstance() {
    if (inst.isEmpty()) {
      inst = Optional.of(new Subsystems());
      inst.get().init();
    }
    return inst.get();
  }
}
