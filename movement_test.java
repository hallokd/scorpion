//import java.awt.Color;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.Color;
import lejos.robotics.ColorAdapter;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

public class movement_test {

	public static void main(String [] args) {
		double diam = 30.05;
		double trackwidth = 100;
		DifferentialPilot rov3r = new DifferentialPilot(diam, trackwidth, Motor.C, Motor.A);

		EV3IRSensor ir = new EV3IRSensor(SensorPort.S4);
		SensorMode distMode = ir.getMode("Distance");
		int distance = 255;

		rov3r.forward();
		while(!Button.ESCAPE.isDown()) {

			float [] sample = new float[distMode.sampleSize()];
			distMode.fetchSample(sample, 0);
			distance = (int)sample[0];

			LCD.drawString("DIST:     ", 0, 2);
			LCD.drawInt(distance, 3, 5, 2);

			if(distance < 40 && distance !=0) {
				rov3r.travel(-70);
				rov3r.rotate(90);
				//rov3r.travel(10);
				//rov3r.rotate(90);
				rov3r.forward();
				
			} 
			Delay.msDelay(100);
		}
		ir.close();
	}
}