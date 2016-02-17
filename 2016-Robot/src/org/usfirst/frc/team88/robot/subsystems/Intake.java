package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Intake extends Subsystem {
	private final static double SHOOTER_P = 1.0;
	private final static double SHOOTER_I = 0.0; 
	private final static double SHOOTER_D = 0.0;
	private final static double SHOOTER_SPEED = 0.5;
	private final static double THRESHOLD_SPEED = SHOOTER_SPEED * 0.05;

	private final static double LOADED_DISTANCE = 1.4;
	
	public final static double INTAKE_IN = 0.9;
	public final static double INTAKE_OUT = -1.0;
	
	private final CANTalon intakeTalon;
	private final CANTalon shooterTalon;

	private final AnalogInput lowerNestSensor;
	private final AnalogInput upperNestSensor;
	
	public Intake (){
		intakeTalon = new CANTalon(RobotMap.intakeMotor);
		intakeTalon.enableBrakeMode(true);
		intakeTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		shooterTalon = new CANTalon(RobotMap.shooterMotor);
//		shooterTalon.setPosition(0);
//		shooterTalon.setPID(SHOOTER_P, SHOOTER_I, SHOOTER_D);
//		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		lowerNestSensor = new AnalogInput(RobotMap.lowerNestSensor);
		upperNestSensor = new AnalogInput(RobotMap.upperNestSensor);
	}

	public void move(double speed) {
		intakeTalon.set(speed);
	}
	
	public void startShooter() {
		shooterTalon.set(SHOOTER_SPEED);
	}

	public void stopShooter() {
		shooterTalon.set(0.0);
	}
	
	public boolean isShooterReady(){	
//		return ((SHOOTER_SPEED - THRESHOLD_SPEED <= shooterTalon.getSpeed()) && 
//				(SHOOTER_SPEED + THRESHOLD_SPEED >= shooterTalon.getSpeed()));
		return true;
	}

	public boolean isBoulderInLowerNest() {
		return getLowerNestDistance() < LOADED_DISTANCE;
	}
	
    private double getLowerNestDistance() {
    	SmartDashboard.putNumber("Lower nest value: ", lowerNestSensor.getValue());
    	SmartDashboard.putNumber("Lower nest voltage: ", lowerNestSensor.getVoltage());
    	SmartDashboard.putNumber("Lower nest average voltage: ", lowerNestSensor.getAverageVoltage());
		
    	return ( 4.95 / lowerNestSensor.getVoltage()) - 0.42;
	}
	
	public boolean isBoulderInUpperNest() {
		return getUpperNestDistance() < LOADED_DISTANCE;
	}
	
    private double getUpperNestDistance() {
    	SmartDashboard.putNumber("Upper nest value: ", upperNestSensor.getValue());
    	SmartDashboard.putNumber("Upper nest voltage: ", upperNestSensor.getVoltage());
    	SmartDashboard.putNumber("Upper nest average voltage: ", upperNestSensor.getAverageVoltage());
		
    	return ( 4.95 / upperNestSensor.getVoltage()) - 0.42;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

