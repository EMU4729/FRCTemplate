package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathfindingSubsystem extends SubsystemBase {
    private final NetworkTable qLearningTable;
    private final DifferentialDriveSub driveSubsystem;
    private final PhotonVisionSubsystem visionSubsystem;

    public PathfindingSubsystem(DifferentialDriveSub driveSubsystem, PhotonVisionSubsystem visionSubsystem) {
        this.driveSubsystem = driveSubsystem;
        this.visionSubsystem = visionSubsystem;
        qLearningTable = NetworkTableInstance.getDefault().getTable("Q-learning");
    }

    public void updatePath() {
        // Get state information from sensors
        double xPos = driveSubsystem.getPose().getX();
        double yPos = driveSubsystem.getPose().getY();
        double heading = driveSubsystem.getHeading();
        boolean targetVisible = visionSubsystem.hasTarget();

        // Send state to the Q-learning model
        qLearningTable.getEntry("xPos").setDouble(xPos);
        qLearningTable.getEntry("yPos").setDouble(yPos);
        qLearningTable.getEntry("heading").setDouble(heading);
        qLearningTable.getEntry("targetVisible").setBoolean(targetVisible);

        // Get action from Q-learning model
        int action = (int) qLearningTable.getEntry("action").getDouble(0);
        executeAction(action);
    }

    private void executeAction(int action) {
        switch (action) {
            case 0: driveSubsystem.arcade(0.5, 0); break; // Move forward
            case 1: driveSubsystem.arcade(0, 0.5); break; // Turn right
            case 2: driveSubsystem.arcade(0, -0.5); break; // Turn left
            case 3: driveSubsystem.off(); break; // Stop
        }
    }
}
