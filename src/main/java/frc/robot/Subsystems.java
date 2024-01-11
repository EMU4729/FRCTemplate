package frc.robot;

import frc.robot.subsystems.DifferentialDriveSub;
import frc.robot.subsystems.LEDSub;
// import frc.robot.subsystems.SwerveDriveSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  public static final DifferentialDriveSub diffDrive = new DifferentialDriveSub();
  // public static final SwerveDriveSub swerveDrive = new SwerveDriveSub();
  public static final LEDSub led = new LEDSub();
}
