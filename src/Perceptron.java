import java.util.*;

public class Perceptron {
	float[] weights;
	float c = new Float(0.00001);
	Random rand = new Random();
 
	Perceptron(int n) {
		weights = new float[n];
		for (int i = 0; i < weights.length; i++) {
			weights[i] = (rand.nextFloat() * 2) - 1;
		}
	}
 
	public int feedforward(float[] inputs) {
		float sum = 0;
		for (int i = 0; i < weights.length; i++) {
			sum += inputs[i] * weights[i];
		}
		return activate(sum);
	}
 
	private int activate(float sum) {
		if (sum > 0) return 1;
		else return -1;
	}

	public void train(float[] inputs, int desired) {
		int guess = feedforward(inputs);
		float error = desired - guess;
		for (int i = 0; i < weights.length; i++) {
			weights[i] += c *  (float) error * inputs[i];
			System.out.println("new weights[" + i + "] += " + error * inputs[i] * c);
		}
	}
}

class Trainer {
	float[] inputs;
	int answer;
 
	Trainer(float x, float y, int a) {
		inputs = new float[3];
		inputs[0] = x;
		inputs[1] = y;
		inputs[2] = -1;
		answer = a;
	}
}