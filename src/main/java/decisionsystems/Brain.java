package decisionsystems;

import java.util.Random;

public class Brain {
    Neuron[] input;
    Neuron[][] hidden;
    Neuron[] output;

    private static Random random = new Random();

    public Brain(int nInput, int[] structure, int nOutput) {
        int nHidden = structure.length;

        // initialize array
        input = new Neuron[nInput];
        output = new Neuron[nOutput];
        hidden = new Neuron[nHidden][];

        for (int i=0; i<nHidden; i++)
            hidden[i] = new Neuron[structure[i]];

        // Fill neuron
        for (int i=0; i<nOutput; i++) // output
            output[i] = new Neuron(0);

        for (int i=0; i<structure[nHidden-1]; i++) // hidden
            hidden[nHidden-1][i] = new Neuron(nOutput);
        for (int i=nHidden-2; i>=0; i--)
            for (int j=0; j<structure[i]; j++)
                hidden[i][j] = new Neuron(structure[i+1]);

        for (int i=0; i<nInput; i++) // input
            input[i] = new Neuron(structure[0]);

        // Connect neurons
        connect(hidden[nHidden-1], output);

        for (int i=nHidden-2; i>=0; i--)
            connect(hidden[i], hidden[i+1]);

        connect(input, hidden[0]);
    }

    private void connect(Neuron[] source, Neuron[] target) {
        int sLen = source.length;
        int tLen = target.length;

        for (int i=0; i<sLen; i++)
            for (int j=0; j<tLen; j++)
                source[i].setTarget(j, target[j]);
    }

    public void emptyNeurons() {
        for (Neuron[] layer : hidden)
            for (Neuron neuron : layer)
                neuron.value = 0;

        for (Neuron neuron : output)
            neuron.value = 0;
    }

    public void propagate() {
        for (Neuron neuron : input)
            neuron.addToTarget();

        for (Neuron[] layer : hidden)
            for (Neuron neuron : layer)
                neuron.addToTarget();
    }

    public double getOutput(int idx) {
        return output[idx].value;
    }

    public void setInput(int idx, double val) {
        input[idx].value = val;
    }

    // GA methods

    public static void mate(Brain p1, Brain p2, Brain target) {
        int nInput = p1.input.length;
        int nHidden = p1.hidden.length;

        for (int i=0; i<nInput; i++)
            Neuron.mate(p1.input[i], p2.input[i], target.input[i]);

        for (int i=0; i<nHidden; i++) {
            int n = p1.hidden[i].length;
            for (int j=0; j<n; j++)
                Neuron.mate(p1.hidden[i][j], p2.hidden[i][j], target.hidden[i][j]);
        }
    }

    public void mutate() {
        int len = hidden.length + 1;
        int chosen = random.nextInt(len); // chosen layer
        Neuron[] layer = null;

        // Choose layer
        if (chosen == 0) layer = input;
        else layer = hidden[chosen-1];

        // Choose neuron
        chosen = random.nextInt(layer.length);
        Neuron chosenNeuron = layer[chosen];
        chosenNeuron.bias = Neuron.generateRandomValue();

        // Choose weight
        chosen = random.nextInt(chosenNeuron.weights.length);
        chosenNeuron.weights[chosen] = Neuron.generateRandomValue();
    }
}