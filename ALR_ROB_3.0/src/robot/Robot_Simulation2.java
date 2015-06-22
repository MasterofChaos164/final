package robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;

import network.Network;

public class Robot_Simulation2 {
	
	private Point robotLocation;
	private Dimension robotSize;
	private Color robotColor;
	
	private Point sensorLocation;
	private Dimension sensorSize;
	private Color sensorColor;
	
	private Point sensorStartLocation;
	
	private BufferedImage image;
	
	// Anzahl der Schritte pro Sekunde
	private static final int speed = 5;
	
	private Network network;
	
	public Robot_Simulation2(BufferedImage image) {
		
		robotSize = new Dimension(20,20);
		robotLocation = new Point(50, 130);
		robotColor = Color.GREEN;
		
		sensorStartLocation = new Point(robotLocation.x + 13 /* TODO */, robotLocation.y + 13 /*(robotSize.height / 2) - (sensorSize.height / 2)*/);
		
		sensorSize = new Dimension(6, 6);
		sensorLocation = sensorStartLocation;
		sensorColor = Color.BLUE;
		
		this.image = image;
		
		network = new Network(isBlack());
	}
	
	public void startRobot() throws Exception{
		network.setSensorInput(isBlack());
		network.calculate();
		helpToMove(network.getSpeedA(), network.getSpeedB());
		network.modifyWeights(isBlack(), sensorStartLocation, sensorLocation);
	}
	
	public void helpToMove (double speedA, double speedB) throws Exception {
		
//		if (speedA > speedB) {
////			speedA /= speedB;
////			speedA *= 9;
////			rotateRobot(speedA);
//			rotateRobot(speedA * 90);
//		}
//		else if (speedA < speedB) {
//			speedB /= speedA;
//			speedB *= 9;
//			rotateRobot(-speedB);
//		}
		rotateRobot(speedA * 90);
		rotateRobot(-speedB * 90);
		moveRobotForMS(300);
	}
	
	public boolean isBlack() {
		return new Color(image.getRGB(getSensorLocation().x, getSensorLocation().y)).equals(new Color(0, 0, 0));
	}
	
	public void moveRobotForMS(double ms) throws Exception {
		
		if (ms < 0)
			throw new Exception("Exception: Only positive values allowed!");
		
		double distance = speed * (ms / 1000);
		
		// Verh�ltnis von Sensormittelpunkt und Robotermittelpunkt (-> Blickrichtung)
		double x = (sensorLocation.getX() + (sensorSize.getWidth() / 2)) - (robotLocation.getX() + (robotSize.getWidth() / 2));
		double y = (sensorLocation.getY() + (sensorSize.getHeight() / 2)) - (robotLocation.getY() + (robotSize.getHeight() / 2));
		
		// Berechnung der Entfernung vom momentanen Standpunkt in x und y Richtung
		double proportion = x / y;
		double yLength = Math.sqrt(Math.pow(distance, 2) / (Math.pow(proportion, 2) + 1));
		double xLength = Math.sqrt(Math.pow(distance, 2) - Math.pow(yLength, 2));
		
		if (x < 0)
			xLength *= -1;
		if (y < 0)
			yLength *= -1;
		
		// Setzt Roboter und Sensor auf ihre neue Position
		moveRobotInDirection(xLength, yLength);
		moveSensorInDirection(xLength, yLength);
		Thread.sleep(100);
	}
	
	public void rotateRobot(double angle) throws Exception {
		
		// Macht keinen Sinn sich im Kreis zu drehen
		if (angle < -360 || angle > 360)
			throw new Exception("Exception: Angle out of range!");
		
		// Transform into radiant value
		double radiantAngle = (angle * Math.PI) / 180;
				
		double robotCenterX = robotLocation.getX() + robotSize.getWidth() / 2;
		double robotCenterY = robotLocation.getY() + robotSize.getHeight() / 2;
		
		double sensorCenterX = sensorLocation.getX() + sensorSize.getWidth() / 2;
		double sensorCenterY = sensorLocation.getY() + sensorSize.getHeight() / 2;
		
		double xNew = robotCenterX + (sensorCenterX - robotCenterX) * Math.cos(radiantAngle) - (sensorCenterY - robotCenterY) * Math.sin(radiantAngle);
		double yNew = robotCenterY + (sensorCenterX - robotCenterX) * Math.sin(radiantAngle) + (sensorCenterY - robotCenterY) * Math.cos(radiantAngle);
		
		sensorLocation.setLocation(xNew - (sensorSize.getWidth() / 2), yNew - (sensorSize.getHeight() / 2));
	}
	
	public void moveRobotInDirection(double xLength, double yLength) {
		robotLocation.setLocation(robotLocation.getX() + xLength, robotLocation.getY() + yLength);
	}
	
	public Point getRobotLocation(){
		return robotLocation;
	}
	
	public void moveSensorInDirection(double xLength, double yLength) {
		sensorLocation.setLocation(sensorLocation.getX() + xLength, sensorLocation.getY() + yLength);		
	}
	
	public Point getSensorLocation() {
		return sensorLocation;
	}
	
	public Dimension getRobotSize() {
		return robotSize;
	}
	
	public Color getRobotColor() {
		return robotColor;
	}
	
	public Dimension getSensorSize() {
		return sensorSize;
	}
	
	public Color getSensorColor() {
		return sensorColor;
	}
}
