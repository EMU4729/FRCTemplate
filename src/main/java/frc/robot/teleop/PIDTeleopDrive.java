package frc.robot.teleop;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.utils.CrvFt;
import frc.robot.utils.logger.Logger;

/**
 * The PID Teleop Command.
 */
public class PIDTeleopDrive extends CommandBase {
  private final Variables   vars      = Variables.getInstance();
  private final Constants   cnst      = Constants.getInstance();
  private final Subsystems  subs      = Subsystems.getInstance();
  private final OI          oi        = OI.getInstance();
  
  private final CrvFt       throtFit;
  private final CrvFt       steerFit;

  public PIDTeleopDrive(){
    this(Variables.getInstance().DriveSettingsPID1);
  }
  public PIDTeleopDrive(double[][] settings) {
    throtFit = new CrvFt(settings[1][1],settings[1][2],settings[1][3]);
    steerFit = new CrvFt(settings[2][1],settings[2][2],settings[2][3]);
    addRequirements(subs.drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {    
    double speed = throtFit.fit(MathUtil.applyDeadband(oi.controller.getLeftY(),cnst.DRIVE_DEADBAND));
    double steering = steerFit.fit(MathUtil.applyDeadband(oi.controller.getRightX(),cnst.DRIVE_DEADBAND),0.5+0.5*Math.abs(speed/throtFit.outAbsMax));
    int reversalMultiplier = vars.invertDriveDirection ? 1 : -1;

    subs.drive.pidArcade(speed*reversalMultiplier, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
