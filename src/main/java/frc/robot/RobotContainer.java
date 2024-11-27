// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Arrays;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.LEDs.FlashSolidLEDCommand;
import frc.robot.LEDs.RepeatedFlashLEDCommand;
import frc.robot.auto.AutoProvider;
import frc.robot.teleop.TeleopProvider;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including
 * Subsystemsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final AutoProvider autoProvider = AutoProvider.getInstance();
  private final TeleopProvider teleopProvider = TeleopProvider.getInstance();

  /**
   * The container for the robot. Contains Subsystemsystems, OI devices, and
   * commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Robot Automations
    // flash leds yellow during endgame
    new Trigger(() -> DriverStation.isTeleop() && DriverStation.getMatchTime() <= 30)
        .whileTrue(new RepeatedFlashLEDCommand(new FlashSolidLEDCommand(Color.kYellow, 0.1, 3, 39), 5));

    // +----------------+
    // | PILOT CONTROLS |
    // +----------------+

    // --- Manual Controls ---

    // Invert Drive
    // OI.pilot.start().onTrue(new InstantCommand(() ->
    // Variables.invertDriveDirection = !Variables.invertDriveDirection));

    OI.pilot.a().onTrue(new FlashSolidLEDCommand(Color.kBrown, 1, 3, 39));
    OI.pilot.b().onTrue(new RepeatedFlashLEDCommand(new FlashSolidLEDCommand(Color.kYellow, 0.1, 3, 39), 5));
    OI.pilot.x().onTrue(new RepeatedFlashLEDCommand(
        Arrays.asList(new FlashSolidLEDCommand(Color.kBlue, 0.05, 3, 39),
                      new FlashSolidLEDCommand(Color.kRed, 0.05, 3, 39)), 5));

    // set field relitive
    // OI.pilot.rightBumper().onTrue(new InstantCommand(() ->
    // Variables.fieldRelative = !Variables.fieldRelative));

    //OI.pilot.start()
    //    .onTrue(
    //        new InstantCommand(() -> Subsystems.swerveDrive.zeroHeading(), Subsystems.swerveDrive));

    // Drive bindings handled in teleop command
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    return teleopProvider.getSelected();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoProvider.getSelected();
  }
}
