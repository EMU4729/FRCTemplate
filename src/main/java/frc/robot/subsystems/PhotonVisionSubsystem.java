package frc.robot.subsystems;
import edu.wpi.first.apriltag.jni.*;
import org.photonvision.PhotonCamera;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonPipelineResult;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.VisionConstants;

public class PhotonVisionSubsystem extends SubsystemBase{
  private final PhotonCamera photonCamera;
  
  public PhotonVisionSubsystem(){
    photonCamera = new PhotonCamera(VisionConstants.PHOTON_CAMERA_NAME);

  }

  public PhotonPipelineResult getLatestResult(PhotonCamera phothonCamera){
    return phothonCamera.getLatestResult();
  }

  public boolean hasTarget(){
    return getLatestResult(photonCamera).hasTargets();
  }

  public double getTargetYaw(){
    return hasTarget() ? getLatestResult(photonCamera).getBestTarget().getYaw() : 0.0;

  }

  @Override
  public void periodic(){
    
  }
}
