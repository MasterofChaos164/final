package network;

import robot.Robot_Simulation2;

public class Evolution {

	private Robot_Simulation2[] robot;
	private Robot_Simulation2[] newRobot;

	private final double crossoverRate = 0.6;
	private final double selectionRate = (1 - crossoverRate) * 10;
	private final double mutationRate = 0.2;

	public Robot_Simulation2[] evolve(Robot_Simulation2[] robot) {
		this.robot = robot;
		newRobot = new Robot_Simulation2[robot.length];
		selection();
		crossover();
		mutation();
		return newRobot;
	}

	private void selection() {
		int minFitness = 0;
		for (int i = 0; i < robot.length; i++) {
			if (i < 4) {
				newRobot[i] = robot[i];
			} else {
				for (int j = 0; j < 4; j++) {
					if (newRobot[minFitness].getFitness() > newRobot[j].getFitness())
						minFitness = j;
				}
				if (robot[i].getFitness() > newRobot[minFitness].getFitness())
					newRobot[minFitness] = robot[i];
			}
		}
	}

	private void crossover() {
		Robot_Simulation2[] temp = new Robot_Simulation2[(int) (crossoverRate * robot.length)];
		int minFitness = 0;
		for (int i = 0; i < robot.length; i++) {
			if (i < 6)
				temp[i] = robot[i];
			else {
				for (int j = 0; j < 6; j++){
					if (temp[minFitness].getFitness() > temp[j].getFitness())
						minFitness = j;
				}
				if (robot[i].getFitness() > temp[minFitness].getFitness())
					temp[minFitness] = robot[i];
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
			
			newRobot[counter + 4] = temp[counter];
			newRobot[counter + 1 + 4] = temp[counter + 1];
			
			counter += 2;
		}
	}

	private void mutation() {
		int random1, random2;
		for (int i = 0; i < (mutationRate * newRobot.length); i++) {
			random1 = (int) (Math.random() * 10);
			random2 = (int) ((Math.random() * 10) % 4);
			if (random2 == 0)
				newRobot[random1].bias1 = Float.intBitsToFloat(Float.floatToIntBits((int)newRobot[random1].bias1) ^ (int)Math.pow(2, Math.random() % 32));
			else if (random2 == 1)
				newRobot[random1].bias2 = Float.intBitsToFloat(Float.floatToIntBits((int)newRobot[random1].bias2) ^ (int)Math.pow(2, Math.random() % 32));
			else if (random2 == 3)
				newRobot[random1].w1 = Float.intBitsToFloat(Float.floatToIntBits((int)newRobot[random1].w1) ^ (int)Math.pow(2, Math.random() % 32));
			else
				newRobot[random1].w2 = Float.intBitsToFloat(Float.floatToIntBits((int)newRobot[random1].w2) ^ (int)Math.pow(2, Math.random() % 32));
				
		}
	}
}
