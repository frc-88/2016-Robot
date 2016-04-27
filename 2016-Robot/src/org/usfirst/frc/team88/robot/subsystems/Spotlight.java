package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Spotlight extends Subsystem {
    private Relay lightswitch;

	public Spotlight() {
		lightswitch = new Relay(RobotMap.spotlightPWM, Relay.Direction.kForward);
	}

	public void toggle() {
		if(lightswitch.get() == Relay.Value.kOff) {
			lightswitch.set(Relay.Value.kOn);
		} else {
			lightswitch.set(Relay.Value.kOff);
		}
	}
	
    public void initDefaultCommand() {
    	// no default command
    }
}

