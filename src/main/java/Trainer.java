import agents.Agent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Trainer {
    static Random random = new Random();

    private ArrayList<Agent> agents;
    private Agent generator;

    private int nGeneration, nPopulation, mutationRate, nSurvive, nChild;

    public Trainer(Agent generator) {
        this.generator = generator;
    }

    public void setParameters(int nPopulation, int nGeneration,int mutationRate, int nChild) {
        this.nPopulation = nPopulation;
        this.nGeneration = nGeneration;
        this.mutationRate = mutationRate;
        this.nChild = 1;
        this.nSurvive = nPopulation / (nChild + 1);
    }

    public void initialize() {
        agents = new ArrayList<>();
        for (int i = 0; i < nPopulation; i++) agents.add(generator.getInstance());
    }

    public void run(int frequency) {
        int cnt = 0;

        for (int g = 0; g < nGeneration; g++) {
            // Mutate
            int nMutate = random.nextInt(mutationRate);
            for (int i = 0; i < nMutate; i++)
                agents.get(random.nextInt(nPopulation)).mutate();

            // Calculate fitness for each agent
            for (Agent agent : agents) {
                agent.calculateFitness();
            }

            // Sort and select best
            Collections.sort(agents);

            // Show best result
            if (frequency > 0 && cnt == frequency) {
                System.out.print("Generation " + g + ": ");
                agents.get(0).summarizeResult();
                cnt=0;
            }
            cnt++;

            // Mate best results
            for (int s=0; s < nSurvive; s++)
                for (int i=0; i < nChild; i++)
                    agents.get(s).mate(agents.get(s + nSurvive), agents.get(s + nSurvive * 2));
        }
    }

    public Agent getBest() {
        return agents.get(0);
    }

    public void animate(Agent ga){

    }

    public void summarizeResult(Agent ga) {
        ga.summarizeResult();
    }
}
