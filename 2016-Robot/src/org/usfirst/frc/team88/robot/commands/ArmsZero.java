package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Arms;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmsZero extends Command {
	private double stillCount;
	private double lastPosition;
	private boolean done;

	public ArmsZero() {
		requires(Robot.arms);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		done = false;
		Robot.arms.move(-Arms.AUTO_SPEED / 2.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double position = Robot.arms.getPosition();

		if (Robot.arms.isZeroed()) {
			done = true;
		}

		// stop if the encoder isn't changing and we're moving
		if (position == lastPosition) {
			if (++stillCount > 100) {
				done = true;
			}
		} else {
			lastPosition = position;
		}

		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.arms.move(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.arms.move(0);
	}
}
