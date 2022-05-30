package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.Clamper;

public class MotorTwoSub extends SubsystemBase {
  private final Constants constants = Constants.getInstance();

  private final WPI_VictorSPX motor = new WPI_VictorSPX(constants.MOTOR_TWO_PORT);

  public MotorTwoSub() {
  }

  public void setMotorSpeed(double speed) {
    speed = Clamper.absUnit(speed);
    motor.set(speed);
  }
}
