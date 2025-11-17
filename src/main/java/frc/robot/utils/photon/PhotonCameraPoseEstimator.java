package frc.robot.utils.photon;

import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.SimCameraProperties;
import org.photonvision.targeting.PhotonPipelineResult;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.RobotBase;

public class PhotonCameraPoseEstimator {
  private final PhotonCamera cam;
  public final PhotonCameraSim camSim;
  private final PhotonPoseEstimator poseEstimator;
  private int errorCounter = 0;

  public PhotonCameraPoseEstimator(
      String cameraName,
      Transform3d robotToCam,
      AprilTagFieldLayout fieldLayout,
      SimCameraProperties camProps) {
    cam = new PhotonCamera(cameraName);
    camSim = new PhotonCameraSim(cam, camProps);
    camSim.enableProcessedStream(RobotBase.isSimulation());

    poseEstimator = new PhotonPoseEstimator(
        fieldLayout,
        PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
        robotToCam);
    poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.AVERAGE_BEST_TARGETS);
  }

  /** @return the robot to camera transform for this camera */
  public Transform3d getRobotToCameraTransform() {
    return poseEstimator.getRobotToCameraTransform();
  }

  /**
   * Updates the {@link PhotonPoseEstimator}'s pose estimation with the latest
   * vision result. Should be called once per robot tick.
   * 
   * @return the new currently estimated pose
   */
  public Optional<EstimatedRobotPose> getEstimatedPose() {
    return getLatestResult().flatMap(poseEstimator::update);
  }

  /** @return the latest vision result from the camera */
  public Optional<PhotonPipelineResult> getLatestResult() {
    if (!cam.isConnected()) {
      printErr("PhotonBridge: Error: Camera not connected");
      return Optional.empty();
    }

    final var results = cam.getAllUnreadResults();
    if (results.isEmpty()) {
      return Optional.empty();
    }
    // is the latest result at the end or the beginning??
    final var latestResult = results.get(results.size() - 1);
    return Optional.of(latestResult);
  }

  /** Resets the internal pose to an origin pose */
  public void reset() {
    reset(new Pose2d());
  }

  /**
   * Resets the internal pose
   * 
   * @param pose The pose to reset to
   */
  public void reset(Pose2d pose) {
    poseEstimator.setLastPose(pose);
    poseEstimator.setReferencePose(pose);
  }

  /**
   * Utility function to prevent flooding stderr with error messages
   * 
   * @param message The message to print
   */
  private void printErr(String message) {
    if (errorCounter <= 0) {
      System.err.println(message);
      errorCounter = 100;
    }
    errorCounter--;
  }
}
