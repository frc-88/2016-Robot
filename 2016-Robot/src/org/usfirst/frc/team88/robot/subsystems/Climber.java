package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.ClimberWithController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
    
	private final CANTalon climberTalon;
	
	public Climber(){
		climberTalon = new CANTalon(RobotMap.climberMotor);
	}
	
	public void move(double speed) {
		climberTalon.set(speed);
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new ClimberWithController());
    }
}

