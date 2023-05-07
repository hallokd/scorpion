import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3MediumRegulatedMotor;
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


public class Multitask_test {

    public static void main(String[] args) {
        Thread colorThread = new Thread(new ColorAttack());
        Thread movementThread = new Thread(new Movement());

        colorThread.start();
        movementThread.start();
    }
}

class ColorAttack implements Runnable {
	public static boolean keepGoing = true;
    public void run() {
        // Paste the code for color_attack_test here
Button.ESCAPE.addKeyListener(new color_attack_test());
        
        //EV3MediumRegulatedMotor m = new EV3MediumRegulatedMotor(MotorPort.C); 
        
        EV3ColorSensor cs = new EV3ColorSensor(SensorPort.S1);
        ColorAdapter ca = new ColorAdapter(cs);
        
        UnregulatedMotor a = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor b = new UnregulatedMotor(MotorPort.B);
        UnregulatedMotor c = new UnregulatedMotor(MotorPort.C);
        UnregulatedMotor d = new UnregulatedMotor(MotorPort.D);
        int count = 0;
        
        
        while(keepGoing) {
            Color cr = ca.getColor();
            // calculate red percentage:
            double total = cr.getRed() + cr.getBlue() + cr.getGreen();
            double red_ratio = cr.getRed() / total;

            
            System.out.println("RED " + red_ratio);
            if(red_ratio > 0.5) {
            	Sound.playTone(500, 100);
                Delay.msDelay(1000);
            } else {
            	Sound.playTone(500, 100);
                Delay.msDelay(1000);
            }
            
            
            
        }
        cs.close();
        //m.close();
        a.close();
        c.close();
        b.close();
        d.close();
    }

    public void keyPressed(Key k) {}

    public void keyReleased(Key k) {
        keepGoing = false;
    }
}

class Movement implements Runnable {
    public void run() {
        // Paste the code for movement_test here
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
