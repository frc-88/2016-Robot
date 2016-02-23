package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDoNothing extends CommandGroup {
    
    public  AutoDoNothing() {

    	addSequential(new IntakeIn());
    	addParallel(new ArmsZero());
    }
}
