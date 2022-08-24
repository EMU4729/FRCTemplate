package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.Constants;
import frc.robot.utils.CrvFt;
import frc.robot.utils.logger.Logger;

/**
 * The Teleop Command.
 */
public class TeleopDrive extends CommandBase {
  private final Variables   vars      = Variables.getInstance();
  private final Constants   cnst      = Constants.getInstance();
  private final Subsystems  subs      = Subsystems.getInstance();
  private final OI          oi        = OI.getInstance();
  private       boolean     accel     = true;

  private       double      lastThrot = 0;

  private final CrvFt       throtFit;
  private final CrvFt       steerFit;

  public TeleopDrive(){
    this(Variables.getInstance().DriveSettingsTELEOP);
  }
  public TeleopDrive(double[][] settings) {
    throtFit = new CrvFt(settings[1][1],settings[1][2],settings[1][3]);
    steerFit = new CrvFt(settings[2][1],settings[2][2],settings[2][3]);
    addRequirements(subs.drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    double throttle = throtFit.fit(oi.controller.getLeftY());
    double steering = steerFit.fit(oi.controller.getRightX(),0.5+0.5*Math.abs(throttle));//limiting max steering based on throttle

    throttle = throttle * (vars.invertDriveDirection ? 1 : -1); //flips the direction of forward based on controller button

    lastThrot += Math.copySign(vars.accelInterval, (throttle - lastThrot));
    if(accel){throttle = lastThrot;}
    // If needed, make the teleop speed multiplier affect steering, too
    subs.drive.arcade(throttle, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
