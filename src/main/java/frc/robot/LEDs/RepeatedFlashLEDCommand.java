package frc.robot.LEDs;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Subsystems;
import frc.robot.constants.LEDConstants;

public class RepeatedFlashLEDCommand extends Command{
  private SequentialCommandGroup flashSequence = new SequentialCommandGroup();
  private int loopIdx = 0;
  private int iterations = 0;

  public RepeatedFlashLEDCommand(SequentialCommandGroup flashCommand, int iterations){
    this(Arrays.asList(flashCommand), iterations);
  }
  public RepeatedFlashLEDCommand(List<SequentialCommandGroup> flashCommands, int iterations){
    for(SequentialCommandGroup flashCommand : flashCommands){
      this.flashSequence.addCommands(flashCommand);
    }
    this.iterations = iterations;
  }

  @Override
  public void initialize() {
    loopIdx = iterations;
  }

  @Override
  public void execute(){
    if(!flashSequence.isScheduled() && loopIdx > 0){
      flashSequence.schedule();
      loopIdx--;
    }
  }

  @Override
  public boolean isFinished(){
    return loopIdx <= 0;
  }
  
  @Override
  public boolean runsWhenDisabled(){
    return true;
  }
}
