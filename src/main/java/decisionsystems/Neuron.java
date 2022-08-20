package decisionsystems;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

class Neuron {
    int nTarget;

    Neuron[] target;
    double[] weights;
    double bias;
    double value;

    int activation =3;

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
            target = new Neuron[nTarget];
            weights = new double[nTarget];
        }
        value = 0;

        // Initialize weight with random value
        //bias = random.nextDouble() * range * 2 - range;
        bias = generateRandomValue();
        for (int i = 0; i < nTarget; i++)
            weights[i] = 0;
            //weights[i] = random.nextDouble() * range * 2 - range;
    }

    public void setTarget(int i, Neuron target) {
        this.target[i] = target;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void addValue(double value) {
        this.value += value;
    }

    public void addToTarget() {
        for (Neuron neuron : target)
            neuron.addValue(excecuteActivationFunction() + bias);

    }

    private double excecuteActivationFunction() {
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
        for (int i=0; i<p1.nTarget; i++)
            target.weights[i] = random.nextBoolean() ? p1.weights[i] : p2.weights[i];
    }

    public static double generateRandomValue() {
        return random.nextDouble() * range * 2 - range;
    }
}
