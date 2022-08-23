package frc.robot.utils.motorInfo;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.utils.logger.Logger;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class MotorInfo {
  public int motorPort;
  public ActuControlTypes type;
  public boolean invert;
  public Optional<int[]> EncoderPort;
  private Optional<Double> EncoderSteps;

  public MotorInfo(int motorPort, ActuControlTypes type, int[] EncoderPort, double EncoderSteps){
    this(motorPort, type, false, Optional.of(EncoderPort), Optional.of(EncoderSteps));
  }
  public MotorInfo(int motorPort, ActuControlTypes type, boolean invert, int[] EncoderPort, double EncoderSteps){
    this(motorPort, type, invert, Optional.of(EncoderPort), Optional.of(EncoderSteps));
  }
  public MotorInfo(int motorPort, ActuControlTypes type){
    this(motorPort, type, false, Optional.empty(), Optional.empty());
  }
  public MotorInfo(int motorPort, ActuControlTypes type, boolean invert){
    this(motorPort, type, invert, Optional.empty(), Optional.empty());
  }
  private MotorInfo(int motorPort, ActuControlTypes type, boolean invert, Optional<int[]> EncoderPort, Optional<Double> EncoderSteps){
    this.motorPort = motorPort;
    this.type = type;
    this.invert = invert;
    this.EncoderPort = EncoderPort;
    this.EncoderSteps = EncoderSteps;
  }

  public MotorController createMotorController(){
    MotorController tmp;
    switch(type){
      case TalonSRX:
        tmp = new WPI_TalonSRX(motorPort);
        tmp.setInverted(invert);
        return tmp;
      case VictorSPX:
        tmp = new WPI_VictorSPX(motorPort);
        tmp.setInverted(invert);
        return tmp;
      default:
        Logger.error("createMotorController : controller type not found");
        throw new IllegalStateException();
    }
  }

  public Encoder createEncoder(){
    if(EncoderPort.isPresent()){
      Encoder encoder = new Encoder(EncoderPort.get()[0],EncoderPort.get()[1],invert,Encoder.EncodingType.k2X);
      encoder.setDistancePerPulse(EncoderSteps.get());
      encoder.setMaxPeriod(0.1);
      encoder.setMinRate(10);
      encoder.setSamplesToAverage(5);
      return encoder;
    }
    Logger.error("createEncoder : Encoder port not found");
    throw new IllegalStateException();
  }
}
