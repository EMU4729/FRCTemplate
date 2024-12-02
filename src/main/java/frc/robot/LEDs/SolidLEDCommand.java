package frc.robot.LEDs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems;

public class SolidLEDCommand extends Command {
  protected Color color;

  private final List<Integer> zones;

  public SolidLEDCommand(Color color) {
    this(color, Subsystems.led.getZonesList());
  }

  public SolidLEDCommand(Color color, int zone) {
    this(color, new ArrayList<Integer>(Arrays.asList(zone)));
  }

  public SolidLEDCommand(Color color, List<Integer> zones) {
    addRequirements(Subsystems.led);
    this.zones = zones;
  }


  @Override
  public void initialize() {
    for(int zone : zones){
      int zoneStart = Subsystems.led.zoneEdges.get(zone);
      int zoneEnd = Subsystems.led.zoneEdges.get(zone+1);

      for (int i = zoneStart; i < zoneEnd && i < Subsystems.led.buffer.getLength(); i++) {
        Subsystems.led.buffer.setLED(i, color);
      }
    }  
    Subsystems.led.apply();
  }

  @Override
  public boolean isFinished(){
    return true;
  } 

  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}
