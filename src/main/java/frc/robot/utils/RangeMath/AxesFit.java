package frc.robot.utils.RangeMath;

import java.util.Optional;

import edu.wpi.first.math.MathUtil;

public class AxesFit{
    protected double inMin = -1; //lowest allowed input (clamped)
    protected double inMax = 1; //largest allowed input (clamped)
    protected double outAbsMin = 0; //lowest allowed output once outside the deadband (used to start non pid drives)
    protected double outAbsMax = 1; //max allowed output when not limited or boosted
    protected double pow = 1; //A input^pow power applied to any output to increase low speed control. will work with any >1 input.
    protected double deadband = 0; //The deadband applied to any axes input
    protected boolean invert = false; //invert this axes
    //the new max output when fully boosted (applied before limiter, increases the normal max)
    protected Optional<Double> boosterMax = Optional.empty();
    //the new max output when fully limited (applied after boost, decreases the boosted max)
    protected Optional<Double> limiterMax = Optional.empty(); 

    public AxesFit(){}

    public AxesFit withInputMinMax(double inMin, double inMax){
      if(inMin >= inMax){throw new IllegalArgumentException("inMin >= inMax");}
      this.inMin = inMin;
      this.inMax = inMax;
      return this;
    }
    public AxesFit withOutputMinMax(double outMin, double outMax){
      if(outMin < 0){throw new IllegalArgumentException("outMin < 0");}
      if(outMax < 0){throw new IllegalArgumentException("outMax < 0");}
      if(Math.abs(outMin) >= Math.abs(outMax)){throw new IllegalArgumentException("|outMin| >= |outMax|");}
      this.outAbsMin = outMin;
      this.outAbsMax = outMax;
      return this;
    }
    public AxesFit withPow(double pow){this.pow = Math.max(pow,1); return this;}
    public AxesFit withDeadBand(double deadband){this.deadband = Math.max(deadband,0); return this;}
    public AxesFit inverted(){invert = true; return this;}
    public AxesFit withBooster(double boosterMax){this.boosterMax = Optional.of(boosterMax); return this;}
    public AxesFit withLimiter(double limiterMax){this.limiterMax = Optional.of(limiterMax); return this;}

  public double fit(double axesInput){
    return fit(axesInput, 0, 0);
  }

  public double fit(double axesInput, double boostPercent, double limitPercent){
    //adjust inputs to a standard range
    axesInput = normaliseInputRange(axesInput); //change the range to -1 -> 1
    axesInput = MathUtil.clamp(axesInput, -1, 1);
    
    //invert if needed
    if(invert){ axesInput = -axesInput;}
    
    //several math functions are easier on pos 0->1
    //the sign is replaced at the end
    double sign = axesInput;
    axesInput = Math.abs(axesInput);
    
    //deadband such that the curve will start from the db not from 0
    axesInput = deadband(axesInput);
    
    //apply a curve to inputs to increase slow speed control
    axesInput = Math.pow(axesInput, pow);;
    
    axesInput = setOutRange(axesInput, outAbsMin, getModifiedMax(boostPercent, limitPercent));

    //replace the sign
    axesInput = Math.copySign(axesInput, sign); 

    return axesInput;
  }

  //-------------------------- Helper functions ------------------------

  private double deadband(double input){
    if(deadband == 0){return input;}
    //yes i need the custom one
    if (input > deadband){
      return (input - deadband)/(1-deadband);//re-range back to 0->1
    } else {
      return 0;
    }
  }

  /*
    * converts the range from inMin -> inMax to -1 -> 1
    * the math is easier in this range
    */
  private double normaliseInputRange(double input){
    if(inMin == -1 && inMax == 1){return input;}
    // -inMin : shifts the range up to be 0 -> min+max
    // /(inMax-inMin) : squash the result into 0->1
    // 2*x - 1 : expand and shift down to -1 -> 1
    return 2*((input-inMin)/(inMax-inMin)) - 1;
  }

  /** reranges the input to the output range */
  private double setOutRange(double input, double absMin, double absMax){
    return Math.copySign(Math.abs(input) * (-absMin + absMax) + absMin, input);
  }

  /** gets the absolute max output once modified by boost and limiter */
  private double getModifiedMax(double boostPercent, double limitPercent){
    double runningMax = outAbsMax;

    if(boosterMax.isPresent() && boostPercent != 0){
      boostPercent = MathUtil.clamp(boostPercent, 0, 1);
      runningMax += boostPercent*(boosterMax.get() - runningMax);
    }

    if(limiterMax.isPresent() && limitPercent != 0){
      limitPercent = MathUtil.clamp(limitPercent, 0, 1);
      runningMax -= limitPercent*(runningMax - limiterMax.get());
    }

    return runningMax;
  }
}