package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Subsystems;
import frc.robot.constants.SwerveDriveConstants;
import frc.robot.subsystems.SwerveDriveSub;

import java.util.function.DoubleSupplier;




public class DefaultDriveCommand extends Command{
  private final SwerveDriveSub drive;
  private final Double xSupplier, ySupplier, rotateSupplier;

  public DefaultDriveCommand(SwerveDriveSub drive, Double xSupplier, Double ySupplier, Double rotateSupplier){
    this.drive = drive;
    this.xSupplier = xSupplier;
    this.ySupplier = ySupplier;
    this.rotateSupplier = rotateSupplier;
    addRequirements(Subsystems.swerveDrive);  // Ensure this command requires the drive subsystem
  }
  @Override
    public void execute() {
        double x = xSupplier;
        double y = ySupplier;
        double rotate = rotateSupplier;
        Subsystems.swerveDrive.drive(x, y, rotate, true, true);  // Assuming field-relative is the default
    }

    @Override
    public boolean isFinished() {
        return false;  // Runs until interrupted
    }
}
