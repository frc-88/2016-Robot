package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCDF extends CommandGroup {
    
    public  AutoCDF() {
    	
    	addSequential(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new AutoForwards(0, 11, 30, false));
    	addSequential(new ArmsToPosition(Robot.arms.POS_FORWARD_LIMIT));
    	addSequential(new AutoForwards(3000, 0, true));
    	addParallel(new AutoDelay(900));
    	addSequential(new ArmsZero());
    	
    }
}
