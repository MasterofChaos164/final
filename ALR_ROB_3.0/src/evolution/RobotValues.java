package evolution;

public class RobotValues {
	
	public float w1, w2, bias1, bias2;
	public double fitness;
	public double black, white;
	
	public RobotValues () {
		w1 = (float) Math.random();
		w2 = (float) Math.random();
		bias1 = (float) Math.random();
		bias2 = (float) Math.random();
		fitness = 0;
		black = 1;
		white = 1;
	}
	
	public RobotValues (RobotValues other) {
		this.w1 = other.w1;
		this.w2 = other.w2;
		this.bias1 = other.bias1;
		this.bias2 = other.bias2;
		this.fitness = other.fitness;
		this.black = other.black;
		this.white = other.white;
	}
}
