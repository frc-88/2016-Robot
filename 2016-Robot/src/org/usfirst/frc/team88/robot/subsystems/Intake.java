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
	private final static double P = 1.0;
	private final static double I = 0.0; 
	private final static double D = 0.0;
	private final static double LOADED_DISTANCE = 6.0;
	private final static double SHOOTER_SPEED = 10.0;
	private final static double THRESHOLD_SPEED = SHOOTER_SPEED * 0.05;
	
	public final static double INTAKE_IN = 0.5;
	public final static double INTAKE_OUT = -0.5;
	
	private final CANTalon intakeTalon;
	private final CANTalon shooterTalon;

	private final AnalogInput nestSensor;
	
	public Intake (){
		intakeTalon = new CANTalon(RobotMap.intakeMotor);
		intakeTalon.enableBrakeMode(true);
		intakeTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		shooterTalon = new CANTalon(RobotMap.shooterMotor);
		shooterTalon.setPosition(0);
		shooterTalon.setPID(P, I, D);
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
		
		nestSensor = new AnalogInput(RobotMap.nestSensor);
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
		return ((SHOOTER_SPEED - THRESHOLD_SPEED <= shooterTalon.getSpeed()) && 
				(SHOOTER_SPEED + THRESHOLD_SPEED >= shooterTalon.getSpeed()));
	}

	public boolean isBoulderInNest() {
		return getDistance() < LOADED_DISTANCE;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    private double getDistance() {
    	SmartDashboard.putNumber("IR sensor value: ", nestSensor.getValue());
    	SmartDashboard.putNumber("IR sensor voltage: ", nestSensor.getVoltage());
    	SmartDashboard.putNumber("IR sensor average voltage: ", nestSensor.getAverageVoltage());
		
    	return ( 4.95 / nestSensor.getAverageVoltage()) - 0.42;
	}
	
}

