package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.CANTalon.MotionProfileStatus;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveMotionProfile extends Command {

	// assume < 128 points
	private static double [][]Points = new double[][]{		
		{0,	0	,10},
		{0.00004761904762,	0.5714285714	,10}};
	
    public DriveMotionProfile() {
    	requires (Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// push all points to both master talons
    	// set talons to motion profile control mode
    	// enable motion profile control
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        // return true when active point's isLast flag is true
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	// disable motion profile control
    	// return to speed control mode
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	// same as end
    }
}
