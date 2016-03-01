package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.LightsMode;
import org.usfirst.frc.team88.robot.Robot;
import org.usfirst.frc.team88.robot.subsystems.Lights;

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
//		if (ds.isAutonomous()) {
//			// green lights in autonomous
//			Robot.lights.setMode(Lights.Mode.GreenJuggle);
//		} else if (ds.isOperatorControl()) {
//			// disco for last 20 seconds
//			// alliance colors the rest of the time
//			// track the shooter while it is moving
//			if (ds.getMatchTime() < 20) {
//				Robot.lights.setMode(Lights.Mode.Disco);
////			} else if (Robot.intake.getShooterSpeedPercent() > 0.05) {
////				Robot.lights.setAnalogOut(Robot.intake.getShooterSpeedPercent());
//				} else if (Robot.oi.getDriverRightZAxis() > 0.05) {
//				Robot.lights.setAnalogOut(Robot.oi.getDriverRightZAxis());
//				if (ds.getAlliance() == DriverStation.Alliance.Blue) {
//					Robot.lights.setMode(Lights.Mode.BlueAnalog);
//				} else {
//					Robot.lights.setMode(Lights.Mode.RedAnalog);
//				}
//			} else {
//				if (ds.getAlliance() == DriverStation.Alliance.Blue) {
//					Robot.lights.setMode(Lights.Mode.BlueJuggle);
//				} else {
//					Robot.lights.setMode(Lights.Mode.RedJuggle);
//				}
//			}
//		} else if (ds.isDisabled()) {
//			Robot.lights.setMode(Lights.Mode.Rainbow);
//		}

//		Robot.lights.setMode(LightsMode.BlueJuggle);

		Robot.lights.setMode(LightsMode.RedAnalog);
	
		Robot.lights.setAnalogOut(Robot.oi.getDriverRightZAxis());
		SmartDashboard.putNumber("stick", Robot.oi.getDriverRightZAxis());
	
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
