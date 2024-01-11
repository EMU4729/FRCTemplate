package frc.robot.commands;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems;

public class SolidLEDCommand extends Command {
  private final Color color;

  public SolidLEDCommand(Color color) {
    this.color = color;
    addRequirements(Subsystems.led);
  }

  @Override
  public void initialize() {
    for (int i = 0; i < Subsystems.led.buffer.getLength(); i++) {
      Subsystems.led.buffer.setLED(i, color);
    }
    Subsystems.led.apply();
  }
}
