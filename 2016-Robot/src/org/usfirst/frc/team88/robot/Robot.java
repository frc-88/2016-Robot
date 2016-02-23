package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team88.robot.commands.AutoDoNothing;
import org.usfirst.frc.team88.robot.commands.AutoLowBarNoShoot;
import org.usfirst.frc.team88.robot.commands.AutoBack3Meters;
import org.usfirst.frc.team88.robot.subsystems.Arms;
import org.usfirst.frc.team88.robot.subsystems.Climber;
import org.usfirst.frc.team88.robot.subsystems.Drive;
import org.usfirst.frc.team88.robot.subsystems.HookDeliverySystem;
import org.usfirst.frc.team88.robot.subsystems.Intake;
import org.usfirst.frc.team88.robot.subsystems.Lights;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static OI oi;
	public static Drive drive;
	public static Intake intake;
	public static Arms arms;
	public static Climber climber;
	public static HookDeliverySystem HDS;
	public static Lights lights;
	public static CameraServer server;

	Command autonomousCommand;
	SendableChooser autoSelector;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		drive = new Drive();
		intake = new Intake();
		arms = new Arms();
		climber = new Climber();
		HDS = new HookDeliverySystem();
		lights = new Lights();

		oi = new OI();

		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture(RobotMap.cameraName);

		// set up the SmartDashboard
		// set up SendableChooser to select autonomous mode
		autoSelector = new SendableChooser();
		autoSelector.addDefault("Do Nothing", new AutoDoNothing());
		autoSelector.addObject("Backwards 3m", new AutoBack3Meters());
		autoSelector.addObject("Low Bar No Shoot", new AutoLowBarNoShoot());
		SmartDashboard.putData("Auto Mode", autoSelector);

		// Buttons for testing autonomous commands
		SmartDashboard.putData("Do Nothing", new AutoDoNothing());
		SmartDashboard.putData("Backwards 3m", new AutoBack3Meters());
		SmartDashboard.putData("Low Bar No Shoot", new AutoLowBarNoShoot());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		// schedule the autonomous command
		autonomousCommand = (Command) autoSelector.getSelected();

		if ((autonomousCommand == null)) {
			autonomousCommand = new AutoDoNothing();
		}

		autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
