package frc.robot.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems;
import frc.robot.constants.DifferentialDriveConstants;
import frc.robot.constants.SwerveDriveConstants;

/**
 * Provides the default command for teleop.
 */
public class TeleopProvider {
  private final Command teleopSwerve = new TeleopDriveSwerve(SwerveDriveConstants.PILOT_SETTINGS);
  private final Command teleopDemoSwerve = new TeleopDriveSwerve(SwerveDriveConstants.PILOT_DEMO_SETTINGS);
  private final Command teleopArcade = new TeleopDriveArcade(DifferentialDriveConstants.PILOT_SETTINGS);
  private final Command teleopDemoArcade = new TeleopDriveArcade(DifferentialDriveConstants.DEMO_SETTINGS);

  private final SendableChooser<Command> chooser = new SendableChooser<>();

  public TeleopProvider() {
    // disabled
    chooser.setDefaultOption("Disable Teleop", new InstantCommand());

    // swerve
    chooser.addOption("Swerve Teleop", teleopSwerve);
    chooser.addOption("Swerve Demo Teleop", teleopDemoSwerve);

    // tank
    chooser.addOption("Arcade Teleop", teleopArcade);
    chooser.addOption("Arcade Demo Teleop", teleopDemoArcade);

    chooser.onChange(Subsystems.swerveDrive::setDefaultCommand);

    SmartDashboard.putData("Teleop Chooser", chooser);
  }

  public Command getSelected() {
    return chooser.getSelected();
  }
}
