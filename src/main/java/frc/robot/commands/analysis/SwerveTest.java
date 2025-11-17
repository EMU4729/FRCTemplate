package frc.robot.commands.analysis;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems;

public class SwerveTest extends SequentialCommandGroup {
  public SwerveTest() {
    addCommands(
        Subsystems.drive.testFunction());
  }
}
