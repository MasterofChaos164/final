package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import robot.Robot_Simulation;
import robot.Robot_Simulation2;

public class RobotUI extends JPanel{
//	private Main main;
	private BufferedImage image; // Is the background Image of the frame
	private int frame_width;
	private int frame_height;
	private JFrame frame;
	
	private Robot_Simulation2 robot;
	
	public RobotUI(Robot_Simulation2 robot2, BufferedImage image) {
		this.robot = robot2;
		this.image = image; 
		this.frame_width = this.image.getWidth();
		this.frame_height = this.image.getHeight();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Neural Network");
		frame.setLocationRelativeTo(null);
		frame.setLocation(frame.getX()-frame_width/2,frame.getY()-frame_height/2);
		frame.add(this);
		frame.setVisible(true);
		this.frame_height += frame.getInsets().top+frame.getInsets().bottom;
		frame.setSize(frame_width,frame_height);
				
		this.setBackground(Color.cyan);
	}
	
	public void setRobot(Robot_Simulation2 robot) {
		this.robot = robot;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawBackground(g);
		drawRobot(g);
	}
	
	public void drawBackground(Graphics g) {
		g.drawImage(image,0,0,this);
	}
	
	public void drawRobot(Graphics g) {
		g.setColor(robot.getRobotColor());
		g.drawLine((int)robot.getRobot().x1, (int)robot.getRobot().y1, (int)robot.getRobot().x2, (int)robot.getRobot().y2);
	}
}
