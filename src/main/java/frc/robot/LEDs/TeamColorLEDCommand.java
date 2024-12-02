package frc.robot.LEDs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Subsystems;

public class TeamColorLEDCommand extends SolidLEDCommand {
  private static Color currentColor = Color.kBlack;
  private Color newColor = currentColor;

  public TeamColorLEDCommand(boolean hardSet) {
    this(hardSet, Subsystems.led.getZonesList());
  }
  public TeamColorLEDCommand(boolean hardSet, List<Integer> zones) {
    super(hardSet ? Color.kBlack : currentColor, zones);
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

