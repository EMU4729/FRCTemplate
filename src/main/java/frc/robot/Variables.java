// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Optional;

/**
 * Variables - use this class to define and set globally-accessed
 * settings/options/variables.
 */
public final class Variables {
  private static Optional<Variables> instance = Optional.empty();

  private Variables() {
  }

  public static Variables getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new Variables());
    }
    return instance.get();
  }

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
  public double TELEOP_THROTTLE_KI = 0.0; // UPDATE
  /** Derivative constant for throttle during teleop */
  public double TELEOP_THROTTLE_KD = 0.8; // UPDATE
  /** Proportional constant for steering during teleop */
  public double TELEOP_STEERING_KP = 0.2; // UPDATE
  /** Integral constant for steering during teleop */
  public double TELEOP_STEERING_KI = 0.0; // UPDATE
  /** Derivative constant for steering during teleop */
  public double TELEOP_STEERING_KD = 0.8; // UPDATE
  /** Encoder max rate for PID loop */
  public double DRIVE_ENCODER_MAX_RATE = 1; // UPDATE

  /** Bool to invert robot steering direction */
  public boolean invertSteering = false;
  /** Bool to invert robot drive direction flipping the apparent front of the robot */
  public boolean invertDriveDirection = false;

  /** Multiplier for robot max speed in teleop */
  public double teleopSpeedMultiplier = 1;
  /** Multiplier for robot max speed in auto */
  public double autoSpeedMultiplier = 1;

  /** Drive Speed Curve Exponent */
  public double speedCurveExponent = 3;
  /** Drive Turning Curve Exponent */
  public double turnCurveExponent = 3;


  /** max speed the robot can do in a straight line m/s*/
  public double robotMaxSpeed = 3.850;
  /** min throttle for robot to move 0->1 */
  public double robotMinThrottle = 0.3;
  /** min steering for robot to turn% 0->1 */
  public double robotMinTurn = 0.3;

  /** should max speed be updated if the robot exedes it */
  public boolean autoUpdateMaxSpeed = false;


}
