package frc.robot;

import frc.robot.subsystems.LEDSub;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.subsystems.DifferentialDriveSub;
import frc.robot.subsystems.DriveSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  public static final DriveSub drive = new DriveSub();
  public static final LEDSub led = new LEDSub();
  public static final DifferentialDriveSub diffDrive = new DifferentialDriveSub();
}
