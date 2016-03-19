package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.LightsMode;
import org.usfirst.frc.team88.robot.Robot;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LightsShow extends Command {

	public LightsShow() {
		requires(Robot.lights);
	}

	DriverStation ds;

	// Called just before this Command runs the first time
	protected void initialize() {
		ds = DriverStation.getInstance();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if ((boolean) Robot.lights.testChooser.getSelected()) {
			Robot.lights.setSelectedMode();
		} else {
			if (ds.isAutonomous()) {
				// green lights in autonomous
				Robot.lights.setMode(LightsMode.GreenJuggle);
			} else if (ds.isOperatorControl()) {
				// rainbow for last 20 seconds
				// alliance colors the rest of the time
				if (Robot.intake.getShooterAnalog() > 0.07){
						if (ds.getAlliance() == DriverStation.Alliance.Blue){
							Robot.lights.setMode(LightsMode.BlueAnalog);
						}
						else{
							Robot.lights.setMode(LightsMode.RedAnalog);
						}
				}
				else{
					if (ds.getMatchTime() < 20) {
						Robot.lights.setMode(LightsMode.Rainbow);
					} else if (ds.getAlliance() == DriverStation.Alliance.Blue) {
						Robot.lights.setMode(LightsMode.BlueJuggle);
					} else {
						Robot.lights.setMode(LightsMode.RedJuggle);
					}
				}
			} else if (ds.isDisabled()) {
				Robot.lights.setMode(LightsMode.Rainbow);
			}

		}


		Robot.lights.setAnalogOut(Robot.intake.getShooterAnalog());
		//SmartDashboard.putNumber("stick", Robot.oi.getDriverRightZAxis());

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
