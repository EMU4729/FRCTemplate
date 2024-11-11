package frc.robot.utils;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj.Encoder;

public class EncoderSupplier implements Supplier<Encoder> {
  private final int port1;
  private final int port2;
  private final double steps;

  private boolean invert = false;

  public EncoderSupplier(int port1, int port2, double steps) {
    this.port1 = port1;
    this.port2 = port2;
    this.steps = steps;
  }

  public EncoderSupplier withInvert() {
    this.invert = true;
    return this;
  }

  public Encoder get() {
    if (port1 < 0) {
      throw new IllegalStateException("MotorInfo : encoder port 1 < 0, check port is setup");
    }

    if (port2 < 0) {
      throw new IllegalStateException("MotorInfo : encoder port 2 < 0, check port is setup");
    }

    if (steps < 0) {
      throw new IllegalArgumentException("MotorInfo : EncoderSteps < 0, check EncoderSteps is setup");
    }

    Encoder encoder = new Encoder(port1, port2, invert, Encoder.EncodingType.k2X);
    encoder.setDistancePerPulse(steps);
    encoder.setMinRate(0.1 * steps);
    encoder.setMinRate(10);
    encoder.setSamplesToAverage(5);
    return encoder;
  }
}
