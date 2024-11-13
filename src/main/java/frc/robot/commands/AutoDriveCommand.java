package frc.robot.commands;

import java.lang.module.ModuleDescriptor.Requires;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.AutoConstants;
import frc.robot.subsystems.DifferentialDriveSub;
import frc.robot.subsystems.PhotonVisionSubsystem;

public class AutoDriveCommand extends Command{
  private final DifferentialDriveSub driveSub;
  private final PhotonVisionSubsystem photonVisionSubsystem;
  private final Timer timer = new Timer();
  
  public AutoDriveCommand(DifferentialDriveSub driveSub, PhotonVisionSubsystem photonVisionSubsystem){
      this.driveSub = driveSub;
      this.photonVisionSubsystem = photonVisionSubsystem;
      addRequirements(driveSub,photonVisionSubsystem);
  }

  @Override
  public void initialize(){
    timer.reset();
    timer.start();
    driveSub.resetEconders();
  }

  @Override
  public void execute(){
    if (photonVisionSubsystem.hasTarget()){
      double yaw = photonVisionSubsystem.getTargetYaw();
      driveSub.drive.arcadeDrive(0.5, -yaw * 0.02); //adjust turn rate based on yaw
    } else{
      double headingError = driveSub.getHeading();
      driveSub.drive.arcadeDrive(0.5, -headingError * 0.05);
    }

  }

  @Override
  public boolean isFinished(){
    return timer.get()> AutoConstants.TIMEOUT_FOR_AUTODRIVE;
  }

  @Override
  public void end(boolean interrupted){
    while(interrupted){
      driveSub.stop();
    }
    
  }


  
}
