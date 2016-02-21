package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DoNothing extends CommandGroup {
    
    public  DoNothing() {

    	addSequential(new IntakeIn());
    	addParallel(new DontMove());
    }
}
