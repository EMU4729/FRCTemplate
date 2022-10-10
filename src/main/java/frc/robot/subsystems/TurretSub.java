package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
// import frc.robot.Variables;
import frc.robot.utils.logger.Logger;

public class TurretSub extends SubsystemBase {
  private final Constants cnst = Constants.getInstance();
  // private final Variables vars = Variables.getInstance();

  private final MotorController slew = cnst.TURRET_SLEW_MOTOR_ID.createMotorController();
  private final Encoder slewEncoder = cnst.TURRET_SLEW_MOTOR_ID.createEncoder();
  // private final MotorController hood =
  // cnst.TURRET_HOOD_MOTOR_ID.createMotorController();
  // private final Encoder hoodEncoder =
  // cnst.TURRET_HOOD_MOTOR_ID.createEncoder();
  private final DigitalInput slewLimit = new DigitalInput(cnst.TURRET_SLEW_LIMIT);
  // private final DigitalInput hoodLimit = new
  // DigitalInput(cnst.TURRET_HOOD_LIMIT);

  private final PIDController slewController = new PIDController(cnst.TURRET_SLEW_PID[0],
      cnst.TURRET_SLEW_PID[1], cnst.TURRET_SLEW_PID[2]);

  private int printCount = 0;
  private double angle = 0;
  private boolean initialized = false;
  public final Command initSlewCommand = new ScheduleCommand(new InstantCommand(() -> setSpeed(-0.3), this))
      .until(() -> slewLimit.get())
      .andThen(() -> {
        slew.stopMotor();
        slewEncoder.reset();
        initialized = true;
      });

  @Override
  public void periodic() {
    if (printCount > 100) {
      Logger.info("Turret : Slew Encoder Distance : " + slewEncoder.getDistance());
      printCount = 0;
    } else {
      printCount++;
    }

    // Slew PID
    // if (!initialized)
    // return;
    // slewController.setSetpoint(angle);
    // setSpeed(slewController.calculate(slewEncoder.getDistance()));
  }

  // public void initHood() {
  // new ScheduleCommand(new InstantCommand(() -> hood.set(0.05)))
  // .until(() -> hoodLimit.get())
  // .andThen(() -> {
  // hood.stopMotor();
  // hoodEncoder.reset();
  // });
  // }

  public void setAngle(double angle) {
    if (!initialized) {
      Logger.error("Turret : TurretSub.setAngle() called before initialization");
      return;
    }
    this.angle = MathUtil.clamp(angle, cnst.TURRET_HOOD_RANGE[0], cnst.TURRET_HOOD_RANGE[1]);
  }

  public void setSpeed(double speed) {
    speed = MathUtil.clamp(speed, -1, 1);
    slew.set(speed);
  }

  /** example and test @WIP */
  // public void driveSlew(double angle) {
  // double dif = angle - slewEncoder.getDistance();
  // if (Math.abs(dif) < 2) {
  // slew.stopMotor();
  // } else if (Math.abs(dif) < 10) {
  // slew.set(Math.copySign(cnst.TURRET_SLEW_THROT_LIMS[2], dif));
  // } else if (Math.abs(dif) < 30) {
  // slew.set(Math.copySign(cnst.TURRET_SLEW_THROT_LIMS[1], dif));
  // } else if (Math.abs(dif) >= 30) {
  // slew.set(Math.copySign(cnst.TURRET_SLEW_THROT_LIMS[0], dif));
  // }
  // }

}
