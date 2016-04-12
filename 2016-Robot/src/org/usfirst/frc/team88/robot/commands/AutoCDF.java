package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCDF extends CommandGroup {
    
    public  AutoCDF() {
    	
    	addSequential(new ArmsZero());
    	addSequential(new AutoForwards(0, 11, 30, false));
    	addSequential(new ArmsToPosition(150000));
    	addSequential(new AutoForwards(3000, 0, true));
    	addParallel(new AutoDelay(900));
    	addSequential(new ArmsZero());
    	
    }
}
