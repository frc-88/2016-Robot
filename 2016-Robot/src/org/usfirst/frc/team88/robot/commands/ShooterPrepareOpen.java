package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterPrepareOpen extends Command {
	private static final Preferences prefs = Preferences.getInstance();
	private double speed, delay, count, ramp, start;
	
    public ShooterPrepareOpen() {
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	count = 0.0;
    	delay = prefs.getDouble("shooterStartDelay", 1.0) * 50.0;
    	speed = prefs.getDouble("shooterRunVolts", 7.0);
    	ramp = prefs.getDouble("shooterRampRate", 2.0);
    	start = prefs.getDouble("shooterStartVolts", 10.0);

    	Robot.intake.startShooterVoltage(start, start);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (count++ > delay);
        }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.startShooterVoltage(speed, ramp);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.startShooterVoltage(speed, ramp);
    }
}
