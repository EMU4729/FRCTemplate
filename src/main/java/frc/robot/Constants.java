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
  /** Port Number for left drive encoder A channel */
  public final int DRIVE_ENCODER_PORT_LA = 0; // UPDATE
  /** Port Number for left drive encoder B channel */
  public final int DRIVE_ENCODER_PORT_LB = 1; // UPDATE
  /** Port Number for right drive encoder A channel */
  public final int DRIVE_ENCODER_PORT_RA = 2; // UPDATE
  /** Port Number for right drive encoder B channel */
  public final int DRIVE_ENCODER_PORT_RB = 3; // UPDATE

  // Auto Straight PID Constants
  /** Proportional constant for driving straight during auto */
  public double AUTO_STRAIGHT_KP = 0.2; // UPDATE
  /** Integral constant for driving straight during auto */
  public double AUTO_STRAIGHT_KI = 0.8; // UPDATE
  /** Derivative constant for driving straight during auto */
  public double AUTO_STRAIGHT_KD = 0.0; // UPDATE

  // Teleop Throttle PID Constants
  /** Proportional constant for throttle during teleop */
  public double TELEOP_THROTTLE_KP = 0.2; // UPDATE
  /** Integral constant for throttle during teleop */
  public double TELEOP_THROTTLE_KI = 0.8; // UPDATE
  /** Derivative constant for throttle during teleop */
  public double TELEOP_THROTTLE_KD = 0.0; // UPDATE
  /** Encoder max rate for PID loop */
  public double DRIVE_ENCODER_MAX_RATE = 1; // UPDATE

  // Controller
  /** Port Number for xbox controller input device */
  public final int DEVICE_PORT_XBOX_CONTROLLER = 0; // WORKING
  /** Threshold for triggering the controller right and left triggers */
  public final double CONTROLLER_TRIGGER_THRESHOLD = 0.5;

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
