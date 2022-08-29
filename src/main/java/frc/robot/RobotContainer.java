// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import frc.robot.auto.AutoProvider;
import frc.robot.teleop.TeleopProvider;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  private final Variables       vars            = Variables.getInstance();
  private final AutoProvider    autoProvider    = AutoProvider.getInstance();
  private final TeleopProvider  teleopProvider  = TeleopProvider.getInstance();
  private final Commands        coms            = Commands.getInstance(); 
  private final OI              oi              = OI.getInstance();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
   * it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Invert Drive
    oi.start.whenPressed(
        new InstantCommand(() -> vars.invertDriveDirection = !vars.invertDriveDirection));

    oi.b.whenPressed(
        new InstantCommand(() -> vars.test1Speed += 0.1));

    oi.x.whenPressed(
        new InstantCommand(() -> vars.test1Speed -= 0.1));

    oi.y.whenHeld(coms.test1Forward);

    oi.a.whenHeld(coms.test1Backward);
    // Drive bindings handled in teleop command

    oi.dPadN.whenPressed(
        new InstantCommand(() -> vars.test2Speed += 0.1));

    oi.dPadS.whenPressed(
        new InstantCommand(() -> vars.test2Speed -= 0.1));

    oi.dPadE.whenHeld(coms.test2Forward);

    oi.dPadW.whenHeld(coms.test2Backward);
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    return teleopProvider.getTeleop();
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoProvider.getAuto();
  }
}
