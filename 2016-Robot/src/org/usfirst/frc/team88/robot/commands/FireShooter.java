package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FireShooter extends Command {

    public FireShooter() {
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(Robot.intake.isShooterReady()){
    		Robot.intake.move(Intake.INTAKE_IN);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    		return !Robot.intake.isBoulderInLowerNest();
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
