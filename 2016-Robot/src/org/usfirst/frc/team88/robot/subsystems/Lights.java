package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.LightsMode;
import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.LightsShow;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Lights extends Subsystem {

	private DigitalOutput digitalOut1, digitalOut2, digitalOut3;
	private AnalogOutput analogOut;
	private LightsMode currentMode;

	private double ARDUINO_MAX_VOLTAGE = 5.0;

	public Lights() {
		digitalOut1 = new DigitalOutput(RobotMap.lightsDigitalOut1);
		digitalOut2 = new DigitalOutput(RobotMap.lightsDigitalOut2);
		digitalOut3 = new DigitalOutput(RobotMap.lightsDigitalOut3);

		analogOut = new AnalogOutput(RobotMap.lightsAnalogOut);
		
		currentMode = LightsMode.Nothing;
		setMode(currentMode);
	}

	public void setMode(LightsMode mode) {
		digitalOut1.set(mode.getBit1());
		digitalOut2.set(mode.getBit2());
		digitalOut3.set(mode.getBit3());
		
		currentMode = mode;
		
		SmartDashboard.putNumber("Lights bit1: ", mode.getBit1()?1:0);
		SmartDashboard.putNumber("Lights bit2: ", mode.getBit2()?1:0);
		SmartDashboard.putNumber("Lights bit3: ", mode.getBit3()?1:0);
	}

	public LightsMode getMode() {
		return currentMode;
	}

	public void setAnalogOut(double output) {
		output = Math.abs(output);
		if (output > 1) {
			output = 1.0;
		}
		output = output * ARDUINO_MAX_VOLTAGE;
		analogOut.setVoltage(output);
	}

	public void initDefaultCommand() {
		setDefaultCommand(new LightsShow());
	}
}
