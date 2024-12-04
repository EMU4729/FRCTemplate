package frc.robot.LEDs;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import edu.wpi.first.units.Time;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Subsystems;

public class FlashSolidLEDCommand extends SolidLEDCommand {
  private Instant endTime;
  private final int msDuration;

  public FlashSolidLEDCommand(Color color, int msDuration){
    super(color);
    this.msDuration = msDuration;
  }
  
  @Override
  public void initialize() {
    // TODO Auto-generated method stub
    super.initialize();
    endTime = Instant.now().plusMillis(msDuration);
  }

  
  @Override
  public boolean isFinished() {
    // TODO Auto-generated method stub
    Instant tmp = Instant.now();
    return Instant.now().isAfter(endTime);
  }

  public int getDuration(){return msDuration;}
}
