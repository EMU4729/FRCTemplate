package frc.robot.teleop;

import edu.wpi.first.math.proto.Controller;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.OI;
import frc.robot.Subsystems;
import frc.robot.Variables;
import frc.robot.constants.DifferentialDriveConstants;
import frc.robot.shufflecontrol.ShuffleControl;
import frc.robot.utils.RangeMath.CurveFit;
import frc.robot.utils.RangeMath.RangeSettings;

/**
 * The Arcade Teleop command
 */
public class TeleopDriveArcade extends Command {
  private final RangeSettings settings;
  private boolean isShutDown = false;

  public TeleopDriveArcade() {
    this(DifferentialDriveConstants.PILOT_SETTINGS);
  }

  public TeleopDriveArcade(RangeSettings settings) {
    this.settings = settings;

    addRequirements(Subsystems.diffDrive);
  }

  @Override
  public void execute() {
    double[] control = CurveFit.fitDrive(new double[] { OI.pilot.getLeftY(), 0, OI.pilot.getRightX(), 0 },
        settings);
    double throttle = control[0];
    double steering = control[2];

    // Invert steering when throttle >= 0 to mimic car controls
    // if (throttle > 0) {
    // steering *= -1;
    // }

    // flips the direction of forward based on controller button
    if (Variables.invertDriveDirection) {
      throttle *= -1;
    }

    ShuffleControl.driveTab.setControlAxis(-OI.pilot.getLeftY(), OI.pilot.getRightX());

    
    if(OI.pilot.b() != null){
        isShutDown = !isShutDown;
    }

    //call drive or stop motors based on shutdown state
    if(isShutDown){
      Subsystems.diffDrive.arcade(0, 0);
      System.out.println("Emergency Shutdown Activated");
    } else{
        Subsystems.diffDrive.arcade(throttle, steering);
    }
    
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
