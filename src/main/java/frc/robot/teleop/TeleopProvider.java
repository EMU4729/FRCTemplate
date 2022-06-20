package frc.robot.teleop;

import java.util.Optional;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * Command for Autonomous.
 */
public class TeleopProvider {
  private static Optional<TeleopProvider> instance = Optional.empty();

  private final Command teleop = new TeleopDrive();
  private final Command pidTeleop = new PIDTeleopDrive();
  private final SendableChooser<Command> chooser = new SendableChooser<>();

  private TeleopProvider() {
    chooser.setDefaultOption("Default Teleop", teleop);
    chooser.addOption("PID Teleop", pidTeleop);

    SmartDashboard.putData(chooser);
  }

  public static TeleopProvider getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new TeleopProvider());
    }
    return instance.get();
  }

  public static Command getTeleop() {
    return getInstance().chooser.getSelected();
  }
}
