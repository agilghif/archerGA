//import java.util.Random;
//
//public class Archer implements GeneticAgent {
//    // Distance sensor & absolute position
//    float pos;
//
//    // Angle sensor, 0 = face upward
//    float archT; float visT;
//
//    // Color sensor, 0 = empty, 1 = light
//    float visCol;
//
//    // Velocity sensor
//    float shootVel; float vel;
//
//    // Counter and shooter indicator
//    int counter; boolean shoot; boolean shot;
//
//    // Force
//    float force; float archTorque; float visTorque;
//    boolean stop;
//
//    // state indicator
//    float state = 1;
//    private int sign = 1;
//    private int stateCounter = 0;
//
//    // Thresholds
//    static float maxDist = 10;
//    static float maxVel = 10f;
//    static float maxShootVel = 20f;
//    static int counterDuration = 20;
//    static float maxState = 20;
//    static int maxStateCounter = 12;
//
//    static float lowerLim = 3, upperLim = 7;
//
//    static float dt = 0.1f;
//    static float hg = 5;
//
//    // Random number generator
//    private static Random random = new Random();
//
//    // decisionsystems.Brain
//    private decisionsystems.Brain brain;
//
//    // comparables
//    int targetSeeDuration = 0;
//    int dist = 0; float oldPos = Float.MIN_VALUE;
//
//
//    public Archer() {
//        pos = random.nextFloat() * maxDist * 2 - maxDist;
//        archT = (float) (random.nextFloat() * Math.PI - Math.PI/2);
//        visT = (float) (random.nextFloat() * Math.PI - Math.PI/2);
//        visCol = 0;
//        vel = random.nextFloat() * maxVel * 2 - maxVel;
//        counter = 0;
//        stop = false;
//
//        state = random.nextFloat() * maxState * 2 - maxState;
//
//        brain = new decisionsystems.Brain(6, new int[] {10, 21, 10}, 7);
//
//        // Output structure
//        // 0:force , 1:shootVel , 2:shoot , 3:visT , 4: archT, 5:memory, 6:stop :: 3 dan 4 terbatas di (-pi, pi)
//    }
//
//    public void shuffle() {
//        pos = random.nextFloat() * maxDist * 2 - maxDist;
//        archT = (float) (random.nextFloat() * Math.PI - Math.PI/2);
//        visT = (float) (random.nextFloat() * Math.PI - Math.PI/2);
//        visCol = 0;
//        vel = random.nextFloat() * maxVel * 2 - maxVel;
//        counter = 0;
//        stop = false;
//
//        state = random.nextFloat() * maxState * 2 - maxState;
//
//        // restart score
//        targetSeeDuration = 0;
//        dist = 0;
//    }
//
//    public void update() {
//        // for checking paramter
//        oldPos = pos;
//
//        // routine
//        insertInput();
//        updatePosition();
//        updateAngle();
//        updateVision();
//        calculateOutput();
//        shootIfTrue();
//
//        // for checking parameter
//        checkStuck();
//    }
//
//    private void checkStuck() {
//        dist += Math.abs(oldPos - pos);
//    }
//
//    private void insertInput() {
//        brain.emptyNeurons();
//        // Input structure
//        // 0:distL, 1:distR, 2:archT, 3: visT,
//        // 4:visCol, 5:shootVel, 6:vel, 7:mem
//        brain.setInput(0, pos); // left position
//        brain.setInput(1, archT);
//        brain.setInput(2, visT);
//        brain.setInput(3, visCol);
//        brain.setInput(4, vel);
//        brain.setInput(5, state);
//    }
//
//    private void updatePosition() {
//        vel *= 0.9;
//
//        vel += force;
//        if (vel > maxVel) vel = maxVel;
//        if (vel < -maxVel) vel = -maxVel;
//
//        pos += vel * dt;
//        if (pos > maxDist) {
//            pos = maxDist;
//            vel = -vel;
//        }
//        if (pos < -maxDist) {
//            pos = -maxDist;
//            vel = -vel;
//        }
//
//        counter++;
//    }
//
//    private void updateAngle() {
//        visT += visTorque/500;
//        archT += archTorque/500;
//
//        if (visT > (float) (Math.PI/2)) visT = (float) (Math.PI/2);
//        else if (visT < (float) (-Math.PI/2)) visT = (float) (-Math.PI/2);
//
//        if (archT > (float) (Math.PI/2)) archT = (float) (Math.PI/2);
//        else if (archT < (float) (-Math.PI/2)) archT = (float) (-Math.PI/2);
//    }
//
//    private void updateVision() {
//        if (visT == 0) {
//            visCol = 0;
//            return;
//        }
//
//        double val = (maxDist - pos) / Math.tan(visT);
//        if (val < upperLim && val > lowerLim) {
//            visCol = 10;
//            targetSeeDuration++;
//        }
//        else visCol = 0;
//    }
//
//    private void calculateOutput() {
//        brain.emptyNeurons();
//        brain.propagate();
//
//        force = brain.getOutput(0);
//        shootVel = Math.abs(brain.getOutput(1)) < maxShootVel ? Math.abs(brain.getOutput(1)) : maxShootVel;
//        shoot = brain.getOutput(2) > 0;
//        stop = brain.getOutput(3) < 0;
//        archTorque = brain.getOutput(4);
//        visTorque = brain.getOutput(5);
//        state = sign * brain.getOutput(6);
//
//        // misc: update state
//        stateCounter++;
//        if (stateCounter > maxStateCounter) {
//            stateCounter=0;
//            sign *= -1;
//        }
//        if (state > maxState) state = maxState;
//        else if (state < -maxState) state = -maxState;
//    }
//
//    public void shootIfTrue() {
//        shot = counter == counterDuration;
//
//        if (shot) {
//            counter = 0;
//
//            float vx = (float) (vel * Math.sin(archT)) + vel;
//            float vy = (float) (vel * Math.cos(archT));
//
//            float y = (vx/pos) * (vy - 5*vx/pos);
//        }
//    }
//
//    @Override
//    public int compareTo(Object o) {
////        return targetSeeDuration - ((Archer) o).targetSeeDuration;
////        return -stuck + ((Archer) o).stuck;
//        return  dist - ((Archer) o).dist;
//    }
//
//    // ga methods
//
//    public static void mate(Archer a1, Archer a2, Archer target) {
//        decisionsystems.Brain b1 = a1.brain, b2 = a2.brain, bt = target.brain;
//        decisionsystems.Brain.mate(b1, b2, bt);
//
//        a1.shuffle();
//        a2.shuffle();
//        target.shuffle();
//    }
//
//    public void mutate() {
//        brain.mutate();
//    }
//}
