package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PrepareShooter extends Command {

	private final static int START = 0;
	private final static int INTAKE_MOVING = 1;
	private final static int BOULDER_IN_NEST = 2;
	private final static int SHOOTER_MOVING = 3;
	private final static int DONE = 4;
	
	private int state = START;
	
    public PrepareShooter() {
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = START;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.getShooterSpeed();
    	Robot.intake.startShooter();
    	state = DONE;
    	
//    	switch (state) {
//    	case START:
//    		Robot.intake.move(Intake.INTAKE_OUT / 2);
//    		state = INTAKE_MOVING;
//    		break;
//    	case INTAKE_MOVING:
//    		if(Robot.intake.isBoulderInLowerNest()) {
//    			Robot.intake.move(0);
//    			state = BOULDER_IN_NEST;
//   		}
//    		break;
//    	case BOULDER_IN_NEST:
//    		Robot.intake.startShooter();
//        	state = SHOOTER_MOVING;
//        	break;
//    	case SHOOTER_MOVING:
//    		if(Robot.intake.isShooterReady()) {
//    			Robot.oi.setOperatorRumble(0.5f);
//    			state = DONE;
//    		}
//    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == DONE;
    }
    
    

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
