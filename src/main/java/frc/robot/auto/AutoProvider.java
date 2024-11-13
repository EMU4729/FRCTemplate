package frc.robot.auto;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Subsystems;

/**
 * Provides the default command for autonomous.
 */
public class AutoProvider {
  private final SendableChooser<Command> chooser = new SendableChooser<>();

  public AutoProvider() {
    chooser.setDefaultOption("disabled", new InstantCommand(() -> {
    }, Subsystems.swerveDrive));
    SmartDashboard.putData("Auto Chooser", chooser);
  }

  public Command getSelected() {
    return chooser.getSelected();
  }
}