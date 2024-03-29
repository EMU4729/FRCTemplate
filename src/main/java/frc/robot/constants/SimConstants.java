package frc.robot.constants;

public class SimConstants {
  protected SimConstants() {
  }

  // Drivetrain
  public final double KV_LINEAR = 1.98;
  public final double KA_LINEAR = 0.2;
  public final double KV_ANGULAR = 1.5;
  public final double KA_ANGULAR = 0.3;

  // Vision
  // our camera's specs:
  // https://support.logi.com/hc/en-us/articles/17368538634519-QuickCam-Pro-9000-Technical-Specifications
  // https://support.logi.com/hc/en-us/articles/360023465073-QuickCam-Pro-9000-Technical-Specifications
  // (not exactly sure why there are 2 links for the same camera but i've put both
  // here just in case)
  public final double CAM_DIAG_FOV = 75; // degrees - assume wide-angle camera
  public final double MAX_LED_RANGE = 5; // meters
  public final int CAM_RES_WIDTH = 640; // pixels
  public final int CAM_RES_HEIGHT = 480; // pixels
  public final double MIN_TARGET_AREA = 10; // square pixels

}
