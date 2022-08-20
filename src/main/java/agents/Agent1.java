package agents;

import decisionsystems.Brain;

import java.util.Random;

public class Agent1 implements Agent {
    double pos, vel, acc;
    static double maxPos = 10, maxVel = 5, maxAcc = 1;
    static double dt = 0.1;
    static int duration = 100;
    double distTravel;

    Brain brain;
    private static Random random = new Random();

    public Agent1() {
//        pos = random.nextDouble() * maxPos * 2 - maxPos;
//        vel = random.nextDouble() * maxVel * 2 - maxVel;
//        acc = random.nextDouble() * maxAcc * 2 - maxAcc;
        pos=0; vel=0; acc=0; distTravel=0;

        brain = new Brain(3, new int[] {4,5,6,5,4,3,2}, 1);
    }

    @Override
    public Agent getInstance() {
        return new Agent1();
    }

    @Override
    public void calculateFitness() {
        for (int t=0; t<duration; t++) {
            brain.emptyNeurons();

            brain.setInput(0, pos);
            brain.setInput(0, vel);
            brain.setInput(0, acc);

            brain.propagate();

            double oldPos = pos;

            acc = brain.getOutput(0);
            if (acc > maxAcc) acc = maxAcc;
            else if (acc < -maxAcc) acc = -maxAcc;

            vel += acc * dt;
            if (vel > maxVel) vel = maxVel;
            else if (vel < -maxVel) vel = -maxVel;

            pos += vel * dt;
            if (pos > maxPos) pos = maxPos;
            else if (pos < -maxPos) pos = -maxPos;

            // calculate score
            distTravel += Math.abs(oldPos - pos);
        }
    }

    @Override
    public void mutate() {
        brain.mutate();
    }

    @Override
    public void reset() {
//        pos = random.nextDouble() * maxPos * 2 - maxPos;
//        vel = random.nextDouble() * maxVel * 2 - maxVel;
//        acc = random.nextDouble() * maxAcc * 2 - maxAcc;
//
//        distTravel = 0;
        pos=0; vel=0; acc=0; distTravel=0;
    }

    @Override
    public void mate(Agent other, Agent target) {
        Brain.mate(this.brain, ((Agent1) other).brain, ((Agent1) target).brain);

        this.reset();
        other.reset();
        target.reset();
    }

    @Override
    public void summarizeResult() {
        System.out.println("Distance travelled =" + distTravel);
    }

    @Override
    public int compareTo(Object o) {
        if (distTravel > ((Agent1) o).distTravel) return 1;
        else if (distTravel < ((Agent1) o).distTravel) return -1;
        return 0;
    }
}
