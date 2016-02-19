package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FireShooter extends Command {
	private int count;
	
	private static final int DELAY = 50; // number of 20ms seconds to wait before ending 
	
    public FireShooter() {
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	count = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	if(Robot.intake.isShooterReady()){
    		Robot.intake.move(Intake.INTAKE_IN);
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (!Robot.intake.isBoulderInLowerNest()) {
    		if (count ++ >= DELAY) { 
    			return true;
    		}
    	}
    	
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	Robot.intake.move(0);
//    	Robot.intake.stopShooter();
//    	Robot.oi.setOperatorRumble(0.0f);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
