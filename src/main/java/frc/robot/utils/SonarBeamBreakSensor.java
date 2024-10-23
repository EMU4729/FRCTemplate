package frc.robot.utils;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;

public class SonarBeamBreakSensor{
  private Ultrasonic ultrasonic;
  private DigitalInput beamBreakSensor;

  public void InitialiseSensor(){
    beamBreakSensor = new DigitalInput(0);
    ultrasonic = new Ultrasonic(1, 2);
    ultrasonic.setAutomaticMode(true);
  }

  public double getSonarDistanceInches(){
    return ultrasonic.getRangeInches();
  }

  public double getSonarDistanceMillimeters(){
    return ultrasonic.getRangeMM();
  }


  public boolean isBeamBroken(){
    return !beamBreakSensor.get();
  }

  //Add any further logical processes later
}
