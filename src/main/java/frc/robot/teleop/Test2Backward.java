package frc.robot.teleop;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Subsystems;
import frc.robot.Variables;

public class Test2Backward extends CommandBase{
    private final Subsystems subs = Subsystems.getInstance();
    private final Variables vars = Variables.getInstance();

    public Test2Backward(){
        addRequirements(subs.test1);
    }

    @Override
    public void initialize(){
        subs.test2.setSpeed(-vars.test2Speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        subs.test2.setSpeed(0);
    }
}
