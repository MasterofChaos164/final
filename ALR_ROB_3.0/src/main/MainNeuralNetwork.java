package main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import adapter.TrainingSets;
import robot.Robot_Simulation;

public class MainNeuralNetwork {

	public static TrainingSets trainingSets;
	private static Robot_Simulation robot;
	private static RobotUI window;
	private static BufferedImage image;
	
	public static void main(String[] args) {
		trainingSets = new TrainingSets();
		image = trainingSets.line01Image;
		
		robot = new Robot_Simulation(new Point (100, 100));
		window = new RobotUI(robot, image);
		// Lernverfahren einfügen
		
		while (true) {
			
			// TODO: Entsprechendes Lernverfahren, dass dem Robot sagt was zu tun ist
			
			try {
				robot.moveRobot(10, 8);
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
