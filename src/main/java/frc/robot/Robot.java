// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ScheduleCommand;
import frc.robot.LEDs.ClearLEDCommand;
import frc.robot.LEDs.RainbowLEDCommand;
import frc.robot.LEDs.SolidLEDCommand;
import frc.robot.LEDs.TeamColorLEDCommand;
//import frc.robot.commands.RainbowLEDCommand;
import frc.robot.shufflecontrol.ShuffleControl;
import frc.robot.utils.logger.Logger;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the
 * name of this class or
 * the package after creating this project, you must also update the
 * build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command autoCommand;
  private RobotContainer robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    robotContainer = new RobotContainer();
    // LEDControl.getInstance().runDirectionLights();
    new ShuffleControl();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and
   * test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    // LEDControl.getInstance().updatePeriodic();
    CommandScheduler.getInstance().run();
  }

  
  int ledsSetCounter = 0;
  boolean justDisabled;
  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    new RainbowLEDCommand().schedule();
    Logger.pauseAllLoggers();
    System.out
    .println("Disabled ----------------------------------------------------------------------------------------");
    ledsSetCounter = 0;
    justDisabled = true;
  }

  @Override
  public void disabledPeriodic() {
    if((justDisabled || ledsSetCounter > 20) && DriverStation.getAlliance().isPresent()){
      new TeamColorLEDCommand(justDisabled).schedule();
      ledsSetCounter = 0;
    }
    ledsSetCounter++;
    justDisabled = false;
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    new ClearLEDCommand().schedule();
    autoCommand = robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (autoCommand != null) {
      autoCommand.schedule();
    }
    Logger.unpauseAllLoggers();
    System.out
        .println("Auto Start --------------------------------------------------------------------------------------");
    //Subsystems.swerveDrive.resetIntegral();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    new ClearLEDCommand().schedule();
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (autoCommand != null) {
      autoCommand.cancel();
    }
    Logger.unpauseAllLoggers();
    System.out
        .println("Teleop Start ------------------------------------------------------------------------------------");
    //Subsystems.swerveDrive.resetIntegral();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
    Logger.unpauseAllLoggers();
    System.out
        .println("Test Start --------------------------------------------------------------------------------------");
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }
}
