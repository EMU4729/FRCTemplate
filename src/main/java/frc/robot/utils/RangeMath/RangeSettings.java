package frc.robot.utils.RangeMath;

public class RangeSettings {
  protected enum axis{
    x,
    y,
    r
  };

  protected double[] min = {0,0,0}; // >= 0
  protected double[] max = {1,1,1}; // <= 1
  protected boolean[] invert = {false, false, false};

  protected double[] pow = {1,1,1}; // often 2 to 4
  
  protected double[] deadband = {0,0,0}; // 0 <= x <= 1
  
  protected double effectXonR = 0;
  protected double effectYonR = 0;
  
  protected double modCrtlMax = 0;
  protected double modRFromX = 0;
  protected double modRFromY = 0;

  public RangeSettings(){

  }

  /**
   * @param minX smallest x power outside deadband (0 < x < 1)
   * @param maxX largest x power outside deadband (0 < x < 1)
   * @param powX curve power applied to x output (output^powX) (0 < x)
   * @param deadbandX minimum input that will not be set to 0
   * @param minR smallest turn power outside deadband (0 < x < 1)
   * @param maxR largest turn power outside deadband (0 < x < 1)
   * @param powR curve power applied to turn output (output^powX) (0 < x)
   * @param deadbandR minimum input that will not be set to 0
   * @param effectXonR reduces turn power when throttle is low (tank bots turn around twice as fast when not moving this reduces that to be consistant across speeds)
   * @return
   */
  public static RangeSettings InitTankBot(double minX, double maxX, double powX, double deadbandX, boolean invertX,
                                          double minR, double maxR, double powR, double deadbandR, boolean invertR,
                                          double modRFromX, double controlMaxModifier){
    RangeSettings rangSets = new RangeSettings();
    rangSets.setX(minX, maxX, powX, deadbandX, invertX);
    rangSets.setY(0,0,0, 0, false);
    rangSets.setR(minR, maxR, powR, deadbandR, invertR, modRFromX, 0);
    rangSets.setControlModifiers(controlMaxModifier);
    return rangSets;
  }

  /**
   * @param minX smallest x power outside deadband (0 < x < 1)
   * @param maxX largest x power outside deadband (0 < x < 1)
   * @param powX curve power applied to x output (output^powX) (0 < x)
   * @param minY smallest y power outside deadband (0 < x < 1)
   * @param maxY largest y power outside deadband (0 < x < 1)
   * @param powY curve power applied to y output (output^powX) (0 < x)
   * @param minR smallest turn power outside deadband (0 < x < 1)
   * @param maxR largest turn power outside deadband (0 < x < 1)
   * @param powR curve power applied to turn output (output^powX) (0 < x)
   * @param effectXonR reduces turn power when throttle is low (tank bots turn around twice as fast when not moving this reduces that to be consistant across speeds)
   * @return
   */
  public static RangeSettings InitSwerveBot(double minX, double maxX, double powX, double deadbandX, boolean invertX,
                                          double minY, double maxY, double powY, double deadbandY, boolean invertY,
                                          double minR, double maxR, double powR, double deadbandR, boolean invertR,
                                          double controlMaxModifier){
    RangeSettings rangSets = new RangeSettings();
    rangSets.setX(minX, maxX, powX, deadbandX, invertX);
    rangSets.setY(minY, maxY, powY, deadbandY, invertY);
    rangSets.setR(minR, maxR, powR, deadbandR, invertR, 0, 0);
    rangSets.setControlModifiers(controlMaxModifier);
    return rangSets;
  }

  public void setX(double minX, double maxX, double powX, double deadbandX, boolean invertX){
    if(minX > 1 || maxX < 0 || minX > maxX){
      throw new IllegalArgumentException("Min > 1, Max < 0, or Min > Max");
    }
    this.min[0] = minX;
    this.max[0] = maxX;
    this.pow[0] = powX;
    this.deadband[0] = deadbandX;
    this.invert[0] = invertX;
  }

  public void setY(double minY, double maxY, double powY, double deadbandY, boolean invertY){
    if(minY > 1 || maxY < 0 || minY > maxY){
      throw new IllegalArgumentException("Min > 1, Max < 0, or Min > Max");
    }
    this.min[1] = minY;
    this.max[1] = maxY;
    this.pow[1] = powY;
    this.deadband[1] = deadbandY;
    this.invert[1] = invertY;
  }

  /**
   * 
   * @param ThrottleRange [min, max]
   * @param Throttlecurve
   */
  public void setR(double minR, double maxR, double powR, double deadbandR, boolean invertR,
                   double modRFromX, double modRFromY){
    if(minR > 1 || maxR < 0 || minR > maxR){
      throw new IllegalArgumentException("Min > 1, Max < 0, or Min > Max");
    } else if(modRFromX < 0 || modRFromX > 1 || modRFromY < 0 || modRFromY > 1) {
      throw new IllegalArgumentException("throttleEffect < 0 or > 1");
    }
    this.min[2] = minR;
    this.max[2] = maxR;
    this.pow[2] = powR;
    this.deadband[2] = deadbandR;
    this.invert[2] = invertR;
    this.effectXonR = modRFromX;
    this.effectYonR = modRFromY;
  }

  public void setControlModifiers(double controlMaxModifier){
    this.modCrtlMax = controlMaxModifier;
  }
}
