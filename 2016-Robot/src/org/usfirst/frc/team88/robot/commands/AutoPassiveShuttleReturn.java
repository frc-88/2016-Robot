package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPassiveShuttleReturn extends CommandGroup {
    
    public  AutoPassiveShuttleReturn() {
    	
    	addParallel(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwards("Dist_PS_1", 6100, false));
    	addSequential(new ArmsDown());
    	addSequential(new AutoRotateToAngle(-180));
    	addSequential(new IntakeOut());
    	addParallel(new AutoDelay(1.1));
    	addSequential(new ArmsZero());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwards("Dist_PS_2", 6000, false));
    	addSequential(new AutoRotateToAngle(-90));
    }
}
