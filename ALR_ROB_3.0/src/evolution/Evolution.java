package evolution;

import java.awt.Point;

import robot.Robot_Simulation;

public class Evolution {
	
	private static final int populationSize = 10;
	private static final int selectionRate = 0;
	private static final int crossoverRate = 0;
	private static final int mutationRate = 0;
	
	RoboterValues[] values;
	
	private Robot_Simulation robot;
	private double targetFitness = 0;
	
	/**
	 * given start location and a target fitness which should be reached
	 * @param startLocation
	 * @param targetFitness
	 */
	public Evolution(Robot_Simulation robot, double targetFitness) {
		this.targetFitness = targetFitness;
		this.robot = robot;
		values = new RoboterValues[populationSize];
	}
	
	/**
	 * Starts the evolution and aborts when the average fitness exceeds the target fitness
	 */
	public void startEvolution() {
		Robot_Simulation[] tempRobots;
		while (calculateOverallFitness() / populationSize < targetFitness) {
			tempRobots = selection(robots, tempRobots);
			tempRobots = crossover(tempRobots);
			tempRobots = mutation(tempRobots);
			robots = tempRobots;
			tempRobots = null;
		}
	}
	
	/**
	 * Calculates the fitness of a single individuum
	 * @param robot
	 * @return fitness
	 */
	private double calculateFitness(Robot_Simulation robot) {
		
		//TODO: Fitness calculation
		return 0;
	}
	
	/**
	 * Calculates the overall fitness of the current population
	 * @return overall fitness
	 */
	private double calculateOverallFitness() {
		double fitness = 0;
		for (Robot_Simulation robot : robots)
			fitness += calculateFitness(robot);
		return fitness;
	}
	
	/**
	 * Selects the robots with the best fitness depending on their fitness
	 */
	private Robot_Simulation[] selection(Robot_Simulation[] robots, Robot_Simulation[] tempRobot) {
		int minFitness = 0;
		for (int i = 0; i < robots.length; i++) {
			if (i < 4) {
				tempRobot[i] = robots[i];
			} else {
				for (int j = 0; j < 4; j++) {
					if (calculateFitness(tempRobot[minFitness]) > calculateFitness(tempRobot[j]))
						minFitness = j;
				}
				if (calculateFitness(robots[i]) > calculateFitness(tempRobot[minFitness]))
					tempRobot[minFitness] = robots[i];
				minFitness = 0;
			}
		}
		return robots;
	}
	
	/**
	 * Crossovers the 
	 * @param robot
	 * @return
	 */
	private Robot_Simulation[] crossover(Robot_Simulation[] robots) {
		
		return robots;
	}
	
	/**
	 * 
	 * @param robot
	 * @return
	 */
	private Robot_Simulation[] mutation(Robot_Simulation[] robots) {
		int random1, random2;
		for (int i = 0; i < (mutationRate * newRobot.length); i++) {
			random1 = (int) (Math.random() * 10);
			random2 = (int) ((Math.random() * 10) % 4);
			if (random2 == 0)
				newRobot[random1].bias1 = Float.intBitsToFloat(Float.floatToIntBits((int) newRobot[random1].bias1) ^ (int) Math.pow(2, Math.random() % 32));
			else if (random2 == 1)
				newRobot[random1].bias2 = Float.intBitsToFloat(Float.floatToIntBits((int) newRobot[random1].bias2) ^ (int) Math.pow(2, Math.random() % 32));
			else if (random2 == 3)
				newRobot[random1].w1 = Float.intBitsToFloat(Float.floatToIntBits((int) newRobot[random1].w1) ^ (int) Math.pow(2, Math.random() % 32));
			else
				newRobot[random1].w2 = Float.intBitsToFloat(Float.floatToIntBits((int) newRobot[random1].w2) ^ (int) Math.pow(2, Math.random() % 32));

		}
		return robots;
	}
}
