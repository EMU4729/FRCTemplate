package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.Navigation;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Test;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  private static Optional<Subsystems> inst = Optional.empty();
  private final  Constants cnst = Constants.getInstance();
  
  public Test test1;
  public Test test2;
  public Navigation nav;
  public DriveSub drive;
  //public final Turret turret;

  private Subsystems() {
  }
  private void init(){
    test1 = new Test(cnst.TEST_1_MOTOR_ID);
    test2 = new Test(cnst.TEST_2_MOTOR_ID);
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
