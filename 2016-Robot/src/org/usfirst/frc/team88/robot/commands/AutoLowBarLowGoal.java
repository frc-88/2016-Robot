package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBarLowGoal extends CommandGroup {
    
    public  AutoLowBarLowGoal() {
    	addParallel(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new ArmsDown());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBack3Meters());
    	addSequential(new AutoRotateToAngle(-135.0));
    	addSequential(new AutoForward3MetersFast());
    	addSequential(new IntakeOut());
    	addSequential(new AutoDelay(3.0));
    	addSequential(new IntakeStop());
    }
}
