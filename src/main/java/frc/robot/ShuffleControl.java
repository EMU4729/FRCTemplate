package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.Optional;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class ShuffleControl {
  private static  Optional<ShuffleControl>  inst  = Optional.empty();

  private         ShuffleboardTab           drive = Shuffleboard.getTab("Drive");

  private ShuffleboardLayout inOut = drive
      .getLayout("Input Output", BuiltInLayouts.kList)
      .withSize(4, 4);
  public NetworkTableEntry DriveOut = inOut
      .add("DriveOutput",0)
      .withWidget(BuiltInWidgets.kDifferentialDrive)
      .getEntry();
  public NetworkTableEntry ControlY = inOut
      .add("Control X axis", 0)
      .withWidget(BuiltInWidgets.kNumberBar)
      .getEntry();
  public NetworkTableEntry ControlX = inOut
      .add("Control Y axis", 0)
      .withWidget(BuiltInWidgets.kNumberBar)
      .getEntry();

  public NetworkTableEntry steerPID = drive
      .add("Steering PID",0)
      .withWidget(BuiltInWidgets.kPIDController)
      .getEntry();
  public NetworkTableEntry steerPIDGraphSense = drive
      .add("Steering PID",0)
      .withWidget(BuiltInWidgets.kGraph)
      .getEntry();
  public NetworkTableEntry steerPIDGraphSet = drive
      .add("Steering PID",0)
      .withWidget(BuiltInWidgets.kGraph)
      .getEntry();
  
  public NetworkTableEntry throttlePID = drive
      .add("Throttle PID",0)
      .withWidget(BuiltInWidgets.kPIDController)
      .getEntry();
  public NetworkTableEntry throttlePIDGraphSense = drive
      .add("Throttle PID",0)
      .withWidget(BuiltInWidgets.kGraph)
      .getEntry();
  public NetworkTableEntry throttlePIDGraphSet = drive
      .add("Throttle PID",0)
      .withWidget(BuiltInWidgets.kGraph)
      .getEntry();

  public NetworkTableEntry IMU = drive
      .add("Throttle PID",0)
      .withWidget(BuiltInWidgets.k3AxisAccelerometer)
      .getEntry();
  public NetworkTableEntry TeleopType = drive
      .add("Throttle PID",0)
      .withWidget(BuiltInWidgets.kComboBoxChooser)
      .getEntry();
  public NetworkTableEntry Gyro = drive
      .add("Throttle PID",0)
      .withWidget(BuiltInWidgets.kGyro)
      .getEntry();  
  

  public static ShuffleControl getInstance() {
    if (inst.isEmpty()) {
      inst = Optional.of(new ShuffleControl());
    }
    return inst.get();
  }

}
