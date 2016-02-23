package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Lights;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LightsShow extends Command {

    public LightsShow() {
    	requires(Robot.lights);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriverStation ds = DriverStation.getInstance();
    	
		if (ds.isAutonomous()) {
			// green lights in autonomous
			Robot.lights.setMode(Lights.Mode.GreenJuggle);
		} else if (ds.isOperatorControl()) {
			// alliance colors in teleop, except for last 20 seconds of the match
			if (ds.getMatchTime() > 20) {
				if (ds.getAlliance() == DriverStation.Alliance.Blue) {
					Robot.lights.setMode(Lights.Mode.BlueJuggle);
				} else {
					Robot.lights.setMode(Lights.Mode.RedJuggle);
				}
			} else {
				Robot.lights.setMode(Lights.Mode.Disco);
			}
		} else if (ds.isDisabled()) {
			Robot.lights.setMode(Lights.Mode.Rainbow);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
