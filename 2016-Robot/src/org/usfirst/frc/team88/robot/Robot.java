package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team88.robot.commands.AutoDoNothing;
import org.usfirst.frc.team88.robot.commands.AutoForward3MetersFast;
import org.usfirst.frc.team88.robot.commands.AutoForwards;
import org.usfirst.frc.team88.robot.commands.AutoLowBarLowGoal;
import org.usfirst.frc.team88.robot.commands.AutoLowBarNoShoot;
import org.usfirst.frc.team88.robot.commands.AutoPassiveNoShoot;
import org.usfirst.frc.team88.robot.commands.AutoPortcullis;
import org.usfirst.frc.team88.robot.commands.AutoRotateToAngle;
import org.usfirst.frc.team88.robot.commands.DrivePark2;
import org.usfirst.frc.team88.robot.commands.IntakeIn;
import org.usfirst.frc.team88.robot.commands.IntakeOut;
import org.usfirst.frc.team88.robot.commands.IntakeStop;
import org.usfirst.frc.team88.robot.commands.ArmsDown;
import org.usfirst.frc.team88.robot.commands.ArmsZero;
import org.usfirst.frc.team88.robot.commands.AutoBackAndForth;
import org.usfirst.frc.team88.robot.commands.AutoBackwards;
import org.usfirst.frc.team88.robot.commands.AutoBackwardsBasedOnRoll;
import org.usfirst.frc.team88.robot.commands.AutoBackwardsToDistance;
import org.usfirst.frc.team88.robot.commands.AutoCDF;
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
	public static Lidar lidar;

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
		//climber = new Climber();
		//HDS = new HookDeliverySystem();
		lights = new Lights();
		lidar = new Lidar(I2C.Port.kOnboard);
		
		oi = new OI();

		lidar.start();
		
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture(RobotMap.cameraName);

		// set up the SmartDashboard
		// set up SendableChooser to select autonomous mode
		autoSelector = new SendableChooser();
		autoSelector.addDefault("Do Nothing", new AutoDoNothing());
		autoSelector.addObject("Low Bar No Shoot", new AutoLowBarNoShoot());
		autoSelector.addObject("Passive No Shoot", new AutoPassiveNoShoot());
		autoSelector.addObject("Low Bar Low Goal", new AutoLowBarLowGoal());
		autoSelector.addObject("Portcullis No Shoot", new AutoPortcullis());
		SmartDashboard.putData("Auto Mode", autoSelector);

		// Buttons for testing autonomous commands
		SmartDashboard.putData("Do Nothing", new AutoDoNothing());
		SmartDashboard.putData("Low Bar No Shoot", new AutoLowBarNoShoot());
		SmartDashboard.putData("Passive No Shoot", new AutoPassiveNoShoot());
		SmartDashboard.putData("Low Bar Low Goal", new AutoLowBarLowGoal());
		SmartDashboard.putData("Portcullis No Shoot", new AutoPortcullis());
		SmartDashboard.putData("CDF No Shoot", new AutoCDF());
		
		// Buttons for testing component commands
		SmartDashboard.putData("ArmsZero", new ArmsZero());
		SmartDashboard.putData("ArmsDown", new ArmsDown());

		SmartDashboard.putData("Intake In", new IntakeIn());
		SmartDashboard.putData("Intake Out", new IntakeOut());
		SmartDashboard.putData("Intake Stop", new IntakeStop());
		
		SmartDashboard.putData("Drive LBNS", new AutoBackwards("Dist_LBNS", 6000));
		SmartDashboard.putData("Drive PNS", new AutoBackwards("Dist_PNS", 9000, true));
		SmartDashboard.putData("Drive LBLG_1", new AutoBackwardsToDistance("Dist_LBLG_1", 4000));
		SmartDashboard.putData("Angle LBLG", new AutoRotateToAngle("Angle_LBLG", 120.0f));
		SmartDashboard.putData("Drive LBLG_2", new AutoForwards("Dist_LBLG_2", 3000));
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		intake.stopShooter();
		oi.rumbleDriverLeft(0f);
		oi.rumbleDriverRight(0f);
		oi.rumbleOperatorLeft(0f);
		oi.rumbleOperatorRight(0f);
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
		intake.stopShooter();
		oi.rumbleDriverLeft(0f);
		oi.rumbleDriverRight(0f);
		oi.rumbleOperatorLeft(0f);
		oi.rumbleOperatorRight(0f);
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
