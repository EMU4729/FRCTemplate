package frc.robot.teleop;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.constants.Constants;

/**
 * Provides the default command for teleop.
 */
public class TeleopProvider {
  private static Optional<TeleopProvider> inst = Optional.empty();

  private final Command teleop = new TeleopDriveArcade();
  private final Command demoTeleop = new TeleopDriveArcade(Constants.diffDrive.DEMO_SETTINGS);

  private final Command teleopTank = new TeleopDriveTank();
  private final Command demoTeleopTank = new TeleopDriveTank(Constants.diffDrive.DEMO_SETTINGS);

  private final Command teleopSwerve = new TeleopDriveSwerve();

  public final SendableChooser<Command> chooser = new SendableChooser<>(); // pub for shuffle board

  private TeleopProvider() {
    // 2 stick arcade
    chooser.setDefaultOption("Arcade Teleop", teleop);
    chooser.addOption("Demo Teleop", demoTeleop);

    // 2 stick tank
    chooser.addOption("Tank Teleop", teleopTank);
    chooser.addOption("Demo Tank Teleop", demoTeleopTank);
    chooser.addOption("Disable Teleop", new InstantCommand());

    // swerve
    chooser.addOption("Swerve Teleop", teleopSwerve);

    SmartDashboard.putData("Teleop Chooser", chooser);
  }

  public static TeleopProvider getInstance() {
    if (!inst.isPresent()) {
      inst = Optional.of(new TeleopProvider());
    }
    return inst.get();
  }

  public Command getSelected() {
    return chooser.getSelected();
  }
}
