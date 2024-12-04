package frc.robot.LEDs;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems;

public class LEDCommandBase extends Command {
  protected List<LEDZone> zones = Subsystems.ledZones;

  public LEDCommandBase withZone(int zone){
    return withZone(new int[]{zone});
  } 
  public LEDCommandBase withZone(int[] zonesArr){
    this.zones = new ArrayList<LEDZone>();
    for(LEDZone zone : Subsystems.ledZones){
      for(int i = 0; i < zonesArr.length; i++){
        if(zone.getID() == zonesArr[i]) {this.zones.add(zone);}
      }
    }
    return this;
  } 
  public LEDCommandBase withZone(List<LEDZone> zones){
    this.zones = zones;
    return this;
  }

  public List<LEDZone> getZones(){return zones;}
}
