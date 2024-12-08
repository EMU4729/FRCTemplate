package frc.robot.LEDs;

import edu.wpi.first.wpilibj.util.Color;

public class SolidLEDCommand extends LEDCommandBase {
  protected Color color;

  public SolidLEDCommand(Color color) {
    this.color = color;
  }

  @Override
  public void initialize() {
    zones.forEach((zone)->zone.apply(color));
  }

  @Override
  public void end(boolean interrupted) {
    super.end(interrupted);
    zones.forEach((zone)->zone.apply(Color.kBlack));
  }

  @Override
  public boolean isFinished(){
    return false;
  } 
}
