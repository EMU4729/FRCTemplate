package frc.robot.LEDs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sound.sampled.Port;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.simulation.AddressableLEDSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.LEDConstants;

/**
 * This subsystem controls the robot's LEDs.
 * 
 * To use, instantiate, and then modify the sub's public `buffer` field (an
 * instance of {@link AddressableLEDBuffer}) in a command, and then call
 * `apply`.
 */
public class LEDSub extends SubsystemBase {
  public final AddressableLEDBuffer buffer;

  private final AddressableLED led;
  //private final AddressableLEDSim ledSim;

  /** the first led in each zone + (last+1) */
  public final List<Integer> zoneEdges;

  public LEDSub(int port, int length) {
    this(port, new ArrayList<Integer>(Arrays.asList(0, length)));
  }
  public LEDSub(int port, List<Integer> zones) {
    buffer = new AddressableLEDBuffer(zones.get(zones.size()-1));
    led = new AddressableLED(port);
    //ledSim = new AddressableLEDSim(led);

    led.setLength(buffer.getLength());
    led.setData(new AddressableLEDBuffer(buffer.getLength()));
    led.start();
    //ledSim.setInitialized(true);

    this.zoneEdges = zones;
  }

  List<Integer> getZonesList(){
    List<Integer> zones = new ArrayList<Integer>();
    for(int i = 0; i < zoneEdges.size()-1; i++){
      zones.add(i);
    }
    return zones;
  }


  /** Applies the data in the buffer to the LEDs */
  public void apply() {
    led.setData(buffer);
  }
}
