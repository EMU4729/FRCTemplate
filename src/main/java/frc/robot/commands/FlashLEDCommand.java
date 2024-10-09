package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.RepeatCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class FlashLEDCommand extends RepeatCommand {
  public FlashLEDCommand(Color color, double duration) {
    super(
        new SequentialCommandGroup(
            new SolidLEDCommand(color),
            new WaitCommand(duration),
            new SolidLEDCommand(Color.kBlack),
            new WaitCommand(duration)));
  }
}
