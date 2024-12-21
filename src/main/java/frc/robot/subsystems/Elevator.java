package frc.robot.subsystems;

import java.time.Duration;
import java.time.Instant;
import java.time.Period;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.GravityTypeValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix6.sim.TalonFXSimState;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.simulation.BatterySim;
import edu.wpi.first.wpilibj.simulation.ElevatorSim;
import edu.wpi.first.wpilibj.simulation.RoboRioSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;

public class Elevator extends SubsystemBase {
  private final double LOWER_PROTECTION_HEIGHT = 0.05;
  private final double UPPER_PROTECTION_HEIGHT = 0.9;
  private int protectionState = -1;

  private double ratio = 50;
  private double drumRadius = 0.5;

  private double targetHeight = 0;

  private final double slidingMass;

  private final TalonFX liftMotor;
  private final TalonFXSimState liftMotorSim;
  private final PositionVoltage liftController;
  private final PositionVoltage liftControllerBottom;

  private final ElevatorSim elevatorSim;
  private final DCMotor elevatorGearbox = DCMotor.getFalcon500(1);

  private final Mechanism2d m_mech2d;
  private final MechanismRoot2d m_mech2dRoot;
  private final MechanismLigament2d m_elevatorMech2d;

  private Instant lastTimeSim = Instant.now();


  public Elevator(double pidP1, double pidD1, double pidD2, double slidingMass) {
    this.slidingMass = slidingMass;

    liftMotor = new TalonFX(5);
    liftMotorSim = liftMotor.getSimState();
    final var liftMotorConfig = new TalonFXConfiguration();

    liftMotorConfig.Feedback.SensorToMechanismRatio = ratio;

    liftMotorConfig.MotorOutput.NeutralMode = NeutralModeValue.Coast;

    liftMotorConfig.CurrentLimits.SupplyCurrentLimitEnable = true;
    liftMotorConfig.CurrentLimits.SupplyCurrentLimit = 40;

    liftMotorConfig.Slot0.kP = pidP1  ;
    liftMotorConfig.Slot0.kD = pidD1  ;
    liftMotorConfig.Slot0.GravityType = GravityTypeValue.Elevator_Static;
    liftMotorConfig.Slot0.kG = 0.79    * slidingMass/(ratio/25) / (drumRadius*Math.PI);

    liftMotorConfig.Slot1.kP = 0;
    liftMotorConfig.Slot1.kD = pidD2;
    liftMotorConfig.Slot1.GravityType = GravityTypeValue.Elevator_Static;
    liftMotorConfig.Slot1.kG = 0      * slidingMass;

    liftMotor.getConfigurator().apply(liftMotorConfig);
    
    liftController = new PositionVoltage(0).withSlot(0);  
    liftControllerBottom = new PositionVoltage(0).withSlot(1);  
    liftMotor.setPosition(0);

    elevatorSim = new ElevatorSim(
      elevatorGearbox,
      ratio,
      slidingMass,
      drumRadius,
      0.0,
      1.0,
      true,
      0,
      VecBuilder.fill(0.01)
    );

    m_mech2d = new Mechanism2d(2, 3);
    m_mech2dRoot = m_mech2d.getRoot("Elevator Root", 1, 1);
    m_elevatorMech2d =
        m_mech2dRoot.append(
        new MechanismLigament2d("Elevator", elevatorSim.getPositionMeters(), 90));

    SmartDashboard.putData("Elevator Sim", m_mech2d);
  }

  public double getPosition(){
    /*if(Robot.isSimulation()){
      return elevatorSim.getPositionMeters();
    }*/
    return liftMotor.getPosition().getValue();
  }

  public double getVelocity(){
    /*if(Robot.isSimulation()){
      return elevatorSim.getVelocityMetersPerSecond();
    }*/
    return liftMotor.getVelocity().getValue();
  }

  public double getAcceleration(){
    if(Robot.isSimulation()){
      System.out.println("Elevator : acceleration not simulated.");
      return 0;
    }
    return liftMotor.getAcceleration().getValue();
  }

  public void setHeight(double height){
    elevatorSim.setState(height, 0);
    periodic();
  }

  @Override  
  public void simulationPeriodic() {
    double dt = Duration.between(lastTimeSim, Instant.now()).toMillis()/1000.0;
    lastTimeSim = Instant.now();
    // In this method, we update our simulation of what our elevator is doing
    // First, we set our "inputs" (voltages)
    elevatorSim.setInput(liftMotorSim.getMotorVoltage());
    
    // Next, we update it. The standard loop time is 20ms.
    elevatorSim.update(dt);
    // Finally, we set our simulated encoder's readings and simulated battery voltage
    liftMotorSim.setRawRotorPosition(elevatorSim.getPositionMeters()*ratio);
    liftMotorSim.setRotorVelocity(elevatorSim.getVelocityMetersPerSecond()*ratio);
    //System.out.println((double)(Math.round(liftMotor.getVelocity().getValue()*10000))/10000 +" "+ elevatorSim.getPositionMeters()*ratio);
    // SimBattery estimates loaded battery voltages
    RoboRioSim.setVInVoltage(
        BatterySim.calculateDefaultBatteryLoadedVoltage(elevatorSim.getCurrentDrawAmps()));
  }

  public void driveTo(double targetHeight){this.targetHeight = targetHeight; protectionState = 0;}

  @Override
  public void periodic(){
    m_elevatorMech2d.setLength(getPosition());
    if(!DriverStation.isEnabled()){liftMotor.set(0); return;}

    System.out.println((double)(Math.round(elevatorSim.getPositionMeters()*10000))/10000 +" "+ 
        (double)(Math.round(liftMotor.getPosition().getValue()*10000))/10000 +" "+ 
        (double)(Math.round(getVelocity()*10000))/10000 +" "+ 
        (double)(Math.round(liftMotor.getVelocity().getValue()*10000))/10000 +" "+ 
        (double)(Math.round(liftMotor.getMotorVoltage().getValue()*10000))/10000 +" "+
        targetHeight);

    if(getPosition() < LOWER_PROTECTION_HEIGHT && targetHeight < LOWER_PROTECTION_HEIGHT && protectionState != 1){
      liftMotor.setControl(
        liftControllerBottom.withPosition(
          targetHeight
        ) 
      );
      protectionState = 1;
    /*} else if(getPosition() < UPPER_PROTECTION_HEIGHT && targetHeight < UPPER_PROTECTION_HEIGHT) {
      liftMotor.setControl(liftController.withPosition(
          UPPER_PROTECTION_HEIGHT-0.01;
        ));*/
    } else if (protectionState != 3) {
      liftMotor.setControl(
        liftController.withPosition(
          targetHeight
        )
      );
      protectionState = 3;
    }
  }
}
