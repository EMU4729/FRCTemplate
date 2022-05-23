package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;

/**
 * The Teleop Command.
 */
public class TeleopDrive extends CommandBase {
  private final Variables variables = Variables.getInstance();
  private final Constants constants = Constants.getInstance();
  private final Subsystems subsystems = Subsystems.getInstance();
  private final PIDController pid = new PIDController(
      constants.TELEOP_THROTTLE_KP,
      constants.TELEOP_THROTTLE_KI,
      constants.TELEOP_THROTTLE_KD);
  private final OI oi = OI.getInstance();

  public TeleopDrive() {
    addRequirements(subsystems.drive, subsystems.navigation);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    // Speed Input Curve: s = c^x
    // Where s is output speed, c is joystick value and x is a input curve exponent
    // constant.

    double throttle = Math.pow(oi.controller.getLeftY(), variables.inputCurveExponent);
    double steering = oi.controller.getRightX();
    double speedMultiplier = variables.teleopSpeedMultiplier;
    int reversalMultiplier = variables.invertDriveDirection ? 1 : -1;

    pid.setSetpoint(constants.DRIVE_ENCODER_MAX_RATE * throttle);
    double speed = pid.calculate(subsystems.navigation.getAverageEncoderRate()) * speedMultiplier * reversalMultiplier;

    // If needed, make the teleop speed multiplier affect steering, too
    subsystems.drive.arcade(speed, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
