package frc.robot;

import frc.robot.subsystems.DriveSub;
import frc.robot.subsystems.NavigationSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  // private final Constants cnst = Constants.getInstance();

  public static NavigationSub nav = new NavigationSub();
  public static DriveSub drive = new DriveSub();

}
