package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBarLowGoal extends CommandGroup {
    
    public  AutoLowBarLowGoal() {
    	addSequential(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new ArmsDown());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwardsToDistance("Dist_LBLG_1", 4000));
    	addSequential(new AutoRotateToAngle("Angle_LBLG", -120.0f));
    	addSequential(new AutoForwards("Dist_LBLG_2", 3000));
    	addSequential(new IntakeOut());
    	addSequential(new AutoDelay(3.0));
    	addSequential(new IntakeStop());
    }
}
