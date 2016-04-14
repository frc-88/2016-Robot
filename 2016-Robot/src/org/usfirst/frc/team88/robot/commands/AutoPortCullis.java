package org.usfirst.frc.team88.robot.commands;

import org.usfirst.frc.team88.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPortCullis extends CommandGroup {
    
    public  AutoPortCullis() {

    	addSequential(new ArmsZero());
    	addSequential(new ArmsToPosition(Robot.arms.POS_FORWARD_LIMIT));
    	addSequential(new AutoForwards(3000, false));
    	addSequential(new ArmsZero());
    }
}
