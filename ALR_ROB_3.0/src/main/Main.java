package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import robot.Robot_Simulation;

public class Main {

	private static Robot_Simulation robot;
	private static RobotUI window;
	private static BufferedImage image;

	public static void main(String[] args) {

		try {
			//image = ImageIO.read(new File("/home/lukas/black_white.png")); //Linux
			image = ImageIO.read(new File("D:\\black_white.png")); // Windows
		} catch (IOException e) {
			System.out.println("Image not found");
			e.printStackTrace();
		}

		robot = new Robot_Simulation(image);
		window = new RobotUI(robot, image);

		while (true) {
			try {
				robot.startRobot();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			window.repaint();
		}
	}
}
