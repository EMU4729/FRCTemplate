package frc.robot.teleop;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.constants.SwerveDriveConstants;

public class TeleopDriveSwerve extends Command {
  private final SlewRateLimiter xLimiter = new SlewRateLimiter(SwerveDriveConstants.MAX_ACCELERATION);
  private final SlewRateLimiter yLimiter = new SlewRateLimiter(SwerveDriveConstants.MAX_ACCELERATION);
  private final SlewRateLimiter turnLimiter = new SlewRateLimiter(SwerveDriveConstants.MAX_ANGULAR_ACCELERATION);

  private boolean robotRelative = false;

  public TeleopDriveSwerve() {
    OI.pilot.back().whileTrue(new StartEndCommand(() -> {
      robotRelative = true;
    }, () -> {
      robotRelative = false;
    }));

    addRequirements(Subsystems.swerveDrive);
  }

  @Override
  public void execute() {
    // TODO: Use RangeSettings for throttle control

    double x = OI.pilot.getLeftX();
    double y = OI.pilot.getLeftY();
    double turn = OI.pilot.getRightX();

    if (Math.abs(x) < 0.05)
      x = 0;
    if (Math.abs(y) < 0.05)
      y = 0;
    if (Math.abs(turn) < 0.05)
      turn = 0;

    x = xLimiter.calculate(x) * SwerveDriveConstants.MAX_SPEED;
    y = yLimiter.calculate(y) * SwerveDriveConstants.MAX_SPEED;
    turn = turnLimiter.calculate(turn) * SwerveDriveConstants.MAX_ANGULAR_SPEED;

    ChassisSpeeds chassisSpeeds;
    if (robotRelative) {
      chassisSpeeds = new ChassisSpeeds(x, y, turn);
    } else {
      chassisSpeeds = ChassisSpeeds.fromFieldRelativeSpeeds(x, y, turn,
          Subsystems.swerveDrive.getRotation2d());
    }
    SwerveModuleState[] states = SwerveDriveConstants.DRIVE_KINEMATICS.toSwerveModuleStates(chassisSpeeds);
    Subsystems.swerveDrive.setModuleStates(states);
  }

  @Override
  public void end(boolean interrupted) {
    Subsystems.swerveDrive.stopModules();
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
