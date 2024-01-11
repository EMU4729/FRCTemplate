package frc.robot.utils;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.simulation.PhotonCameraSim;
import org.photonvision.simulation.VisionSystemSim;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.RobotBase;
import frc.robot.constants.Constants;
import frc.robot.utils.logger.Logger;

public class PhotonBridge {
  private final AprilTagFieldLayout fieldLayout;
  private final Transform3d robotToCam = new Transform3d(new Translation3d(0, 0, 0), new Rotation3d(0, 0, 0));
  private final PhotonCamera cam = new PhotonCamera(Constants.vision.PHOTON_CAMERA_NAME);
  private final PhotonPoseEstimator poseEstimator;

  // Simulation
  private VisionSystemSim visionSim;
  private PhotonCameraSim camSim;

  public PhotonBridge() {
    AprilTagFieldLayout tempFieldLayout;

    try {
      tempFieldLayout = new AprilTagFieldLayout(AprilTagFields.k2023ChargedUp.m_resourceFile);
    } catch (IOException e) {
      tempFieldLayout = new AprilTagFieldLayout(List.of(), 0, 0);
      Logger.error("PhotonSub : Error reading AprilTag field layout: " + e);
    }

    fieldLayout = tempFieldLayout;
    poseEstimator = new PhotonPoseEstimator(fieldLayout, PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR, cam, robotToCam);
    poseEstimator.setMultiTagFallbackStrategy(PoseStrategy.LOWEST_AMBIGUITY);

    if (RobotBase.isSimulation()) {
      visionSim = new VisionSystemSim(Constants.vision.PHOTON_CAMERA_NAME);
      visionSim.addAprilTags(fieldLayout);
    }
  }

  public Optional<EstimatedRobotPose> getEstimatedGlobalPose(Pose2d prevEstimatedRobotPose) {
    poseEstimator.setReferencePose(prevEstimatedRobotPose);
    return poseEstimator.update();
  }

  public void reset() {
    reset(new Pose2d());
  }

  public void reset(Pose2d pose) {
    poseEstimator.setLastPose(pose);
    poseEstimator.setReferencePose(pose);
  }

  public void simulationPeriodic(Pose2d pose) {
    visionSim.update(pose);

    // todo: find out why the hell this always gives empty results
    System.out.println(cam.getLatestResult().targets.stream().map((target) -> target.toString()).toList());
  }
}
