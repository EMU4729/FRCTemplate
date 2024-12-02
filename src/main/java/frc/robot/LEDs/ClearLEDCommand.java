package frc.robot.LEDs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Subsystems;

public class ClearLEDCommand extends SolidLEDCommand {
  public ClearLEDCommand() {
    super(Color.kBlack, 0);
  }
  
  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}
