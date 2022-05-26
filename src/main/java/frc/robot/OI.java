package frc.robot;

import java.util.Optional;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * OI - Use this class to access and initialize all controller-related stuff.
 */
public class OI {
  private static Optional<OI> instance = Optional.empty();
  private final Constants constants = Constants.getInstance();

  public static OI getInstance() {
    if (!instance.isPresent()) {
      instance = Optional.of(new OI());
    }
    return instance.get();
  }

  public final XboxController controller = new XboxController(constants.DEVICE_PORT_XBOX_CONTROLLER);

  public final JoystickButton start = new JoystickButton(controller,
      Button.kStart.value);
  public final JoystickButton back = new JoystickButton(controller, Button.kBack.value);

  public final JoystickButton lb = new JoystickButton(controller,
      Button.kLeftBumper.value);
  public final JoystickButton rb = new JoystickButton(controller,
      Button.kRightBumper.value);

  public final JoystickButton lsButton = new JoystickButton(controller, Button.kLeftStick.value);
  public final JoystickButton rsButton = new JoystickButton(controller, Button.kRightStick.value);

  public final JoystickButton a = new JoystickButton(controller, Button.kA.value);
  public final JoystickButton b = new JoystickButton(controller, Button.kB.value);
  public final JoystickButton x = new JoystickButton(controller, Button.kX.value);
  public final JoystickButton y = new JoystickButton(controller, Button.kY.value);

  public final POVButton dPadUp = new POVButton(controller, 0);
  public final POVButton dPadRight = new POVButton(controller, 90);
  public final POVButton dPadDown = new POVButton(controller, 180);
  public final POVButton dPadLeft = new POVButton(controller, 270);
}
