package frc.robot.utils.RangeMath;

import edu.wpi.first.math.MathUtil;

public class CurveFit {
  /**
   * 
   * @param input value to be curved
   * @param settings [absMinIn, absMaxIn, absMinOut, absMaxOut, curvePower, deadband]
   */
  public static double fit(double input, double[] settings){
    input = MathUtil.applyDeadband(input, settings[5]);
    input = setInRange(input, settings[0], settings[1]);
    input = applyCurve(input, settings[4]);
    input = setOutRange(input, settings[2], settings[3]);
    return input;
  }

  /**
   * <pre>
   * clamps the input between inMin and inMax
   * adjust the range to -1 -> 1
   * power ofs the input by pow to fit an exponential curve to the input
   * (pow > 1 = slow start quick end, pow = 1 = linear, pow < 1 = quick start slow
   * end)
   * adjusts range again so that the output jumps straight to outAbsMin and curves
   * up to outAbsMax
   * on either side of zero
   * sign of input respected
   * 
   * <pre>
   * @param inputs [X Speed(-1,1), Y Speed(-1,1), R Speed(-1,1), Speed Modifier(0,1)]
   * @param settings settings that define all factors of how the drive should be controlled
   */
  public static double[] fitDrive(double[] inputs, RangeSettings settings) {
    for(int i = 0; i < 3; i++)
      inputs[i] = MathUtil.clamp(inputs[i], -1, 1);
    inputs[3] = MathUtil.clamp(inputs[3], 0, 1);
    inputs = deadbandAll(inputs, settings);
    inputs = invertAll(inputs, settings);

    
    inputs = applyCurveAll(inputs, settings); // fit power of curve to the input
    inputs = setOutRangeAll(inputs, settings); //set the range of output
    /*
     * rough sketch of the result
     *            ___ outAbsMax
     *           |
     *        __/ ___ outAbsMin
     * ______|______
     *  __|
     * /
     * |
     * 
     */
    return inputs;
  }
  
  private static double[] deadbandAll(double[] inputs, RangeSettings settings){
    for(int i = 0; i < 3; i++)
      inputs[i] = MathUtil.applyDeadband(inputs[i], settings.deadband[i]);
    return inputs;
  }

  private static double[] invertAll(double[] inputs, RangeSettings settings){
    for(int i = 0; i < 3; i++){
      if(settings.invert[i]){
        inputs[i] = -inputs[i];
      }
    }
    return inputs;
  } 

  private static double applyCurve(double input, double pow){
    return Math.copySign(Math.pow(Math.abs(input), pow), input); // fit power of curve to the input
  }

  private static double[] applyCurveAll(double[] inputs, RangeSettings settings){
    for(int i = 0; i < 3; i++)
      inputs[i] = applyCurve(inputs[i], settings.pow[i]);
    return inputs;
  }

  private static double setInRange(double input, double inMin, double inMax){
    input = Math.copySign((input-inMin)/(inMax-inMin), input);
    input = MathUtil.clamp(input, -1, 1);
    return input;
  }

  private static double setOutRange(double input, double absMin, double absMax){
    return Math.copySign(Math.abs(input) * (-absMin + absMax) + absMin, input);
  }

  private static double[] setOutRangeAll(double[] inputs, RangeSettings settings){
    double turnLimiter = limitTurnByThrottle(inputs, settings);
    double controlLimiter = limitControlByModifier(inputs, settings);
    for(int i = 0; i < 3; i++){
      double limiter = settings.max[i] * controlLimiter;
      if(i == 2)
        limiter *= turnLimiter;
      inputs[i] = setOutRange(inputs[i], settings.min[i], limiter);
    }
    return inputs;
  }

  private static double limitTurnByThrottle(double[] inputs, RangeSettings settings){
    if(settings.modRFromX == 0 && settings.modRFromY == 0)
      return 1;
    double turnModifier = Math.hypot(inputs[1]*settings.modRFromX, inputs[2]*settings.modRFromY);
    return (1 - settings.modRFromX*settings.modRFromY) + turnModifier;
  }
  private static double limitControlByModifier(double[] inputs, RangeSettings settings){
    if(settings.modCrtlMax == 0)
      return 1;
    return (1-settings.modCrtlMax) + settings.modCrtlMax*inputs[3];
  }
}
