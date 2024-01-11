package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems;

public class SimpleAuto extends SequentialCommandGroup {
  public SimpleAuto() {
    addCommands(
        new InstantCommand(() -> Subsystems.diffDrive.tank(0.5, 0.5), Subsystems.diffDrive),
        new WaitCommand(2),
        new InstantCommand(Subsystems.diffDrive::off, Subsystems.diffDrive));
  }

}
