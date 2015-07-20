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
	private static double oldAlpha;
	private static Point oldSensorLocation;
	
	public static void main(String[] args) {
		Point sensorLocation;
		double value = -2; //Just for initialization
		double speedA, speedB;
		double output;
		
		trainingSets = new TrainingSets();
		mainFrame = new MainFrame();
		image = trainingSets.line01Image;
		
		robot = new Robot_Simulation(new Point (100, 155));
		window = new RobotUI(robot, image);
		oldSensorLocation = new Point(robot.getSensorLocation().x,robot.getSensorLocation().y);
		
		while (true) {
			sensorLocation = robot.getSensorLocation();
			
			if(sensorLocation.x >= trainingSets.line01RGBSet[0].length) {
				robot.setRobotX(robot.getRobotLocation().x - trainingSets.line01RGBSet[0].length);
				robot.setSensorX(robot.getSensorLocation().x - trainingSets.line01RGBSet[0].length);
				continue;
			}
			
			if(sensorLocation.x < 0) {
				robot.setRobotX(robot.getRobotLocation().x + trainingSets.line01RGBSet[0].length-1);
				robot.setSensorX(robot.getSensorLocation().x + trainingSets.line01RGBSet[0].length-1);
				continue;
			}
			
			if(sensorLocation.y >= trainingSets.line01RGBSet.length) {
				robot.setRobotY(robot.getRobotLocation().y - trainingSets.line01RGBSet.length);
				robot.setSensorY(robot.getSensorLocation().y - trainingSets.line01RGBSet.length);
				continue;
			}
			
			if(sensorLocation.y < 0) {
				robot.setRobotY(robot.getRobotLocation().y + trainingSets.line01RGBSet.length-1);
				robot.setSensorY(robot.getSensorLocation().y + trainingSets.line01RGBSet.length-1);
				continue;
			}
			
			if(trainingSets.line01RGBSet[oldSensorLocation.y][oldSensorLocation.x] != trainingSets.line01RGBSet[sensorLocation.y][sensorLocation.x]) {
				oldAlpha = robot.getAlpha();
			}
			
			value = (trainingSets.line01RGBSet[sensorLocation.y][sensorLocation.x] == -1)? -0.999999999 : 0.999999999;
			System.out.println("Wert: "+value);
			
			// TODO: Entsprechendes Lernverfahren, dass dem Robot sagt was zu tun ist
			mainFrame.noticeSensorDetection(value);
			mainFrame.calculateLeastSquaresOptimum();
			
			output = mainFrame.net.neuron[mainFrame.numHiddens].output;
			System.out.println("output: "+output);

			if(robot.getAlpha() > oldAlpha+Math.PI) {
				robot.setAlpha(oldAlpha);
				speedA = MAX_SPEED;
				speedB = MAX_SPEED;
			} else {
				speedA = MAX_SPEED*(1-output);
				speedB = MAX_SPEED*(1+output);
			}
			
			oldSensorLocation = new Point(sensorLocation.x, sensorLocation.y);
			
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
