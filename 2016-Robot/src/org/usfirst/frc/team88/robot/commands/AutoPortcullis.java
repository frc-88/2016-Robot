package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPortcullis extends CommandGroup {
    
    public  AutoPortcullis() {

    	addSequential(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new IntakeStop());
    	addSequential(new ArmsToPosition(Robot.arms.POS_FORWARD_LIMIT));
    	addSequential(new AutoForwards(4000, true));
    	addSequential(new ArmsZero());
    }
}
