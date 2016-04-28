package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.IntakeUpdateSmartDashboard;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *  One boulder goes in
 *  Happy boulder in its nest
 *  What's that noise? Up and away!
 */

public class Intake extends Subsystem {
	
	private static final Preferences prefs = Preferences.getInstance();
	
	
	private final static double SHOOTER_P = 0.5;
	private final static double SHOOTER_I = 0.0; 
	private final static double SHOOTER_D = 0.05;
	private final static double SHOOTER_F = 1.15;
	private final static int SHOOTER_IZONE = 0;
	private final static double SHOOTER_RAMPRATE = 3.0;
	private final static int SHOOTER_PROFILE = 0;

//	private final static double SHOOTER_SPEED = 0.65;
	private static double SHOOTER_SPEED = 1200;
	private static double SHOOTER_TARGET_SPEED = 2380;
	// shooter speed is in position change / 10ms
	// shooter encoder is 128 count, so 512 ticks per rotation
	// so 512 would be 1 rotation per 10ms, or 100 rotations per second
	// or 6000 rpm
	private final static double THRESHOLD_SPEED = SHOOTER_SPEED * 0.05;

	private final static double LOADED_DISTANCE = 1.4;
	
	public final static double INTAKE_IN = 0.9;
	public final static double INTAKE_OUT = -1.0;
	
	private static double shooterLights;
	
	private final CANTalon intakeTalon;
	private final CANTalon shooterTalon;

	private final AnalogInput boulderHolder;
	private boolean shooterRunning;
	
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
	}

	public void move(double speed) {
		intakeTalon.set(speed);
	}
	
	public void startShooter() {
		changeSpeed();
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooterTalon.set(SHOOTER_SPEED);
		shooterRunning = true;
		printShooter();
	}

	public void startShooterOpen(double voltage) {
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		shooterTalon.set(voltage);
		shooterRunning = true;		
	}
	
	public void startShooterVoltage(double runv, double ramp){
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Voltage);
		shooterTalon.set(runv);
		shooterTalon.setVoltageCompensationRampRate(ramp);
		shooterRunning = true;
	}
	
	public void stopShooter() {
		shooterTalon.set(0);
		shooterRunning = false;
		printShooter();
	}
	
	public void printShooter(){
		SmartDashboard.putBoolean("Shooter Running", shooterRunning);
	}
	
	public boolean isShooterReady(){	
		double targetVoltage = prefs.getDouble("shooterRunVolts", 7.0) * 0.9;
		double targetCurrent = prefs.getDouble("shooterTargetCurrent", 1.0);
		
		return ( (shooterTalon.getOutputVoltage() > targetVoltage) && 
				 (shooterTalon.getOutputCurrent() < targetCurrent) );
	}

	public double getShooterSpeed() {
		SmartDashboard.putNumber("Shooter speedx: ", shooterTalon.getSpeed());
		SmartDashboard.putNumber("Shooter position: ", shooterTalon.getEncPosition());
		SmartDashboard.putNumber("Shooter Voltage: ", shooterTalon.getOutputVoltage());
		SmartDashboard.putNumber("Shooter Current: ", shooterTalon.getOutputCurrent());
		
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

    	SHOOTER_SPEED = prefs.getDouble("shooterSpeed", 1050);
    }
    
    public double getShooterAnalog(){
    	shooterLights = shooterTalon.getSpeed() / SHOOTER_SPEED;
    	return shooterLights;
    }
}

