package org.usfirst.frc.team88.robot;

import org.usfirst.frc.team88.robot.commands.DrivePark;
import org.usfirst.frc.team88.robot.commands.DriveWithControllerClosed;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	private static final int LEFT_HORIZ_AXIS = 0;
    private static final int LEFT_VERT_AXIS = 1;
    private static final int RIGHT_HORIZ_AXIS = 4;
    private static final int RIGHT_VERT_AXIS = 5;
    private static final int LEFT_Z_AXIS = 2;
    private static final int RIGHT_Z_AXIS =3;
    
    private Joystick driverController = new Joystick(0);
    private Button driverButtonA = new JoystickButton(driverController, 1);
    private Button driverButtonB = new JoystickButton(driverController, 2);
    private Button driverButtonX = new JoystickButton(driverController, 3);
    private Button driverButtonY = new JoystickButton(driverController, 4);
    private Button driverButtonLeftBumper = new JoystickButton(driverController, 5);
    private Button driverButtonRightBumper = new JoystickButton(driverController, 6);

    public OI () {
        driverButtonLeftBumper.whenPressed(new DrivePark());
        driverButtonRightBumper.whenPressed(new DriveWithControllerClosed());
    }
     
    public double getDriverRightVerticalAxis() {
        return -driverController.getRawAxis(RIGHT_VERT_AXIS);
    }
    
    public double getDriverRightHorizontalAxis() {
        return driverController.getRawAxis(RIGHT_HORIZ_AXIS);
    }
    
    public double getDriverLeftVerticalAxis() {
        return -driverController.getRawAxis(LEFT_VERT_AXIS);
    }     
    public double getDriverLeftHorizontalAxis() {
        return driverController.getRawAxis(LEFT_HORIZ_AXIS);
    }
    public double getDriverLeftZAxis() {
        return driverController.getRawAxis(LEFT_Z_AXIS);
    }
    public double getDriverRightZAxis() {
        return driverController.getRawAxis(RIGHT_Z_AXIS);
    }
   
}

