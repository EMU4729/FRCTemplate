package frc.robot.LEDs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems;

public class FlashSolidLEDCommand extends SequentialCommandGroup {
  public FlashSolidLEDCommand(Color color, double duration) {
    this(new ArrayList<Color>(Arrays.asList(color, Color.kBlack)), duration);
  }
  public FlashSolidLEDCommand(List<Color> colors, double duration) {
    this(colors, duration, new LEDCommandBase().withZone(Subsystems.ledZones));
  }
  public FlashSolidLEDCommand(Color color, double duration, LEDCommandBase zones) {
    this(new ArrayList<Color>(Arrays.asList(color, Color.kBlack)), duration, zones);
  }
  public FlashSolidLEDCommand(List<Color> colors, double duration, LEDCommandBase zones) {
      for(Color color : colors){
        addCommands(
            new SolidLEDCommand(color).withZone(zones.getZones()),
            new WaitCommand(duration));          
      }
      addCommands(new SolidLEDCommand(Color.kBlack).withZone(zones.getZones()));
  }
  
  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}
