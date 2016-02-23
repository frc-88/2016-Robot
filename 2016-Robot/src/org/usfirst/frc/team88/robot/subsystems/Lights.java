package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.LightsShow;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lights extends Subsystem {
    
	public static enum Mode {
//		0 = Rainbow = Rest = 0 0 0
//		1 = Red Juggle = Red Alliance = 1 0 0
//		2 = Blue Juggle = Blue Alliance = 0 1 0
//		3 = Green Juggle = Auto Green = 1 1 0
//		4 = Disco = Disco Climbing/ Idle? = 0 0 1
//		5 = Red SineLon = Shooter Warming Up/ Ready: Analog = 0 1 1
//		6 = Blue SineLon = Shooter Warming Up/ Ready: Analog = 1 0 1
//		7 = Nothing = Nothing = 111
		Rainbow 	(false, false, false),
		RedJuggle	(false, false, true ),
		BlueJuggle	(false, true,  false),
		GreenJuggle	(false, true,  true ),
		Disco		(true,  false, false),
		RedAnalog	(true,  false, true ),
		BlueAnalog	(true,  true,  false),
		Nothing		(true,  true,  true );
		
		private boolean bit1, bit2, bit3;
		
		Mode(boolean bit1, boolean bit2 , boolean bit3) {
			this.bit1 = bit1;
			this.bit2 = bit2;
			this.bit3 = bit3;
		}
		
		public boolean getBit1() {
			return bit1;
		}

		public boolean getBit2() {
			return bit1;
		}

		public boolean getBit3() {
			return bit1;
		}
	}

	private DigitalOutput digitalOut1, digitalOut2, digitalOut3;
	private Mode currentMode;
	
	public Lights(){
		digitalOut1 = new DigitalOutput(RobotMap.lightsDigitalOut1);
		digitalOut2 = new DigitalOutput(RobotMap.lightsDigitalOut2);
		digitalOut3 = new DigitalOutput(RobotMap.lightsDigitalOut3);
		
		currentMode = Mode.Nothing;
		setMode(currentMode);
	}
	
	public void setMode(Mode mode) {
		digitalOut1.set(mode.getBit1());
		digitalOut2.set(mode.getBit1());
		digitalOut3.set(mode.getBit3());
		currentMode = mode;
	}
	
	public Mode getMode() {
		return currentMode;
	}
	
    public void initDefaultCommand() {
        setDefaultCommand(new LightsShow());
    }
}

