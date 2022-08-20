package agents;

public interface Agent extends Comparable {
    Agent getInstance();
    void calculateFitness();
    void mutate();
    void reset();
    void mate(Agent other, Agent target);
    void summarizeResult();

}
