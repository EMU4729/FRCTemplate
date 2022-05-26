package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.Clamper;

public class MotorSub extends SubsystemBase {
  private final Constants constants = Constants.getInstance();

  private final WPI_VictorSPX motorOne = new WPI_VictorSPX(constants.MOTOR_ONE_PORT);
  private final WPI_VictorSPX motorTwo = new WPI_VictorSPX(constants.MOTOR_TWO_PORT);

  public MotorSub() {
  }

  public void setMotorOneSpeed(double speed) {
    speed = Clamper.absUnit(speed);
    motorOne.set(speed);
  }

  public void setMotorTwoSpeed(double speed) {
    speed = Clamper.absUnit(speed);
    motorTwo.set(speed);
  }

}
