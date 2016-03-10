package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterFire2 extends Command {
	
	Preferences prefs;
	
	private int count;
	private boolean fired;

	private static double DISTANCE = 24.0;
	private static final int DELAY = 30; // number of 20ms seconds to wait
										 // before ending after we fire

	public ShooterFire2() {
		requires(Robot.intake);
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		count = 0;
		fired = false;
		adjustDistance();

		Robot.drive.set(-0.1, -0.1);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if ( !fired && (Robot.lidar.getDistance() > DISTANCE) ) {
			Robot.intake.move(Intake.INTAKE_IN);
			fired = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (fired) {
			if (count++ >= DELAY) {
				return true;
			}
		}

		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intake.move(0);
		Robot.intake.stopShooter();
		Robot.drive.set(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.intake.move(0);
		Robot.intake.stopShooter();
		Robot.drive.set(0, 0);
	}
	
	protected void adjustDistance() {
    	prefs = Preferences.getInstance();
    	DISTANCE = prefs.getDouble("shooterDistance", 24.0);
	}
}
