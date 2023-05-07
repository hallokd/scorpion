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

public class color_attack_test implements KeyListener {
    public static boolean keepGoing = true;

    
    public static void main(String[] args) {
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
            	b.setPower(75);
            	b.backward();
            	Delay.msDelay(2000);
            	b.setPower(75);
            	b.forward();
            	Delay.msDelay(2000);
            	b.flt();
            	a.setPower(50);
            	c.setPower(50);
            	a.forward();
            	c.forward();
            	Delay.msDelay(2000);
            	a.flt();
            	c.flt();
            	d.setPower(100);
            	d.forward();
            	Delay.msDelay(1000);
            	a.setPower(50);
            	c.setPower(50);
            	a.backward();
            	c.backward();
            	Delay.msDelay(2000);
            	a.flt();
            	c.flt();
            	d.setPower(100);
            	d.forward();
            	Delay.msDelay(1000);
            	d.setPower(100);
            	d.backward();
            	Delay.msDelay(1000);
            	d.setPower(100);
            	d.forward();
            	Delay.msDelay(1000);
            	d.setPower(100);
            	d.backward();
            	Delay.msDelay(1000);
            	d.setPower(50);
            	d.forward();
            	Delay.msDelay(250);
            	d.flt();
            } else {
            	Sound.playTone(500, 100);
                Delay.msDelay(1000);
            }
            
            if (count == 4) {
                a.setPower(100);
                c.setPower(100);
                a.forward(); // set the motors to go backward
                c.forward();
                Delay.msDelay(2000);
                a.flt();
                c.flt();
                break; // exit the while loop
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