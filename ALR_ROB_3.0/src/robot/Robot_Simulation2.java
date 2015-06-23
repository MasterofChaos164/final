package robot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import network.Network;

public class Robot_Simulation2 {
	
	Line2D.Double robot;
	Color robotColor;
	
	private BufferedImage image;
	
	private int black, white = 1;
	
	public float w1, bias1, w2, bias2;
	
	private double speedA, speedB;
	
	private double fitness, lastFitness = 0;
	
	// Anzahl der Schritte pro Sekunde
	private static final int speed = 3;
	
	public Robot_Simulation2(BufferedImage image) {
		
		robot = new Line2D.Double(30, 160, 30, 190);
		robotColor = Color.green;
		
		this.image = image;
	}
	
	public void startRobot() throws Exception {
		randomWeights();
		speedA = (bias1 + w1 * white/black);
		speedB = (bias2 + w2 * black/white);
		moveRobot();
		lastFitness = fitness;
	}
	
	public boolean isBlack() {
		return new Color(image.getRGB(getRobotLocation().x, getRobotLocation().y)).equals(new Color(0, 0, 0));
	}
	
	public void moveRobot() {
		//Bewegung 10 mal ausführen
		int xDirection = 1, yDirection = 1;
		
		double verhältnis = speedA / speedB;
		
		if (speedA > speedB) {
			speedB /= speedA;
			speedA /= speedA;
		} else {
			speedA /= speedB;
			speedB /= speedB;
		}
		
		speedA *= speed;
		speedB *= speed;
		
		if (robot.x1 < robot.x2 && robot.y1 < robot.y2) {
			xDirection = 1;
			yDirection = -1;
		}
		else if (robot.x1 < robot.x2 && robot.y1 > robot.y2) {
			xDirection = -1;
			yDirection = -1;
		}
		else if (robot.x1 > robot.x2 && robot.y1 < robot.y2) {
			xDirection = 1;
			yDirection = 1;
		}
		else if (robot.x1 > robot.x2 && robot.y1 > robot.y2) {
			xDirection = -1;
			yDirection = 1;
		}
		
		if (speedB > speedA) {
			robot.x1 += speedA * xDirection;
			robot.y1 += speedA * yDirection;
		}
		
		// x1 + SpeedA * xDirection
		
	}
	
//	public void moveRobotForMS(double ms) throws Exception {
//		
//		if (ms < 0)
//			throw new Exception("Exception: Only positive values allowed!");
//		
//		double distance = speed * (ms / 1000);
//		
//		// Verh�ltnis von Sensormittelpunkt und Robotermittelpunkt (-> Blickrichtung)
//		double x = (sensorLocation.getX() + (sensorSize.getWidth() / 2)) - (robotLocation.getX() + (robotSize.getWidth() / 2));
//		double y = (sensorLocation.getY() + (sensorSize.getHeight() / 2)) - (robotLocation.getY() + (robotSize.getHeight() / 2));
//		
//		// Berechnung der Entfernung vom momentanen Standpunkt in x und y Richtung
//		double proportion = x / y;
//		double yLength = Math.sqrt(Math.pow(distance, 2) / (Math.pow(proportion, 2) + 1));
//		double xLength = Math.sqrt(Math.pow(distance, 2) - Math.pow(yLength, 2));
//		
//		if (x < 0)
//			xLength *= -1;
//		if (y < 0)
//			yLength *= -1;
//		
//		// Setzt Roboter und Sensor auf ihre neue Position
//		moveRobotInDirection(xLength, yLength);
//		moveSensorInDirection(xLength, yLength);
//		Thread.sleep(100);
//	}
	
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
	
	public Point getRobotLocation(){
		return new Point((int)(robot.x1 - (robot.x1 - robot.x2) / 2), (int)(robot.y1 - (robot.y1 - robot.y2) / 2));
	}
	
	public Line2D.Double getRobot() {
		return robot;
	}
	
	public Color getRobotColor() {
		return robotColor;
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

	public double calculateFitness(Point startLocation, Point endLocation, boolean isBlack) {
		if (isBlack == true)
			black++;
		else
			white++;
		double abstand = Math.sqrt(Math.pow(endLocation.getX() - startLocation.getX(), 2) + Math.pow(endLocation.getY() - startLocation.getY(), 2));
		return Math.abs((abstand / (black - white)));
	}
	
	private void randomWeights() {
		w1 = (float) Math.random();
		bias1 = (float) Math.random();
		w2 = (float) Math.random();
		bias2 = (float) Math.random();
	}
}
