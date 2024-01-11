package frc.robot.utils.motorbuilder;

public abstract class MotorBuilder<T> {
  public final int port;

  public boolean invert = false;
  public boolean brake = false;
  public boolean safety = false;

  public MotorBuilder(int motorPort) {
    this.port = motorPort;
  }

  public MotorBuilder<T> withBrake() {
    brake = true;
    return this;
  }

  public MotorBuilder<T> withInvert() {
    invert = true;
    return this;
  }

  public MotorBuilder<T> withSafety() {
    safety = true;
    return this;
  }

  public abstract T build();
}
