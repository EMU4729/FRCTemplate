// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import frc.robot.commands.TeleopDrive;
import frc.robot.utils.Clamper;

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
  private final Variables variables = Variables.getInstance();
  private final Constants constants = Constants.getInstance();
  private final Commands commands = Commands.getInstance();
  private final Subsystems subsystems = Subsystems.getInstance();
  private final TeleopDrive teleopCommand = new TeleopDrive();
  private final OI oi = OI.getInstance();

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
    oi.start.whenPressed(commands.driveInvert);

    // Motor Speed Adjustment
    oi.dPadRight.whenPressed(new InstantCommand(
        () -> variables.motorOneSpeed = Clamper.unit(variables.motorOneSpeed + constants.MOTOR_SPEED_ADJUSTMENT)));
    oi.dPadLeft.whenPressed(new InstantCommand(
        () -> variables.motorOneSpeed = Clamper.unit(variables.motorOneSpeed - constants.MOTOR_SPEED_ADJUSTMENT)));
    oi.b.whenPressed(new InstantCommand(
        () -> variables.motorTwoSpeed = Clamper.unit(variables.motorTwoSpeed + constants.MOTOR_SPEED_ADJUSTMENT)));
    oi.x.whenPressed(new InstantCommand(
        () -> variables.motorTwoSpeed = Clamper.unit(variables.motorTwoSpeed - constants.MOTOR_SPEED_ADJUSTMENT)));

    // Motor Run
    oi.dPadUp.whenHeld(new StartEndCommand(
        () -> subsystems.motor.setMotorOneSpeed(variables.motorOneSpeed),
        () -> subsystems.motor.setMotorOneSpeed(0), subsystems.motor));
    oi.dPadDown.whenHeld(new StartEndCommand(
        () -> subsystems.motor.setMotorOneSpeed(-variables.motorOneSpeed),
        () -> subsystems.motor.setMotorOneSpeed(0), subsystems.motor));
    oi.y.whenHeld(new StartEndCommand(
        () -> subsystems.motor.setMotorTwoSpeed(variables.motorTwoSpeed),
        () -> subsystems.motor.setMotorTwoSpeed(0), subsystems.motor));
    oi.a.whenHeld(new StartEndCommand(
        () -> subsystems.motor.setMotorTwoSpeed(-variables.motorTwoSpeed),
        () -> subsystems.motor.setMotorTwoSpeed(0), subsystems.motor));

    // Drive bindings handled in teleop command
  }

  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
  public Command getTeleopCommand() {
    return teleopCommand;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
