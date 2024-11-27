package frc.robot.LEDs;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;

public class TeamColorLEDCommand extends SolidLEDCommand {
  public TeamColorLEDCommand() {
    super(DriverStation.getAlliance().isPresent() ? 
                (DriverStation.getAlliance().get() == Alliance.Red ?
                      Color.kRed :
                      Color.kBlue) :
                Color.kYellow);
  }

  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}

