package main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import neuralnetwork.MainFrame;
import adapter.TrainingSets;
import robot.Robot_Simulation;

public class MainNeuralNetwork {

	public final static double MAX_SPEED = 10;
	public static TrainingSets trainingSets;
	public static MainFrame mainFrame;
	private static Robot_Simulation robot;
	private static RobotUI window;
	private static BufferedImage image;
	
	public static void main(String[] args) {
		Point sensorLocation;
		double value;
		double speedA, speedB;
		double output;
		
		trainingSets = new TrainingSets();
		mainFrame = new MainFrame();
		image = trainingSets.line01Image;
		
		robot = new Robot_Simulation(new Point (100, 100));
		window = new RobotUI(robot, image);
		// Lernverfahren einfügen
		
		while (true) {
			sensorLocation = robot.getSensorLocation();
			value = (trainingSets.line01RGBSet[sensorLocation.y][sensorLocation.x] == -1)? -0.999999999 : 0.999999999;
			System.out.println("Wert: "+value);
			if(sensorLocation.x >= trainingSets.line01RGBSet.length || sensorLocation.y >= trainingSets.line01RGBSet[0].length || sensorLocation.x < 0 || sensorLocation.y < 0) {
				continue;
			}
			// TODO: Entsprechendes Lernverfahren, dass dem Robot sagt was zu tun ist
			mainFrame.noticeSensorDetection(value);
			mainFrame.calculateLeastSquaresOptimum();
			
			output = mainFrame.net.neuron[mainFrame.numHiddens].output;
			System.out.println("output: "+output);
			if(output < 0) {
				//-1.0 => Rechtskurve
				speedA = MAX_SPEED;
				speedB = MAX_SPEED/5*4+MAX_SPEED/5*(1+output);
			} else {
				//1.0 => Linkskurve
				speedA = MAX_SPEED/5*4+MAX_SPEED/5*(1-output);
				speedB = MAX_SPEED;
			}
			
			try {
				robot.moveRobot(speedA, speedB);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Roboter neu zeichnen
			window.repaint();
			
			// Kurze Pause um die Visualisierung besser nachvollziehen zu können
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println("Sleep failed");
				e.printStackTrace();
			}
		}
	}
}
