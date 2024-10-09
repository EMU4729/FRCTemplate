package frc.robot.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems;
import frc.robot.commands.DriveToPoint;

public class TestAuto extends SequentialCommandGroup {
  public TestAuto() {
    addCommands(
        Subsystems.drive
            .runOnce(() -> Subsystems.drive.resetOdometry(new Pose2d(1.4, 5.5, Rotation2d.fromDegrees(0)))),
        new DriveToPoint(new Pose2d(1, 1, Rotation2d.fromDegrees(90))));
  }

}
