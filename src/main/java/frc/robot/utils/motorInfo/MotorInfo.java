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

  public MotorInfo(int motorPort, ActuControlTypes type, int[] EncoderPort){
    this(motorPort, type, false, Optional.of(EncoderPort));
  }
  public MotorInfo(int motorPort, ActuControlTypes type, boolean invert, int[] EncoderPort){
    this(motorPort, type, invert, Optional.of(EncoderPort));
  }
  public MotorInfo(int motorPort, ActuControlTypes type){
    this(motorPort, type, false, Optional.empty());
  }
  public MotorInfo(int motorPort, ActuControlTypes type, boolean invert){
    this(motorPort, type, invert, Optional.empty());
  }
  private MotorInfo(int motorPort, ActuControlTypes type, boolean invert, Optional<int[]> EncoderPort){
    this.motorPort = motorPort;
    this.type = type;
    this.invert = invert;
    this.EncoderPort = EncoderPort;
  }

  public MotorController createMotorController(){
    switch(type){
      case TalonSRX:
        return new WPI_TalonSRX(motorPort);
      case VictorSPX:
      return new WPI_VictorSPX(motorPort);
      case Compressor:
        Logger.error("createMotorController : Compressor not of type motor controller");
        throw new IllegalStateException();
      case Solenoid:
        Logger.error("createMotorController : Solenoid not of type motor controller");
        throw new IllegalStateException();
      case DoubleSolenoid:
        Logger.error("createMotorController : DoubleSolenoid not of type motor controller");
        throw new IllegalStateException();
      default:
        Logger.error("createMotorController : controller type not found");
        throw new IllegalStateException();
    }
  }

  public Encoder createEncoder(){
    if(EncoderPort.isPresent()){
      Encoder encoder = new Encoder(EncoderPort.get()[0],EncoderPort.get()[1],invert,Encoder.EncodingType.k2X);
      encoder.setMaxPeriod(0.1);
      encoder.setMinRate(10);
      encoder.setSamplesToAverage(5);
      return encoder;
    }
    Logger.error("createEncoder : Encoder port not found");
    throw new IllegalStateException();
  }
}
