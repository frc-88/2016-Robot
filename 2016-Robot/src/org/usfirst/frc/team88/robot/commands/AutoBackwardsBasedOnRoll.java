package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBackwardsBasedOnRoll extends CommandGroup {
    
    public  AutoBackwardsBasedOnRoll() {
    	
    	//The angle used (number below) needs to be at lease 8 greater the starting roll value
    	
    	addSequential (new AutoBackwardsToRoll("navXRoll", 8.5, false));
    }
}
