package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.IntakeUpdateSmartDashboard;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Intake extends Subsystem {
	
	Preferences prefs;
	
	private final static double SHOOTER_P = 0.5;
	private final static double SHOOTER_I = 0.0; 
	private final static double SHOOTER_D = 0.05;
	private final static double SHOOTER_F = 1.15;
	private final static int SHOOTER_IZONE = 0;
	private final static double SHOOTER_RAMPRATE = 3.0;
	private final static int SHOOTER_PROFILE = 0;

//	private final static double SHOOTER_SPEED = 0.65;
	private static double SHOOTER_SPEED = 1200;
	// shooter speed is in position change / 10ms
	// shooter encoder is 128 count, so 512 ticks per rotation
	// so 512 would be 1 rotation per 10ms, or 100 rotations per second
	// or 6000 rpm
	private final static double THRESHOLD_SPEED = SHOOTER_SPEED * 0.05;

	private final static double LOADED_DISTANCE = 1.4;
	
	public final static double INTAKE_IN = 0.9;
	public final static double INTAKE_OUT = -1.0;
	
	private final CANTalon intakeTalon;
	private final CANTalon shooterTalon;

	private final AnalogInput boulderHolder;
	
	public Intake (){
		intakeTalon = new CANTalon(RobotMap.intakeMotor);
		intakeTalon.enableBrakeMode(true);
		intakeTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		shooterTalon = new CANTalon(RobotMap.shooterMotor);
		shooterTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		shooterTalon.reverseSensor(true);
		shooterTalon.setPosition(0);
		shooterTalon.setPID(SHOOTER_P, SHOOTER_I, SHOOTER_D, SHOOTER_F, SHOOTER_IZONE, SHOOTER_RAMPRATE,SHOOTER_PROFILE);
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
//		shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		boulderHolder = new AnalogInput(RobotMap.boulderHolder);
    
		prefs = Preferences.getInstance();
    	prefs.getDouble("shooterDistance", 24.0);
    	prefs.getDouble("shooterSpeed", SHOOTER_SPEED);
	}

	public void move(double speed) {
		intakeTalon.set(speed);
	}
	
	public void startShooter() {
		changeSpeed();
		shooterTalon.set(SHOOTER_SPEED);
		
	}

	public void stopShooter() {
		shooterTalon.set(0);
	}
	
	public boolean isShooterReady(){	
		return ( ( ( SHOOTER_SPEED - THRESHOLD_SPEED ) <= ( shooterTalon.getSpeed() * 10 ) ) && 
				 ( ( SHOOTER_SPEED + THRESHOLD_SPEED ) >= ( shooterTalon.getSpeed() * 10 ) ) );
	}

	public double getShooterSpeed() {
		SmartDashboard.putNumber("Shooter speedx: ", shooterTalon.getSpeed());
		SmartDashboard.putNumber("Shooter position: ", shooterTalon.getEncPosition());
		
		return shooterTalon.getSpeed();
	}
	
	public double getShooterSpeedPercent() {
		return ( shooterTalon.getSpeed() * 10 ) / SHOOTER_SPEED;
	}
	
	public boolean isBoulderInHolder() {
		return getBoulderHolderDistance() < LOADED_DISTANCE;
	}
	
    private double getBoulderHolderDistance() {
    	SmartDashboard.putNumber("Boulder holder value: ", boulderHolder.getValue());
    	SmartDashboard.putNumber("Boulder holder voltage: ", boulderHolder.getVoltage());
    	SmartDashboard.putNumber("Boulder holder average voltage: ", boulderHolder.getAverageVoltage());
		
    	return ( 4.95 / boulderHolder.getVoltage()) - 0.42;
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new IntakeUpdateSmartDashboard());
    }
    
    public void changeSpeed() {
    	prefs = Preferences.getInstance();
    	SHOOTER_SPEED = prefs.getDouble("shooterSpeed", 1200);
    }
}

