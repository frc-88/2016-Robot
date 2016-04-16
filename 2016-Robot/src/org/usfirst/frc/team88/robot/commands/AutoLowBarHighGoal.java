package org.usfirst.frc.team88.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoLowBarHighGoal extends CommandGroup {
    
    public  AutoLowBarHighGoal() {
    	addSequential(new IntakeIn());
    	addSequential(new ArmsZero());
    	addSequential(new ArmsDown());
    	addSequential(new IntakeStop());
    	addSequential(new AutoBackwardsToDistance("Dist_LBHG_1", 4000));
    	addParallel(new AutoDelay(1.3));
    	addSequential(new IntakeOut());
    	addParallel(new Delay(0.03));
    	addSequential(new IntakeStop());
    	addSequential(new ShooterPrepare());
    	addSequential(new AutoRotateToAngle("Angle_LBHG", 60.0f));
    	addSequential(new AutoBackwardsToRoll("Dist_LBHG_2", 3000));
    	addSequential(new ShooterFire());
    }
}
