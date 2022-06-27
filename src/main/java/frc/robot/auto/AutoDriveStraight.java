package frc.robot.auto;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Subsystems;

/**
 * Command to drive straight with help of the IMU.
 * 
 * Only to be used in autonomous.
 */
public class AutoDriveStraight extends CommandBase {
  private final Constants constants = Constants.getInstance();
  private final Subsystems subsystems = Subsystems.getInstance();

  private double speed;
  private PIDController pid;

  public AutoDriveStraight() {
    addRequirements(subsystems.drive);
  }

  public void run(double targetAngle, double speed) {
    this.speed = speed;
    pid = new PIDController(
        constants.AUTO_STRAIGHT_KP,
        constants.AUTO_STRAIGHT_KI,
        constants.AUTO_STRAIGHT_KD);
    pid.setSetpoint(targetAngle);
    this.schedule(true);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double steering = pid.calculate(subsystems.drive.getHeading());
    subsystems.drive.arcade(speed, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    subsystems.drive.off();
  }

}
