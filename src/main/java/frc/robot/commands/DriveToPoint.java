package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems;
import frc.robot.constants.AutoConstants;

public class DriveToPoint extends Command {
  private final PIDController xController = AutoConstants.TRANSLATION_PID.get();
  private final PIDController yController = AutoConstants.TRANSLATION_PID.get();
  private final PIDController rotController = AutoConstants.ROT_PID.get();

  public DriveToPoint(Pose2d targetPose) {
    xController.setSetpoint(targetPose.getX());
    yController.setSetpoint(targetPose.getY());
    rotController.setSetpoint(targetPose.getRotation().getRadians());

    xController.setTolerance(AutoConstants.TRANSLATION_TOLERANCE);
    yController.setTolerance(AutoConstants.TRANSLATION_TOLERANCE);
    rotController.setTolerance(AutoConstants.ROT_TOLERANCE);

    rotController.enableContinuousInput(-Math.PI, Math.PI);

    addRequirements(Subsystems.drive);
  }

  @Override
  public void initialize() {
    xController.reset();
    yController.reset();
    rotController.reset();
  }

  @Override
  public void execute() {
    final var currentPose = Subsystems.drive.getPose();

    final var xSpeed = xController.calculate(currentPose.getX());
    final var ySpeed = yController.calculate(currentPose.getY());
    final var rotSpeed = rotController.calculate(currentPose.getRotation().getRadians());

    Subsystems.drive.drive(xSpeed, ySpeed, rotSpeed, true, false);
  }

  @Override
  public boolean isFinished() {
    return xController.atSetpoint() && yController.atSetpoint() && rotController.atSetpoint();
  }

  @Override
  public void end(boolean interrupted) {
    Subsystems.drive.setX();
  }

}
