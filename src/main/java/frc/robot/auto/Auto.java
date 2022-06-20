package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems;

public class Auto extends SequentialCommandGroup {
  private final Subsystems subsystems = Subsystems.getInstance();

  public Auto() {
    addCommands(
        // Drive forward
        new InstantCommand(() -> subsystems.drive.arcade(0.5, 0), subsystems.drive),
        // Wait 2s
        new WaitCommand(2),
        // Drive backwards
        new InstantCommand(() -> subsystems.drive.arcade(-0.5, 0), subsystems.drive),
        // Wait 2s
        new WaitCommand(2));
  }
}
