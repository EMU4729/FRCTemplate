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
      addCommands(
          new SolidLEDCommand(color, 0),
          new WaitCommand(duration),
          new ClearLEDCommand(),
          new WaitCommand(duration));

          
  }
  
  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}
