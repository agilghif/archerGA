package decisionsystems;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

class Neuron {
    int nTarget;

    Neuron[] targets;
    double[] weights;
    double bias;
    double value;

    int activation = RELU;

    static double range = 10;

    static final int LINEAR = 0;
    static final int RELU = 1;
    static final int TANH = 2;
    static final int SIGMOID = 3;
    static final int LRELU = 4;

    private static Random random = new Random();

    public Neuron(int nTarget) {
        this.nTarget = nTarget;

        if (nTarget != 0) {
            targets = new Neuron[nTarget];
            weights = new double[nTarget];
        }
        value = 0;

        // Initialize weight with random value
        bias = random.nextDouble() * range * 2 - range;
        for (int i = 0; i < nTarget; i++)
            weights[i] = random.nextDouble() * range * 2 - range;
    }

    public void setTarget(int i, Neuron target) {
        this.targets[i] = target;
    }

    public void addToValue(double value) {
        this.value += value;
    }

    public void addValueToTargets() {
        for (Neuron neuron : targets)
            neuron.addToValue(excetuteActivationFunction());
    }

    private double excetuteActivationFunction() {
        value += bias;

        if (activation == 0)
            return value;
        else if (activation == 1)
            return value > 0 ? value : 0;
        else if (activation == 2)
            return Math.tanh(value*2);
        else if (activation == 3)
            return (1/(1+Math.exp(value)));
        else if (activation == 4)
            return value > 0 ? value : 0.05f * value;
        return value;
    }

    public void setActivation(int activation) {
        this.activation = activation;
    }

    // GA methods
    public static void mate(@NotNull Neuron p1, Neuron p2, Neuron target) {
        // Fix this to not be too random
        for (int i=0; i<p1.nTarget; i++)
            target.weights[i] = random.nextBoolean() ? p1.weights[i] : p2.weights[i];
    }

    public static double generateRandomValue() {
        return random.nextDouble() * range * 2 - range;
    }
}
