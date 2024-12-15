package frc.robot.utils.RangeMath;


public class DriveBaseFit {
  

  protected AxesFit axesLin;
  protected AxesFit axesYaw;
  
  protected double ModifierTurnRateBySpeed;

  public DriveBaseFit(AxesFit axesLin, AxesFit axesYaw){
    this(axesLin, axesYaw, 0);

  }
  public DriveBaseFit(AxesFit axesLin, AxesFit axesYaw, double ModifierTurnRateBySpeed){
    this.axesLin = axesLin;
    this.axesYaw = axesYaw;
    this.ModifierTurnRateBySpeed = ModifierTurnRateBySpeed;
  }

  public double[] fitTank(double x, double yaw){ return fitTank(x, yaw, 0, 0);}
  public double[] fitTank(double x, double yaw, double boostPercent, double limitPercent){
    x = axesLin.fit(x, boostPercent, limitPercent);
    yaw = axesYaw.fit(yaw, boostPercent, limitPercent);
    yaw *= ((1-ModifierTurnRateBySpeed) + (ModifierTurnRateBySpeed*(Math.abs(x)/axesLin.outAbsMax)));
    return new double[]{x, 0, yaw};
  }

  public double[] fitSwerve(double x, double y, double yaw){ return fitSwerve(x, y, yaw, 0, 0);}
  public double[] fitSwerve(double x, double y, double yaw, double boostPercent, double limitPercent){
    double angle = Math.atan2(y, x);
    double power = Math.hypot(x, y);

    power = axesLin.fit(power, boostPercent, limitPercent);
    yaw = axesYaw.fit(yaw, boostPercent, limitPercent);
   
    System.out.println(power +" "+ power*Math.cos(angle) +" "+ power*Math.sin(angle) +" "+ yaw);
    return new double[]{power*Math.cos(angle), power*Math.sin(angle), yaw};
  }
}
