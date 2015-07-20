package main;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import evolution.Evolution;
import evolution.RobotValues;
import robot.Robot_Simulation;

public class Main {

	private static Robot_Simulation robot;
	private static RobotUI window;
	private static BufferedImage image;
	private static Evolution evolution;

	public static void main(String[] args) {

		try {
			//image = ImageIO.read(new File("/home/lukas/black_white.png")); //Linux
			image = ImageIO.read(new File("E:\\black_white.png")); // Windows
		} catch (IOException e) {
			System.out.println("Image not found");
			e.printStackTrace();
		}
		
		robot = new Robot_Simulation(new Point(20, 100));
		evolution = new Evolution(robot, 500, image);
				
		window = new RobotUI(robot, image);
		
		evolution.startEvolution();
		
		RobotValues value = evolution.getBestIndividuum();
		
		while (true) {
			robot.moveRobot((value.bias1 + value.w1 * value.black / value.white) * 10, (value.bias2 + value.w2 * value.white / value.black) * 10);
			if(robot.getSensorLocation().x >= image.getWidth()) {
				robot.setRobotX(robot.getRobotLocation().x - image.getWidth());
				robot.setSensorX(robot.getSensorLocation().x - image.getWidth());
			}
			
			if(robot.getSensorLocation().x < 0) {
				robot.setRobotX(robot.getRobotLocation().x + image.getWidth());
				robot.setSensorX(robot.getSensorLocation().x + image.getWidth());
			}
			
			if(robot.getSensorLocation().y >= image.getHeight()) {
				robot.setRobotY(robot.getRobotLocation().y - image.getHeight());
				robot.setSensorY(robot.getSensorLocation().y - image.getHeight());
			}
			
			if(robot.getSensorLocation().y < 0) {
				robot.setRobotY(robot.getRobotLocation().y + image.getHeight());
				robot.setSensorY(robot.getSensorLocation().y + image.getHeight());
			}
			window.repaint();
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
