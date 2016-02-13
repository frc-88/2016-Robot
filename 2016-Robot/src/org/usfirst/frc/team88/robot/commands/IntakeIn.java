package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeIn extends Command {
	
	private final static int BALL_IS_NOT_IN_NEST = 0;
	private final static int BALL_IS_IN_NEST = 1;
	private final static int BALL_HAS_PASSED_NEST = 2;
	
	private int state = BALL_IS_NOT_IN_NEST;
	
    public IntakeIn() {
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = BALL_IS_NOT_IN_NEST;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state){
    	case BALL_IS_NOT_IN_NEST:
    		if(!Robot.intake.isBoulderInNest()){
    			Robot.intake.move(Intake.INTAKE_IN);
    		}
    		state = BALL_IS_IN_NEST;
    		break;
    	case BALL_IS_IN_NEST:
    		Robot.intake.move(Intake.INTAKE_IN/2);
    		if(!Robot.intake.isBoulderInNest()){
    			state = BALL_HAS_PASSED_NEST;
    		}
    		break;
    	case BALL_HAS_PASSED_NEST:
    		Robot.intake.move(0);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state ==BALL_HAS_PASSED_NEST;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.move(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
		Robot.intake.move(0);
    }
}
