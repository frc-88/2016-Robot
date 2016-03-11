package org.usfirst.frc.team88.robot.subsystems;

import org.usfirst.frc.team88.robot.LightsMode;
import org.usfirst.frc.team88.robot.RobotMap;
import org.usfirst.frc.team88.robot.commands.LightsShow;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *  Bright and shiny lights
 *  Hypnotize and bewilder
 *  And win us awards!
 */
public class Lights extends Subsystem {

	private DigitalOutput digitalOut1, digitalOut2, digitalOut3;
	private AnalogOutput analogOut;
	private LightsMode currentMode;
	private SendableChooser lightsMode;
	public SendableChooser testChooser;
	
	private double ARDUINO_MAX_VOLTAGE = 5.0;

	public Lights() {
		digitalOut1 = new DigitalOutput(RobotMap.lightsDigitalOut1);
		digitalOut2 = new DigitalOutput(RobotMap.lightsDigitalOut2);
		digitalOut3 = new DigitalOutput(RobotMap.lightsDigitalOut3);

		analogOut = new AnalogOutput(RobotMap.lightsAnalogOut);
		
		testChooser = new SendableChooser();
		testChooser.addDefault("Match", false);
		testChooser.addObject("Test", true);
		SmartDashboard.putData("Lights Test", testChooser);
				
		lightsMode = new SendableChooser();
		lightsMode.addDefault("Rainbow", LightsMode.Rainbow);
		lightsMode.addObject("RedJuggle", LightsMode.RedJuggle);
		lightsMode.addObject("BlueJuggle", LightsMode.BlueJuggle);
		lightsMode.addObject("GreenJuggle", LightsMode.GreenJuggle);
		lightsMode.addObject("Disco", LightsMode.Disco);
		lightsMode.addObject("RedAnalog", LightsMode.RedAnalog);
		lightsMode.addObject("BlueAnalog", LightsMode.BlueAnalog);
		lightsMode.addObject("Nothing", LightsMode.Nothing);
		SmartDashboard.putData("Lights Mode", lightsMode);

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

	public void setSelectedMode() {
		setMode((LightsMode)lightsMode.getSelected());
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
