package frc.robot.LEDs;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;

public class TeamColorLEDCommand extends SolidLEDCommand {
  private Color currentColor = Color.kBlack;
  private Color newColor = currentColor;

  public TeamColorLEDCommand() {
    super(Color.kBlack);
  }

  @Override
  public void initialize() {
  }

  int waitIdx = 0;
  @Override
  public void execute(){
    if(waitIdx++ > 20){waitIdx = 0;}
    else {return;}

    if(DriverStation.waitForDsConnection(1) && DriverStation.getAlliance().isPresent()){
      newColor = DriverStation.getAlliance().get() == Alliance.Red ? Color.kRed : Color.kBlue;
    } else {
      newColor = Color.kYellow;
    }

    if(newColor != currentColor){
      currentColor = newColor;
      color = newColor;
      zones.forEach((zone)->zone.apply(color));
    }

  }

  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}

