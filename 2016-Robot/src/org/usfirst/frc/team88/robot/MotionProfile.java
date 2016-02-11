package org.usfirst.frc.team88.robot;

import java.util.Timer;
import java.util.TimerTask;

import edu.wpi.first.wpilibj.CANTalon;

public class MotionProfile {
	
	CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	static int numPoints = 0;
	
	public static double[][] Points = new double[numPoints][3];
	
	/**Define our points here
	 * [0][0] = first point, position
	 * [0][1] = first point, velocity
	 * [0][2] = first point, duration
	 * [1][0] = second point, position
	 */
	
	public void sendPoints (double[][] profile, CANTalon talon){
		Timer timer = new Timer();
		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		point.isLastPoint = false;
		point.zeroPos = false;
		
		talon.getMotionProfileStatus(status);
		
		talon.clearMotionProfileTrajectories();
		
		if(status.hasUnderrun){
			//Just an alert, we can make it whatever we want later
			System.out.println("You're not feeding the Talon data fast enough!");
			
			talon.clearMotionProfileHasUnderrun();
		}
		
		for(int i = 0; i<= profile.length; i++){
			point.position = profile[i][0];
			point.velocity = profile[i][1];
			point.timeDurMs = (int) profile[i][2];
			
			
			
			if(i==0){
				point.zeroPos = true;
			}
			
			if((i+1) == profile.length){
				point.isLastPoint = true;
			}
			
			talon.pushMotionProfileTrajectory(point);
		}
	}
	
	public void startMotionProfile(CANTalon talon){
		//The example just had a boolean turning true, I didn't see it connected to anything, so
		//I'll have to figure this out
		
	}
	
	public void resetTalon(CANTalon talon){
		talon.clearMotionProfileTrajectories();
	}
	
	class CheckBuffer extends TimerTask(CANTalon talon){
		talon.processMotionProfileBuffer();
	}
	@Override
	public void run() {
		talon.processMotionProfileBuffer();
	}
}
}


