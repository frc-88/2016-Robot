package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.Joystick;

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

