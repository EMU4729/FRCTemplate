package frc.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import frc.robot.LEDs.LEDZone;
import frc.robot.constants.LEDConstants;
import frc.robot.subsystems.DifferentialDriveSub;
import frc.robot.subsystems.SwerveDriveSub;

/**
 * Subsystems - Use this class to initialize and access all subsystems globally.
 */
public class Subsystems {
  //public static final SwerveDriveSub swerveDrive = new SwerveDriveSub();
  public static final List<LEDZone> ledZones = new ArrayList<LEDZone>(Arrays.asList(
    new LEDZone(new short[]{60,99}, new short[]{62,101}, 0),
    new LEDZone(63, 98, 1),
    new LEDZone(0,59, 2)
  ));
  //public static final DifferentialDriveSub diffDrive = new DifferentialDriveSub();
}
