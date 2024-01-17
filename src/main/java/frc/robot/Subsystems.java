package frc.robot;

import frc.robot.subsystems.LEDSub;
import frc.robot.subsystems.DriveSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  public static final DriveSub swerveDrive = new DriveSub();
  public static final LEDSub led = new LEDSub();
}
