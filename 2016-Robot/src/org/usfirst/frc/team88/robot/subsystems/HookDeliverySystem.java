package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class HookDeliverySystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private final CANTalon angleTalon;
	private final CANTalon elevatorTalon;
	
	public HookDeliverySystem(){
		angleTalon = new CANTalon(RobotMap.hdsAngleMotorController);
//		angleTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		angleTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		
		elevatorTalon = new CANTalon(RobotMap.hdsElevatorMotorController);
		elevatorTalon.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		elevatorTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}
	
	public void moveAngle(double speed){
		angleTalon.set(speed);
	}
	
	public void moveElevator(double speed){
		elevatorTalon.set(speed);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

