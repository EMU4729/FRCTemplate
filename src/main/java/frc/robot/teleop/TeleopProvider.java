package frc.robot.teleop;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Variables;

/**
 * Command for Autonomous.
 */
public class TeleopProvider {
  private static Optional<TeleopProvider> inst = Optional.empty();

  private final Command                   teleop    = new TeleopDrive();
  private final Command                   demo      = new TeleopDrive(Variables.getInstance().DriveSettingsDEMO);
  private final Command                   pidTeleop = new PIDTeleopDrive();
  public  final SendableChooser<Command>  chooser   = new SendableChooser<>(); // pub for shuffle board

  private TeleopProvider() {
    chooser.setDefaultOption("Default Teleop", teleop);
    chooser.addOption("Demo and Practice Teleop", demo);
    chooser.addOption("PID Teleop", pidTeleop);

    SmartDashboard.putData(chooser);
  }

  public static TeleopProvider getInstance() {
    if (!inst.isPresent()) {
      inst = Optional.of(new TeleopProvider());
    }
    return inst.get();
  }

  public Command getTeleop() {
    return chooser.getSelected();
  }
}