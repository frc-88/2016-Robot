package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Arms;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmsToPosition extends Command {
	private double target;
	private boolean moveUp;
	private double stillCount;
	private double lastPosition;
	private boolean done;

	public ArmsToPosition(double position) {
		requires(Robot.arms);

		target = position;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		double position = Robot.arms.getPosition();

		stillCount = 0;
		if (!Robot.arms.isZeroed()) {
			done = true;
		} else {
			done = false;

			if (position > target) {
				moveUp = true;
				Robot.arms.move(-Arms.AUTO_SPEED);
			} else if (position < target) {
				moveUp = false;
				Robot.arms.move(Arms.AUTO_SPEED);
			} else {
				done = true;
			}
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double position = Robot.arms.getPosition();

		if (!done && moveUp && ((position <= target) || Robot.arms.atRevLimit())) {
			done = true;
		}

		if (!done && !moveUp && ((position >= target) || Robot.arms.atFwdLimit())) {
			done = true;
		}

		// stop if the encoder isn't changing and we're moving
		if (position == lastPosition) {
			if (++stillCount > 5) {
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
