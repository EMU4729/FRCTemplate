package frc.robot.utils.motorInfo;

import java.util.Optional;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.utils.logger.Logger;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

public class MotorInfo {
  public  int               motorPort;
  public  ActuControlTypes  type;
  public  boolean           invert = false;
  public  boolean           brake = false;
  public  boolean           saftey = false;
  public  Optional<int[]>   EncoderPort = Optional.empty();
  private Optional<Double>  EncoderSteps = Optional.empty();

  /** {invert,brake,saftey} */
  public MotorInfo(int motorPort, ActuControlTypes type){
    this.motorPort  = motorPort;
    this.type       = type;
  }

  public MotorInfo encoder(int[] EncoderPort, double EncoderSteps){
    this.EncoderPort = Optional.of(EncoderPort);
    this.EncoderSteps = Optional.of(EncoderSteps);
    return this;
  }
  public MotorInfo initBrake(){
    brake = true;
    return this;
  }
  public MotorInfo initInvert(){
    invert = true;
    return this;
  }
  public MotorInfo initSaftey(){
    saftey = true;
    return this;
  }

  public MotorController createMotorController(){
    if(motorPort < 0){throw new IllegalArgumentException("MotorInfo : motor port num < 0, check port is defined");}
    switch(type){
      case TalonSRX:
        WPI_TalonSRX tmpT = new WPI_TalonSRX(motorPort);
        if(!tmpT.isAlive()){Logger.warn("MotorInfo : new WPI_TalonSRX on port "+motorPort+"not found, may not exist or be of wrong type");}
        tmpT.setInverted(invert);
        if(brake){tmpT.setNeutralMode(NeutralMode.Brake);} else{tmpT.setNeutralMode(NeutralMode.Coast);}
        tmpT.setSafetyEnabled(saftey);
        return tmpT;
      case VictorSPX:
        WPI_VictorSPX tmpV = new WPI_VictorSPX(motorPort);
        if(!tmpV.isAlive()){Logger.warn("MotorInfo : new WPI_VictorSPX on port "+motorPort+"not found, may not exist or be of wrong type");}
        tmpV.setInverted(invert);
        if(brake){tmpV.setNeutralMode(NeutralMode.Brake);} else{tmpV.setNeutralMode(NeutralMode.Coast);}
        tmpV.setSafetyEnabled(saftey);
        return tmpV;
      case Default:
        throw new IllegalStateException("MotorInfo : controller type not set");
      default:
        throw new IllegalStateException("MotorInfo : controller type not found");
    }
  }

  public Encoder createEncoder(){
    if(EncoderPort.isPresent()){
      if(EncoderPort.get()[0] < 0){throw new IllegalStateException("MotorInfo : encoder port 1 < 0, check port is setup");}
      if(EncoderPort.get()[1] < 0){throw new IllegalStateException("MotorInfo : encoder port 2 < 0, check port is setup");}
    } else {throw new IllegalStateException("MotorInfo : EncoderPort not found, check EncoderPort is defined");}
    if(EncoderSteps.isEmpty()){
      if(EncoderSteps.get() < 0){throw new IllegalArgumentException("MotorInfo : EncoderSteps < 0, check EncoderSteps is setup");}
    } else {throw new IllegalStateException("MotorInfo : EncoderSteps not found, check EncoderSteps is defined");}
    
    Encoder encoder = new Encoder(EncoderPort.get()[0],EncoderPort.get()[1],invert,Encoder.EncodingType.k2X);
    encoder.setDistancePerPulse(EncoderSteps.get());
    encoder.setMaxPeriod(0.1);
    encoder.setMinRate(10);
    encoder.setSamplesToAverage(5);
    return encoder;
  }
}
