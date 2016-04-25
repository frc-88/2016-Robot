package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBarShuttle extends CommandGroup {
    
    public  AutoLowBarShuttle() {
    	
    	addSequential(new ArmsZero());
    	addParallel(new IntakeIn());
    	addSequential(new ArmsDown());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwards("Dist_LBS_1", 6000));
    	addSequential(new AutoRotateToAngle(-120));
    	addSequential(new IntakeOut());
    	addParallel(new AutoDelay(1.1));
    	addSequential(new IntakeStop());
    	addSequential(new AutoRotateToAngle(-180));
//    	addSequential(new AutoBackwards("Dist_LBS_2",3000));
//    	addSequential(new AutoRotateToAngle(-90));
    }
}
