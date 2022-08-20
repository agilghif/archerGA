package agents;

public interface GeneticAgent extends Comparable {
    void calculateFitness();
    void mutate();
    void reset();
    void mate(GeneticAgent other, GeneticAgent target);
    void summarizeResult();

}
