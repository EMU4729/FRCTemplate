package frc.robot.LEDs;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems;

public class SolidLEDCommand extends Command {
  private final Color color;
  private final int StartIdx;
  private final int EndIdx;

  public SolidLEDCommand(Color color) {
    this(color, 0, Subsystems.led.buffer.getLength());
  }

  public SolidLEDCommand(Color color, int StartIdx, int EndIdx) {
    this.color = color;
    this.StartIdx = StartIdx;
    this.EndIdx = EndIdx;
    addRequirements(Subsystems.led);
  }

  @Override
  public void initialize() {
    for (int i = StartIdx; i < Subsystems.led.buffer.getLength() && i < EndIdx; i++) {
      Subsystems.led.buffer.setLED(i, color);
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
