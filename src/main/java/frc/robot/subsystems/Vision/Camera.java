package frc.robot.subsystems.Vision;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.math.controller.PIDController;
import frc.robot.constants.VisionConstants;

public class Camera {
  PhotonCamera photonCamera = new PhotonCamera(VisionConstants.PHOTON_CAMERA_NAME);
  PhotonPipelineResult result = photonCamera.getLatestResult();
  private PIDController Visionpid = new PIDController(0, 0, 0);
  public void ExtractData(){
    if(result.hasTargets()){
      double yaw = result.getBestTarget().getYaw();
      double pitch = result.getBestTarget().getPitch();
      double area = result.getBestTarget().getArea();

      double distance = PhotonUtils.calculateDistanceToTargetMeters(yaw, area, pitch, pitch); //this needs constants
      double turnSpeed = Visionpid.calculate(yaw,0);
      
    }
    
  }
}
