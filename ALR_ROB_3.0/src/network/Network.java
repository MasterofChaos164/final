package network;

public class Network {
	private boolean sensorInput;
	private double w[];
	private double bias;
	private double speedA, speedB; // speedA = linker Motor, speedB = rechter Motor;
	
	private boolean isBlack;
	
	
	public Network(boolean input) {		
		bias = 0.5;
		speedA = 0;
		speedB = 0;
		w = new double[4];
		isBlack = input;
		generateRandomWeights();
	}
	
	public void calculate() {
		speedA = neuron (w[0], w[1], sensorInput);
		speedB = neuron (w[2], w[3], sensorInput);
	}
	
	public double neuron(double w1, double w2, boolean input) {
		double speed;
		int x;
		
		if (input == true)
			x = 1;
		else
			x = 0;		
		
		speed = (bias * w1) + (x * w2);
		
		return speed;
	}
	
	public double getSpeedA() {
		return speedA;
	}
	
	public double getSpeedB() {
		return speedB;
	}
	
	
	public void modifyWeights(boolean input) {
		if ((input == true && isBlack == true) || (input == false && isBlack == false)) {
			if (speedA > speedB) {
				if (w[0] >= 0.1)
					w[0] -= 0.1;
				if (w[1] >= 0.1)
					w[1] -= 0.1;
				if (w[2] >= 0.02)
					w[2] -= 0.02;
				if (w[3] >= 0.02)
					w[3] -= 0.02;
			}
			else if (speedA < speedB) {
				if (w[0] >= 0.02)
					w[0] -= 0.02;
				if (w[1] >= 0.02)
					w[1] -= 0.02;
				if (w[2] >= 0.1)
					w[2] -= 0.1;
				if (w[3] >= 0.1)
					w[3] -= 0.1;
			}
		}
		else {
			for (int i = 0; i < w.length; i++) {
				if (i >= 0.1)
					w[i] += 0.1;
			}
		}
	}
	
	public void setSensorInput(boolean input) {
		System.out.println(input);
		sensorInput = input;
	}
	
	public void generateRandomWeights() {
		for (int i = 0; i < w.length; i++)
			w[i] = Math.random();
	}
}
