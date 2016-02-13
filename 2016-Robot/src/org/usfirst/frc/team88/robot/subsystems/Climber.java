package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
    
	private final DoubleSolenoid releaseSolenoid;
	
	private  boolean enabled;

	public Climber(){
    	releaseSolenoid = new DoubleSolenoid(RobotMap.climberSolenoidIn, RobotMap.climberSolenoidOut);
    	releaseSolenoid.set(Value.kReverse);
    	
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
			releaseSolenoid.set(Value.kForward);
		}
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

