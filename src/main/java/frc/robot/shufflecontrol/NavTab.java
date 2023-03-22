package frc.robot.shufflecontrol;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class NavTab {
  private ShuffleboardTab nav = Shuffleboard.getTab("Nav");

  private GenericEntry pitchEntry = nav.add("Pitch Angle", 0).getEntry();
  private GenericEntry rollEntry = nav.add("Roll Angle", 0).getEntry();

  public void setPitchAngle(double pitch){
    pitchEntry.setDouble(pitch);
  }

  public void setRollAngle(double roll) {
    rollEntry.setDouble(roll);
  }
}
