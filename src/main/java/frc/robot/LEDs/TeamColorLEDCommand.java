package frc.robot.LEDs;

import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Subsystems;

public class TeamColorLEDCommand extends SolidLEDCommand {
  private static Color currentColor = Color.kBlack;
  private Color newColor = currentColor;

  public TeamColorLEDCommand(boolean hardSet) {
    super((currentColor = hardSet ? Color.kBlack : currentColor));
    if(DriverStation.waitForDsConnection(1) && DriverStation.getAlliance().isPresent()){
      newColor = DriverStation.getAlliance().get() == Alliance.Red ? Color.kRed : Color.kBlue;
    } else {
      newColor = Color.kYellow;
    }
  }

  @Override
  public void initialize() {
    if(newColor != currentColor){
      currentColor = newColor;
      color = newColor;
      super.initialize();
    }

  }

  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}

