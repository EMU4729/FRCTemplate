package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.motorInfo.MotorInfo;

public class Test extends SubsystemBase{
    private MotorController motorController;


    public Test(MotorInfo motor) {
        motorController = motor.createMotorController();
    }

    public void setSpeed(double speed) {
        speed = MathUtil.clamp(speed, -1, 1);
        motorController.set(speed);
    }
}
