package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
    
	private final CANTalon climberTalon;
	
	private final double speed;
	private  boolean enabled;

	public Climber(){
		
		climberTalon = new CANTalon(RobotMap.climberMotor);
		
		speed = 0.5;
    	enabled = false;
	}
	
	public void enable(){
		enabled = true;
	}
	
	public void disable(){
		enabled = false;
	}
	
	public void fire(){
		if(enabled){
			climberTalon.set(speed);
		}
	}
	
	public void unFire(){
		climberTalon.set(0);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

