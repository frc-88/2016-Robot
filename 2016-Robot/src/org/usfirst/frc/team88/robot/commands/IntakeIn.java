package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeIn extends Command {
	private boolean done;
	
    public IntakeIn() {
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!Robot.intake.isBoulderInNest()) {
        	Robot.intake.move(Intake.INTAKE_IN);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.intake.isBoulderInNest();
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
