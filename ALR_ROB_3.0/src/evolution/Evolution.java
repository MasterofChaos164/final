package evolution;

import robot.Robot_Simulation;

public class Evolution {
	
	private static final int populationSize = 10;
	private static final double selectionRate = 0.4;
	private static final double crossoverRate = 0.6;
	private static final double mutationRate = 0.1;
	
	private static final int iterations = 100;
	
	RobotValues[] values;
	
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
		values = new RobotValues[populationSize];
		for (int i = 0; i < populationSize; i++) {
			values[i] = new RobotValues();
		}
	}
	
	/**
	 * Starts the evolution and aborts when the average fitness exceeds the target fitness
	 * @throws Exception 
	 */
	public void startEvolution() {
		RobotValues[] tempValues = new RobotValues[populationSize];
		
		while (calculateOverallFitness() / populationSize < targetFitness) {
			
			for (RobotValues value : values) {
				for (int i = 0; i < iterations; i++)
					robot.moveRobot((value.bias1 + value.black / value.white * value.w1) * 10, (value.bias2 + value.white / value.black * value.w2) * 10);
				value.fitness = calculateFitness(robot, value);
				robot.resetRobot();
			}
			
			tempValues = selection(tempValues);
			tempValues = crossover(tempValues);
			tempValues = mutation(tempValues);
			values = tempValues;
		}
	}
	
	/**
	 * Calculates the fitness of a single individuum
	 * @param robot
	 * @return fitness
	 */
	private double calculateFitness(Robot_Simulation robot, RobotValues value) {
		int blackwhite;
		if (value.black > value.white)
			blackwhite = value.white / value.black;
		else
			blackwhite = value.black / value.white;
		return (Math.sqrt(Math.pow(robot.getRobotLocation().x - robot.robotStartLocation.x, 2) + Math.pow(robot.getRobotLocation().y - robot.robotStartLocation.y, 2))) / blackwhite;
	}
	
	/**
	 * Calculates the overall fitness of the current population
	 * @return overall fitness
	 */
	private double calculateOverallFitness() {
		double fitness = 0;
		for (RobotValues value : values)
			fitness += value.fitness;
		return fitness;
	}
	
	/**
	 * Selects the robots with the best fitness depending on their fitness
	 */
	private RobotValues[] selection(RobotValues[] tempValues) {
		int minFitness = 0;
		for (int i = 0; i < tempValues.length; i++) {
			if (i < selectionRate * populationSize) {
				tempValues[i] = new RobotValues (values[i]);
			} else {
				for (int j = 0; j < selectionRate * populationSize; j++) {
					if (tempValues[minFitness].fitness > tempValues[j].fitness)
						minFitness = j;
				}
				if (values[i].fitness > tempValues[minFitness].fitness)
					tempValues[minFitness] = new RobotValues(values[i]);
				minFitness = 0;
			}
		}
		return tempValues;
	}
	
	/**
	 * Crossovers the 
	 * @param robot
	 * @return
	 */
	private RobotValues[] crossover(RobotValues[] tempValues) {
		RobotValues[] temp = new RobotValues[(int) (crossoverRate * values.length)];
				int minFitness = 0;
				for (int i = 0; i < values.length; i++) {
					if (i < 6)
						temp[i] = new RobotValues(values[i]);
					else {
						for (int j = 0; j < 6; j++){
							if (temp[minFitness].fitness > temp[j].fitness)
								minFitness = j;
						}
						if (values[i].fitness > temp[minFitness].fitness)
							temp[minFitness] = new RobotValues(values[i]);
					} 
				}
				int counter = 0;
				int help1, help2;
				int mask1 = (int) Math.pow (2, (Math.random() * 100) % 32 - 1);
				int mask2 = 0xFFFFFFFF - mask1;
				while (counter < temp.length) {
					help1 = Float.floatToIntBits(temp[counter].bias1);
					help2 = Float.floatToIntBits(temp[counter+1].bias1);
					temp[counter].bias1 = Float.intBitsToFloat(help1 & mask1 + help2 & mask2);
					temp[counter+1].bias1 = Float.intBitsToFloat(help2 & mask1 + help1 & mask2);
					
					help1 = Float.floatToIntBits(temp[counter].bias2);
					help2 = Float.floatToIntBits(temp[counter+1].bias2);
					temp[counter].bias2 = Float.intBitsToFloat(help1 & mask1 + help2 & mask2);
					temp[counter+1].bias2 = Float.intBitsToFloat(help2 & mask1 + help1 & mask2);
					
					help1 = Float.floatToIntBits(temp[counter].w1);
					help2 = Float.floatToIntBits(temp[counter+1].w1);
					temp[counter].w1 = Float.intBitsToFloat(help1 & mask1 + help2 & mask2);
					temp[counter+1].w1 = Float.intBitsToFloat(help2 & mask1 + help1 & mask2);
					
					help1 = Float.floatToIntBits(temp[counter].w2);
					help2 = Float.floatToIntBits(temp[counter+1].w2);
					temp[counter].w2 = Float.intBitsToFloat(help1 & mask1 + help2 & mask2);
					temp[counter+1].w2 = Float.intBitsToFloat(help2 & mask1 + help1 & mask2);
					
					tempValues[counter + 4] = temp[counter];
					tempValues[counter + 1 + 4] = temp[counter + 1];
					
					counter += 2;
				}
		return tempValues;
	}
	
	/**
	 * 
	 * @param robot
	 * @return
	 */
	private RobotValues[] mutation(RobotValues[] tempValues) {
		int random1, random2;
		for (int i = 0; i < (mutationRate * values.length); i++) {
			random1 = (int) (Math.random() * 10);
			random2 = (int) ((Math.random() * 10) % 4);
			if (random2 == 0)
				tempValues[random1].bias1 = Float.intBitsToFloat(Float.floatToIntBits((int) tempValues[random1].bias1) ^ (int) Math.pow(2, Math.random() % 32));
			else if (random2 == 1)
				tempValues[random1].bias2 = Float.intBitsToFloat(Float.floatToIntBits((int) tempValues[random1].bias2) ^ (int) Math.pow(2, Math.random() % 32));
			else if (random2 == 3)
				tempValues[random1].w1 = Float.intBitsToFloat(Float.floatToIntBits((int) tempValues[random1].w1) ^ (int) Math.pow(2, Math.random() % 32));
			else
				tempValues[random1].w2 = Float.intBitsToFloat(Float.floatToIntBits((int) tempValues[random1].w2) ^ (int) Math.pow(2, Math.random() % 32));

		}
		return tempValues;
	}
	
	/**
	 * Returns the values of the best individuum
	 * @return
	 */
	public RobotValues getBestIndividuum() {
		int maximum = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i].fitness > values[maximum].fitness)
				maximum = i;
		}
		return values[maximum];
	}
}
