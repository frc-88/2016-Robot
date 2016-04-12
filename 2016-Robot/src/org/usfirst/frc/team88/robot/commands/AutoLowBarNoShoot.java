package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBarNoShoot extends CommandGroup {
    
    public  AutoLowBarNoShoot() {
    	addParallel(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new ArmsDown());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwards("Dist_LBNS", 6000));
    }
}
