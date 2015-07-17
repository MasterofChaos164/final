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
	
	private Point robotStartLocation;
	private Point sensorStartLocation;
	
	private double alpha;
	
	// Anzahl der Schritte pro Sekunde
//	private static final int speed = 5;
	
	
	public Robot_Simulation(Point startLocation) {
		
		robotStartLocation = startLocation;
		robotSize = new Dimension(20,20);
		robotLocation = robotStartLocation;
		robotColor = Color.GREEN;

		sensorSize = new Dimension(6, 6);
		int sensorStartLocationX = (int)(robotStartLocation.x + robotSize.getWidth());
		int sensorStartLocationY = (int)(robotStartLocation.y + robotSize.getHeight()/2.0-sensorSize.getHeight()/2.0);
		sensorStartLocation = new Point(sensorStartLocationX, sensorStartLocationY);
		sensorLocation = sensorStartLocation;
		sensorColor = Color.BLUE;
		
		alpha = 0;
	}
	
	public void moveRobot (double speedA, double speedB) throws Exception {
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
	
//	public void rotateRobot(double angle) throws Exception {
//		
//		// Macht keinen Sinn sich im Kreis zu drehen
//		if (angle < -360 || angle > 360)
//			throw new Exception("Exception: Angle out of range!");
//		
//		// Transform into radiant value
//		double radiantAngle = (angle * Math.PI) / 180;
//				
//		double robotCenterX = robotLocation.getX() + robotSize.getWidth() / 2;
//		double robotCenterY = robotLocation.getY() + robotSize.getHeight() / 2;
//		
//		double sensorCenterX = sensorLocation.getX() + sensorSize.getWidth() / 2;
//		double sensorCenterY = sensorLocation.getY() + sensorSize.getHeight() / 2;
//		
//		double xNew = robotCenterX + (sensorCenterX - robotCenterX) * Math.cos(radiantAngle) - (sensorCenterY - robotCenterY) * Math.sin(radiantAngle);
//		double yNew = robotCenterY + (sensorCenterX - robotCenterX) * Math.sin(radiantAngle) + (sensorCenterY - robotCenterY) * Math.cos(radiantAngle);
//		
//		sensorLocation.setLocation(xNew - (sensorSize.getWidth() / 2), yNew - (sensorSize.getHeight() / 2));
//	}
	
	private void moveRobotInDirection(double xLength, double yLength) {
		robotLocation.setLocation(robotLocation.getX() + xLength, robotLocation.getY() + yLength);
	}
	
	public Point getRobotLocation(){
		return robotLocation;
	}
	
	private void moveSensorInDirection(double xLength, double yLength) {
		sensorLocation.setLocation(sensorLocation.getX() + xLength, sensorLocation.getY() + yLength);		
	}
	
	public Point getSensorLocation() {
		return sensorLocation;
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
