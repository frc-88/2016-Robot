package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoBackAndForth extends CommandGroup {
    
    public  AutoBackAndForth() {
    	addParallel(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwards(9000, true));
    	addSequential(new Delay(0.5));
    	addSequential(new AutoForwards(4000, false));
    }
}
