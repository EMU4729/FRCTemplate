package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.robot.LEDs.LEDSub;
import frc.robot.constants.LEDConstants;
import frc.robot.subsystems.DifferentialDriveSub;
import frc.robot.subsystems.SwerveDriveSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  //public static final SwerveDriveSub swerveDrive = new SwerveDriveSub();
  public static final LEDSub led = new LEDSub(3, new ArrayList<Integer>(Arrays.asList(0,3,39,42,62,82,102)));
  //public static final DifferentialDriveSub diffDrive = new DifferentialDriveSub();
}
