import agents.Agent1;
import agents.GeneticAgent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Simulator {
    static Random random = new Random();
    //static Grapher g = new Grapher(500, 500);

    static ArrayList<GeneticAgent> agents;

    static int nGenerations, nPopulation, mutationRate, nSurvive, nChild;

    public static void main(String[] args) {
        // set Params
        nGenerations = 1000;
        nPopulation = 100*3;
        mutationRate = 3;
        nSurvive = 100;
        nChild = 1;

        // Initialization
        initialize();

        // Simulate
        run(1);

        // Simulate best
        summarizeResult(getBest());
    }

    static void initialize() {
        agents = new ArrayList<>();
        for (int i = 0; i < nPopulation; i++) agents.add(new Agent1());
    }

    static void run(int frequency) {
        int cnt = 0;

        for (int g = 0; g < nGenerations; g++) {
            // Mutate
            int nMutate = random.nextInt(mutationRate);
            for (int i = 0; i < nMutate; i++)
                agents.get(random.nextInt(nPopulation)).mutate();

            // Calculate fitness for each agent
            for (GeneticAgent agent : agents) {
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

    static GeneticAgent getBest() {
        return agents.get(0);
    }

    static void animate(GeneticAgent ga){

    }

    static void summarizeResult(GeneticAgent ga) {
        ga.summarizeResult();
    }
}
