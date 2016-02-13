package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.MoveArmsWithController;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Arms extends Subsystem {
    
	private final CANTalon armTalon;

	public Arms(){
		armTalon = new CANTalon(RobotMap.armMotorController);
		armTalon.enableBrakeMode(true);
		armTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
	public void move(double speed){
		armTalon.set(speed);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new MoveArmsWithController());
    }
}

