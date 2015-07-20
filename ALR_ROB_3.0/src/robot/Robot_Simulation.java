package robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Robot_Simulation {
	
	private Point robotLocation;
	private Dimension robotSize;
	private Color robotColor;
	
	private Point sensorLocation;
	private Dimension sensorSize;
	private Color sensorColor;
	
	public Point robotStartLocation;
	public Point sensorStartLocation;
	
	private double alpha;
	
	// Anzahl der Schritte pro Sekunde
//	private static final int speed = 5;
	
	
	public Robot_Simulation(Point startLocation) {
		
		robotStartLocation = new Point (startLocation.x, startLocation.y);
		robotSize = new Dimension(20,20);
		robotLocation = new Point (robotStartLocation.x, robotStartLocation.y);
		robotColor = Color.GREEN;

		sensorSize = new Dimension(6, 6);
		int sensorStartLocationX = (int)(robotStartLocation.x + robotSize.getWidth());
		int sensorStartLocationY = (int)(robotStartLocation.y + robotSize.getHeight()/2.0-sensorSize.getHeight()/2.0);
		sensorStartLocation = new Point(sensorStartLocationX, sensorStartLocationY);
		sensorLocation = new Point (sensorStartLocation.x, sensorStartLocation.y);
		sensorColor = Color.BLUE;
		
		alpha = 0;
	}
	
	public void moveRobot (double speedA, double speedB) {
		double deltaAlpha = (speedA - speedB) / getRobotSize().width;
		double deltaS = (speedA + speedB) / 2;
		double deltaX = 0, deltaY = 0;
		if (speedA == speedB) {
			deltaX = speedA * Math.cos(deltaAlpha);
			deltaY = speedB * Math.sin(deltaAlpha);
		} else if (-speedA != speedB && speedA != 0) {
			deltaX = (deltaS / deltaAlpha) * (Math.cos((Math.PI / 2) + alpha - deltaAlpha) + Math.cos(alpha - (Math.PI / 2))); // 
			deltaY = (deltaS / deltaAlpha) * (Math.sin((Math.PI / 2) + alpha - deltaAlpha) + Math.sin(alpha - (Math.PI / 2)));
		}
		
		alpha += deltaAlpha;
		moveRobotInDirection(deltaX, deltaY);
		moveSensorInDirection(deltaX, deltaY);
	}
	
	public void resetRobot() {
		robotLocation.x = robotStartLocation.x;
		robotLocation.y = robotStartLocation.y;
		sensorLocation.x = sensorStartLocation.x;
		sensorLocation.y = sensorStartLocation.y;
		alpha = 0;
	}
	
	private void moveRobotInDirection(double xLength, double yLength) {
		robotLocation.setLocation(robotLocation.getX() + xLength, robotLocation.getY() + yLength);
	}
	
	public Point getRobotLocation(){
		return robotLocation;
	}
	
	public void setRobotLocation(Point robotLocation) {
		this.robotLocation = robotLocation;
	}
	
	private void moveSensorInDirection(double xLength, double yLength) {
		sensorLocation.setLocation(sensorLocation.getX() + xLength, sensorLocation.getY() + yLength);		
	}
	
	public Point getSensorLocation() {
		return sensorLocation;
	}
	
	public void setSensorLocation(Point sensorLocation) {
		this.sensorLocation = sensorLocation;
	}
	
	public void setRobotX(int xPos) {
		this.robotLocation.x = xPos;
	}
	
	public void setRobotY(int yPos) {
		this.robotLocation.y = yPos;
	}
	
	public void setSensorX(int xPos) {
		this.sensorLocation.x = xPos;
	}
	
	public void setSensorY(int yPos) {
		this.sensorLocation.y = yPos;
	}
	
	public void setRobotSize(Dimension robotSize) {
		this.robotSize = robotSize;
	}
	
	public Dimension getRobotSize() {
		return robotSize;
	}
	
	public Dimension getSensorSize() {
		return sensorSize;
	}
	
	public Color getRobotColor() {
		return robotColor;
	}
	
	public Color getSensorColor() {
		return sensorColor;
	}
	
	public double getAlpha() {
		return alpha;
	}
	
	public void setAlpha(double blub) {
		alpha = blub;
	}
}
