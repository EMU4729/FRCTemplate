package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.shufflecontrol.ShuffleControl;
import frc.robot.utils.RangeMath.CurveFit;
import frc.robot.utils.RangeMath.RangeSettings;

public class TeleopDriveSwerve extends Command {
  private int updateShuffleCounter = 0;
  public RangeSettings settings;

  public TeleopDriveSwerve(RangeSettings settings) {
    this.settings = settings;
    //addRequirements(Subsystems.swerveDrive);
  }

  @Override
  public void execute() {
    double limiter = (1 - OI.pilot.getRightTriggerAxis()); // TODO enable this when everything else is tested
    // organise field relitive switch
    double[] control = CurveFit.fitDrive(new double[] { OI.pilot.getLeftX(), OI.pilot.getLeftY(),
        OI.pilot.getRightX(), limiter }, settings);
    var translateX = control[0];
    var translateY = control[1];
    var rotate = control[2];
    // System.out.println(control[0]+" "+control[1]+" "+control[2]+" "+control[3]+"
    // "+Subsystems.drive.getSpeedMS());
    if (updateShuffleCounter > SwerveDriveConstants.updateShuffleInterval) {
      ShuffleControl.driveTab.setControlAxis(translateX, translateY, rotate);
      updateShuffleCounter = 0;
    } else {
      updateShuffleCounter++;
    }
    //Subsystems.swerveDrive.drive(translateX, translateY, rotate, Variables.fieldRelative, true); // TODO fix rate limit
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
