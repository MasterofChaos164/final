package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import network.Evolution;
import robot.Robot_Simulation2;

public class Main {

	private static Robot_Simulation2[] robot;
	private static RobotUI window;
	private static BufferedImage image;
	private static Evolution evolution;
	
	private static int population = 10;

	public static void main(String[] args) {

		try {
			image = ImageIO.read(new File("/home/lukas/black_white.png")); //Linux
			//image = ImageIO.read(new File("D:\\black_white.png")); // Windows
		} catch (IOException e) {
			System.out.println("Image not found");
			e.printStackTrace();
		}
		
		robot = new Robot_Simulation2[population];
		evolution = new Evolution();
		
		for (int i = 0; i< population; i++)
			robot[i] = new Robot_Simulation2(image);
		
		window = new RobotUI(robot[0], image);

		while (true) {
			for (int i = 0; i < 10; i++) {
				window.setRobot(robot[i]);
				try {
					robot[i].startRobot();
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
				window.repaint();
			}
			robot = evolution.evolve(robot); // TODO Rückgabe
			
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
