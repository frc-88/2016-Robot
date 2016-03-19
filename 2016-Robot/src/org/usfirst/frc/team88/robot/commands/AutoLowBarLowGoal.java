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
    	addSequential(new AutoBackwardsToDistance("AutoLowBarLowGoalLeg1",5000));
    	addSequential(new AutoRotateToAngle(150.0f));
    	addSequential(new AutoForwards("AutoLowBarLowGoalLeg2",3000));
    	addSequential(new IntakeOut());
    	addSequential(new AutoDelay(3.0));
    	addSequential(new IntakeStop());
    }
}
