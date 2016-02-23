package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBarNoShoot extends CommandGroup {
    
    public  AutoLowBarNoShoot() {

    	//addSequential(new IntakeIn());
    	//addSequential(new ArmsDown());
    	addParallel(new DriveBackwards3Meters());
    	
    }
}
