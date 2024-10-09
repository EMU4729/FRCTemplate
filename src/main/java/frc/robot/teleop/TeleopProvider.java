package frc.robot.teleop;

import java.util.Optional;

import org.ejml.equation.Variable;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.constants.DriveConstants;

/**
 * Provides the default command for teleop.
 */
public class TeleopProvider {
  private static Optional<TeleopProvider> inst = Optional.empty();

  private final Command teleopSwerve = new TeleopDriveSwerve(DriveConstants.PILOT_SETTINGS);
  private final Command teleopDemoSwerve = new TeleopDriveSwerve(DriveConstants.PILOT_DEMO_SETTINGS);

  private final SendableChooser<Command> chooser = new SendableChooser<>(); // pub for shuffle board

  private TeleopProvider() {
    // swerve
    chooser.setDefaultOption("Swerve Teleop", teleopSwerve);

    // disabled
    chooser.addOption("Disable Teleop", new InstantCommand(() -> {}, Subsystems.drive));
    chooser.addOption("Swerve Demo Teleop", teleopDemoSwerve);

    chooser.onChange(Subsystems.drive::setDefaultCommand);
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
