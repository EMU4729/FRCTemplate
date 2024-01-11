package frc.robot.utils.motorbuilder;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.utils.logger.Logger;

public class VictorMotorBuilder extends MotorBuilder<WPI_VictorSPX> {
  public VictorMotorBuilder(int port) {
    super(port);
  }

  public WPI_VictorSPX build() {
    if (port < 0) {
      Logger.error("MotorInfo : motor port num < 0, check port is defined : " + port);
      return new WPI_VictorSPX(99);
    }
    WPI_VictorSPX victor = new WPI_VictorSPX(port);
    if (!victor.isAlive()) {
      Logger.warn(
          "MotorInfo : new WPI_VictorSPX on port " + port + "not found, may not exist or be of wrong type");
    }
    victor.setInverted(invert);
    if (brake) {
      victor.setNeutralMode(NeutralMode.Brake);
    } else {
      victor.setNeutralMode(NeutralMode.Coast);
    }
    victor.setSafetyEnabled(safety);
    return victor;
  }
}
