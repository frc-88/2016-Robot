package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPassiveNoShoot extends CommandGroup {
    
    public  AutoPassiveNoShoot() {
    	addParallel(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBack3MetersFast());
    }
}
