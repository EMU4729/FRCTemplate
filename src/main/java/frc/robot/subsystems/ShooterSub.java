package frc.robot.subsystems;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSub extends SubsystemBase {
    private final Constants cnst = Constants.getInstance();
    private final MotorController motor = cnst.SHOOTER_MOTOR.createMotorController();

    public void setSpeed(double speed) {
        speed = MathUtil.clamp(speed, -1, 1);
        motor.set(speed);
    }
}
