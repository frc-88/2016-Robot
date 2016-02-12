package org.usfirst.frc.team88.robot;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Notifier;

public class MotionProfile {
	
	CANTalon.MotionProfileStatus status = new CANTalon.MotionProfileStatus();
	static int numPoints = 0;
	private CANTalon _talon;
	
	public static double[][] Points = new double[numPoints][3];
	
	/**Define our points here
	 * [0][0] = first point, position
	 * [0][1] = first point, velocity
	 * [0][2] = first point, duration
	 * [1][0] = second point, position
	 */
	class PeriodicRunnable implements java.lang.Runnable {
	    public void run() {  _talon.processMotionProfileBuffer();    }
	}
	Notifier notifier = new Notifier(new PeriodicRunnable());
	
	public void sendPoints (double[][] profile, CANTalon talon){
		CANTalon.TrajectoryPoint point = new CANTalon.TrajectoryPoint();
		point.isLastPoint = false;
		point.zeroPos = false;
		
		notifier.startPeriodic(0.005);
		
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
	
	public void fireMotionProfile(CANTalon talon){
		talon.set(CANTalon.SetValueMotionProfile.valueOf(1));
	}

}


