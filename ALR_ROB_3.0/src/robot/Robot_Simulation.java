package robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.peer.RobotPeer;

import network.Network;

public class Robot_Simulation {
	
	private Point robotLocation;
	private Dimension robotSize;
	private Color robotColor;
	
	private Point sensorLocation;
	private Dimension sensorSize;
	private Color sensorColor;
	
	private Point robotStartLocation;
	private Point sensorStartLocation;
	
	private BufferedImage image;

	private int black = 1, white = 1;
	
	public float w1, bias1, w2, bias2;

	private double speedA, speedB;
	
	private double alpha;
	
	private double fitness, lastFitness = 0;
	
	// Anzahl der Schritte pro Sekunde
	private static final int speed = 5;
	
	
	public Robot_Simulation(BufferedImage image) {
		
		robotStartLocation = new Point (30, 160);
		sensorStartLocation = new Point (robotStartLocation.x + 20, robotStartLocation.y + 10);

		robotSize = new Dimension(20,20);
		robotLocation = robotStartLocation;
		robotColor = Color.GREEN;
		
		sensorSize = new Dimension(6, 6);
		sensorLocation = sensorStartLocation;
		sensorColor = Color.BLUE;
		
		alpha = 0;
		
		randomWeights();
		
		this.image = image;
	}
	
	public void startRobot() throws Exception{
		lastFitness = fitness;
		double whiteBlack = white/black;
		double blackWhite = black/white;
		
		if (whiteBlack > 10)
			whiteBlack = 10;
		if (blackWhite > 10)
			blackWhite = 10;
		
		speedA = (bias1 + w1 * whiteBlack);
		speedB = (bias2 + w2 * blackWhite);
		helpToMove(speedA * 10, speedB * 10);
		fitness = getFitness();
	}
	
	public void helpToMove (double speedA, double speedB) throws Exception {
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
	
	public boolean isBlack() {
		Color color = new Color(1,1,1);
		try {
			color = new Color(image.getRGB(getSensorLocation().x, getSensorLocation().y));
		} catch (ArrayIndexOutOfBoundsException e) {
			robotLocation = robotStartLocation;
			sensorLocation = sensorStartLocation;
		}	
		return color.equals(new Color(0, 0, 0));
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
		
		
		// 561 861
		if (getSensorLocation().x < 0 || getSensorLocation().x > image.getWidth() || getSensorLocation().y < 0 ||getSensorLocation().y > image.getHeight()) {
			robotLocation = robotStartLocation;
			sensorLocation = sensorStartLocation;
		}
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
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getLastFitness() {
		return lastFitness;
	}
	
	public void setLastFitness(double lastFitness) {
		this.lastFitness = lastFitness;
	}

	public double calculateFitness() {
		if (isBlack() == true)
			black++;
		else
			white++;
		double abstand = Math.sqrt(Math.pow(sensorLocation.getX() - sensorStartLocation.getX(), 2) + Math.pow(sensorLocation.getY() - sensorStartLocation.getY(), 2));
		if (black > white)
			return Math.abs((abstand / (black - white)));
		else
			return Math.abs((abstand/ (white - black)));
	}
	
	private void randomWeights() {
		w1 = (float) Math.random();
		bias1 = (float) Math.random();
		w2 = (float) Math.random();
		bias2 = (float) Math.random();
	}
}
