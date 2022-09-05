package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.MotorInfo;

public class TestSub extends SubsystemBase {
    private final MotorController motorController;

    public TestSub(MotorInfo motor) {
        motorController = motor.createMotorController();
    }

    public void setSpeed(double speed) {
        speed = MathUtil.clamp(speed, -1, 1);
        motorController.set(speed);
    }
}
