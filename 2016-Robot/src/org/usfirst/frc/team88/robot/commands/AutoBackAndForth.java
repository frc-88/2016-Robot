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
    	addSequential(new AutoBack3MetersFast());
    	addSequential(new AutoForward3MetersFast());
    }
}
