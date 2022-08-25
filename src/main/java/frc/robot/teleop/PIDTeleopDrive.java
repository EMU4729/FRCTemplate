package frc.robot.teleop;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.OI;
import frc.robot.ShuffleControl;
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
    this(Variables.getInstance().DriveSettingsPID1,Variables.getInstance().DriveSettingsPID2);
  }
  public PIDTeleopDrive(double[][] settings1,double[][] settings2) {
    throtFit = new CrvFt(settings1[0][0],settings1[0][1],settings1[0][2]);
    steerFit = new CrvFt(settings1[1][0],settings1[1][1],settings1[1][2]).initThrotEffect(settings1[1][3]);
    subs.drive.pidArcadeSetup(settings2);
    addRequirements(subs.drive);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {    
    double speed = throtFit.fit(MathUtil.applyDeadband(oi.controller.getLeftY(),cnst.CONTROLLER_AXIS_DEADZONE));
    double steering = steerFit.fit(MathUtil.applyDeadband(oi.controller.getRightX(),cnst.CONTROLLER_AXIS_DEADZONE),speed/throtFit.outAbsMax);
    int reversalMultiplier = vars.invertDriveDirection ? 1 : -1;

    ShuffleControl.getInstance().setControlAxis(-oi.controller.getLeftY(), oi.controller.getRightX());
    subs.drive.pidArcade(speed*reversalMultiplier, steering);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
