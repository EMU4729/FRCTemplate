package frc.robot.LEDs;

import java.time.Duration;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.OI;

public class BatteryPercentLEDCommand extends RepeatedFlashLEDCommand {
  static PowerDistribution pdb = new PowerDistribution();
  static boolean finished = false; 
  
  private BatteryPercentLEDCommand(Color color, int duration, int iterations){
    super((FlashSolidLEDCommand)(new FlashSolidLEDCommand(color, duration).withZone(new int[]{0})), iterations);
  }

  public static void runFor(Color color, int duration, int iterations){
    new BatteryPercentLEDCommand(color, duration, iterations).withInterruptBehavior(InterruptionBehavior.kCancelIncoming).schedule();
  }

  public static void checkBattery(){
    finished = false;
    double voltage = pdb.getVoltage();
    if(voltage < 11){
      runFor(Color.kRed, 100, 1000000);
    } else if(voltage < 11.5){
      runFor(Color.kOrange, 250, 1000000);
    } else if(voltage < 12){
      runFor(Color.kYellow, 500, 1000000);
    } else if(voltage < 12.5){
      runFor(Color.kDarkGreen, 1000, 1000000);
    } else {
      runFor(Color.kGreen, 100000000, 1000000);
    }
  }
  @Override
  public boolean isFinished() {
    return finished;
  }

  public static void finish() {
    finished = true;
  }
}
