package frc.robot.LEDs;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FlashSolidLEDCommand extends SequentialCommandGroup {
  public FlashSolidLEDCommand(Color color, double duration, int StartIdx, int EndIdx) {
      addCommands(
          new SolidLEDCommand(color, StartIdx, EndIdx),
          new WaitCommand(duration),
          new SolidLEDCommand(Color.kBlack, StartIdx, EndIdx),
          new WaitCommand(duration));

          
  }
  
  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}
