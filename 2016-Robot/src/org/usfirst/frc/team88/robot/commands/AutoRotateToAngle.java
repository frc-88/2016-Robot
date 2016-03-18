package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoRotateToAngle extends Command {
	private float target;

	public AutoRotateToAngle(float angle) {
		requires(Robot.drive);

		target = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drive.turnController.setSetpoint(target);
		Robot.drive.turnController.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.updateSmartDashboard();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.drive.turnController.onTarget();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.turnController.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.turnController.disable();
	}
}
