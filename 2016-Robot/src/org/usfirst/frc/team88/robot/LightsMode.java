package org.usfirst.frc.team88.robot;

public enum LightsMode {
	// 0 = Rainbow = Rest = 0 0 0
	// 1 = Red Juggle = Red Alliance = 1 0 0
	// 2 = Blue Juggle = Blue Alliance = 0 1 0
	// 3 = Green Juggle = Auto Green = 1 1 0
	// 4 = Disco = Disco Climbing/ Idle? = 0 0 1
	// 5 = Red SineLon = Shooter Warming Up/ Ready: Analog = 0 1 1
	// 6 = Blue SineLon = Shooter Warming Up/ Ready: Analog = 1 0 1
	// 7 = Nothing = Nothing = 111
	Rainbow		(false, false, false), 
	RedJuggle	(true,  false, false), 
	BlueJuggle	(false, true,  false), 
	GreenJuggle	(true,  true,  false), 
	Disco 		(false, false, true),
	RedAnalog 	(false, true,  true),
	BlueAnalog	(true,  false, true),
	Nothing		(true,  true,  true);

	private boolean bit1, bit2, bit3;

	LightsMode(boolean bit1, boolean bit2, boolean bit3) {
		this.bit1 = bit1;
		this.bit2 = bit2;
		this.bit3 = bit3;
	}

	public boolean getBit1() {
		return bit1;
	}

	public boolean getBit2() {
		return bit2;
	}

	public boolean getBit3() {
		return bit3;
	}
}
