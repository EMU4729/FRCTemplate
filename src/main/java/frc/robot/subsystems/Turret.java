package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Variables;

public class Turret extends SubsystemBase {
  private final Constants         cnst        = Constants.getInstance();
  private final Variables         vars        = Variables.getInstance();

  private final MotorController   slew        = cnst.TURRET_SLEW_MOTOR_ID.createMotorController();
  private final Encoder           slewEncoder = cnst.TURRET_SLEW_MOTOR_ID.createEncoder();
  private final MotorController   hood        = cnst.TURRET_HOOD_MOTOR_ID.createMotorController();
  private final Encoder           hoodEncoder = cnst.TURRET_HOOD_MOTOR_ID.createEncoder();
  private final DigitalInput      slewLimit   = new DigitalInput(cnst.TURRET_SLEW_LIMIT);
  private final DigitalInput      hoodLimit   = new DigitalInput(cnst.TURRET_HOOD_LIMIT);

  public void initHood(){
    new ScheduleCommand(new InstantCommand(()-> slew.set(0.05)))
        .until(() -> slewLimit.get())
        .andThen(()->{
            slew.stopMotor();
            slewEncoder.reset();
        });
  }
  public void initSlew(){
    new ScheduleCommand(new InstantCommand(()-> hood.set(0.05)))
        .until(() -> hoodLimit.get())
        .andThen(()->{
            hood.stopMotor();
            hoodEncoder.reset();
        });
  }

  /** example and test @WIP */
  public void driveSlew(double angle){
    double dif = angle - slewEncoder.getDistance();
    if(Math.abs(dif)<2){
      slew.stopMotor();
    } else if(Math.abs(dif)<10){
      slew.set(Math.copySign(cnst.TURRET_SLEW_THROT_LIMS[3], dif));
    } else if(Math.abs(dif)<30){
      slew.set(Math.copySign(cnst.TURRET_SLEW_THROT_LIMS[2], dif));
    } else if(Math.abs(dif)>=30){
      slew.set(Math.copySign(cnst.TURRET_SLEW_THROT_LIMS[1], dif));
    }
  }
}
