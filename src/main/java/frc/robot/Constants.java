// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;
import java.util.Optional;

/**
 * Constants - use this class to store any port ids, file paths, or basically
 * anything that will not change.
 */
public final class Constants {
  private static Optional<Constants> instance = Optional.empty();

  private Constants() {
  }

  public static Constants getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new Constants());
    }
    return instance.get();
  }

  // Envars
  public final Map<String, String> env = System.getenv();

  // Drive Ports
  /** Port Number for left master drive */
  public final int DRIVE_MOTOR_PORT_LM = 4; // WORKING
  /** Port Number for right master drive */
  public final int DRIVE_MOTOR_PORT_RM = 1; // WORKING
  /** Port Number for left slave drive */
  public final int DRIVE_MOTOR_PORT_LS = 5; // WORKING
  /** Port Number for right slave drive */
  public final int DRIVE_MOTOR_PORT_RS = 2; // WORKING

  // Motor Ports
  /** Port number for motor one */
  public final int MOTOR_ONE_PORT = 6; // UNTESTED
  /** Port number for motor two */
  public final int MOTOR_TWO_PORT = 7; // UNTESTED
  /** Motor speed adjustment */
  public final double MOTOR_SPEED_ADJUSTMENT = 0.05;

  // Controller
  /** Port Number for xbox controller input device */
  public final int DEVICE_PORT_XBOX_CONTROLLER = 0; // WORKING

  // File Paths
  /** file path header for files on usb storage */
  public final String[] PATH_USB = { "u//", "v//" };
  /** file path header for files on internal storage */
  public final String PATH_INTERNAL = env.get("HOME");
  /** file name for default auto */
  public final String PATH_AUTO_FILE_NAME = "autoCommands.txt";

  // Error Retry Limit
  /** limit for repeated attempts to create log file on USB storage */
  public final int REPEAT_LIMIT_LOGGER_CREATION = 10;
  /** limit for repeated attempts to read auto from internal storage */
  public final int REPEAT_LIMIT_AUTO_READ = 10;
  /** save attempts per second for the logger */
  public int LOGGER_SAVE_RATE = 10;
}
