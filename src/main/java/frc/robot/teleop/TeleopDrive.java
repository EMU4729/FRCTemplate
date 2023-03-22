package frc.robot.teleop;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.shufflecontrol.ShuffleControl;
import frc.robot.Constants;
import frc.robot.utils.CurveFit;

/**
 * The Teleop Command.
 */
public class TeleopDrive extends CommandBase {
  private final Variables vars = Variables.getInstance();
  private final Constants cnst = Constants.getInstance();
  private final OI oi = OI.getInstance();

  private final CurveFit throtFit;
  private final CurveFit steerFit;

  private boolean accel = false;
  private double lastThrot = 0;

  public TeleopDrive() {
    this(Variables.getInstance().DriveSettingsTELEOP);
  }

  public TeleopDrive(double[][] settings) {
    throtFit = new CurveFit(settings[0][0], settings[0][1], settings[0][2]);
    steerFit = new CurveFit(settings[1][0], settings[1][1], settings[1][2]).setThrotEffect(settings[1][3]);
    addRequirements(Subsystems.drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double throttle = throtFit.fit(MathUtil.applyDeadband(oi.pilot.getLeftY(), cnst.CONTROLLER_AXIS_DEADZONE));
    double steering = steerFit.fit(MathUtil.applyDeadband(oi.pilot.getRightX(), cnst.CONTROLLER_AXIS_DEADZONE),
        throttle);// limiting max steering based on throttle

    // Invert steering when throttle >= 0 to mimic car controls
    if (throttle > 0) {
      steering *= -1;
    }

    throttle = throttle * (vars.invertDriveDirection ? 1 : -1); // flips the direction of forward based on controller
                                                                // button

    lastThrot += Math.copySign(vars.accelInterval, (throttle - lastThrot));
    if (accel) {
      throttle = lastThrot;
    }

    ShuffleControl.driveTab.setControlAxis(-oi.pilot.getLeftY(), oi.pilot.getRightX());
    ShuffleControl.driveTab.setThrotGraph(-oi.pilot.getLeftY(), throttle);
    ShuffleControl.driveTab.setSteerGraph(oi.pilot.getRightX(), steering);

    Subsystems.drive.arcade(throttle, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
