package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.IntakeUpdateSmartDashboard;

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
	private final static double SHOOTER_SPEED = 0.65;
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
		shooterTalon.setFeedbackDevice(CANTalon.FeedbackDevice.EncRising);
//		shooterTalon.setPosition(0);
//		shooterTalon.setPID(SHOOTER_P, SHOOTER_I, SHOOTER_D);
//		shooterTalon.changeControlMode(CANTalon.TalonControlMode.Speed);
		shooterTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		boulderHolder = new AnalogInput(RobotMap.boulderHolder);
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

	public double getShooterSpeed() {
		SmartDashboard.putNumber("Shooter speed: ", shooterTalon.getSpeed());
		SmartDashboard.putNumber("Shooter position: ", shooterTalon.getEncPosition());
		
		return shooterTalon.getSpeed();
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
}

