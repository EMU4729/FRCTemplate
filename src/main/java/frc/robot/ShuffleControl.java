package frc.robot;

import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.teleop.TeleopProvider;

import java.util.Optional;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;

public class ShuffleControl {
  private static  Optional<ShuffleControl>  inst = Optional.empty();
  private         Subsystems                subs = Subsystems.getInstance();
  public static ShuffleControl getInstance() {
    if (inst.isEmpty()) {
      inst = Optional.of(new ShuffleControl());
    }
    return inst.get();
  }
  private         ShuffleboardTab           drive = Shuffleboard.getTab("Drive");
  private NetworkTableEntry ControlX = drive
      .add("Control X axis", 0)
      .withSize(4, 1).withPosition(0, 2)
      .withWidget(BuiltInWidgets.kNumberBar)
      .getEntry();
  private NetworkTableEntry ControlY = drive
      .add("Control Y axis", 0)
      .withSize(4, 1).withPosition(0, 3)
      .withWidget(BuiltInWidgets.kNumberBar)
      .getEntry();
  public void setControlAxis(double contX, double contY){
    ControlX.setDouble(contX);
    ControlY.setDouble(contY);
  }
  
  private NetworkTableEntry steerPIDGraphSense = drive
      .add("Steering Graph",new double[] {0,0})
      .withSize(4,3).withPosition(5,3)
      .withWidget(BuiltInWidgets.kGraph)
      .getEntry();
  public void setSteerGraph(double in, double out){
    steerPIDGraphSense.setDoubleArray(new double[] {in,out});
  }
  private NetworkTableEntry throtPIDGraphSense = drive
      .add("Throttle Graph",new double[] {0,0})
      .withSize(4,3).withPosition(5,0)
      .withWidget(BuiltInWidgets.kGraph)
      .getEntry();
  public void setThrotGraph(double in, double out){
    throtPIDGraphSense.setDoubleArray(new double[] {in,out});
  }

  private ShuffleControl(){
    drive
        .add("DriveOutput", subs.drive.drive)
        .withSize(4, 2).withPosition(0, 0)
        .withWidget(BuiltInWidgets.kDifferentialDrive);

    drive
        .add("TeleopType",TeleopProvider.getInstance().chooser)
        .withSize(2, 1).withPosition(2, 4)
        .withWidget(BuiltInWidgets.kComboBoxChooser);

    /*drive
      .add("IMU",subs.nav.imu)
      .withSize(2, 1).withPosition(2, 5)
      .withWidget(BuiltInWidgets.k3AxisAccelerometer);*/

    drive
        .add("Throttle PID",subs.drive.PIDthrot)
        .withSize(1, 3).withPosition(4, 0)
        .withWidget(BuiltInWidgets.kPIDController);

    drive
        .add("Steering PID",subs.drive.PIDsteer)
        .withSize(1, 3).withPosition(4, 3)
        .withWidget(BuiltInWidgets.kPIDController);
  }
}
