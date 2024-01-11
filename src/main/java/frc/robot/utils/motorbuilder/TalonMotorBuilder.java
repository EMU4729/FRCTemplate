package frc.robot.utils.motorbuilder;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import frc.robot.utils.logger.Logger;

public class TalonMotorBuilder extends MotorBuilder<WPI_TalonSRX> {
  public TalonMotorBuilder(int port) {
    super(port);
  }

  public WPI_TalonSRX build() {
    if (port < 0) {
      Logger.error("MotorInfo : motor port num < 0, check port is defined : " + port);
      return new WPI_TalonSRX(99);
    }
    WPI_TalonSRX talon = new WPI_TalonSRX(port);
    if (!talon.isAlive()) {
      Logger.warn(
          "MotorInfo : new WPI_TalonSRX on port " + port + "not found, may not exist or be of wrong type");
    }
    talon.setInverted(invert);
    if (brake) {
      talon.setNeutralMode(NeutralMode.Brake);
    } else {
      talon.setNeutralMode(NeutralMode.Coast);
    }
    talon.setSafetyEnabled(safety);
    return talon;
  }
}
