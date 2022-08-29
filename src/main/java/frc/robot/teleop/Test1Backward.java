package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems;
import frc.robot.Variables;

public class Test1Backward extends CommandBase{
    private final Subsystems subs = Subsystems.getInstance();
    private final Variables vars = Variables.getInstance();

    public Test1Backward(){
        addRequirements(subs.test1);
    }

    @Override
    public void initialize(){
        subs.test1.setSpeed(-vars.test1Speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subs.test1.setSpeed(0);
    }
}
