
import java.util.Random;
public class Task2 {
    public static void main(String[] args) {
        Model2 model = new Model2(2.0, 1.0, 1.0, 1.0);
        model.simulate(1000.0);
    }
}

class Model2 {
    private double tnext;
    private double tcurr;
    private double tCreate, tProcess1, tProcess2, tProcess3, tDispose;
    private double delayCreate, delayProcess1, delayProcess2, delayProcess3;
    private int numCreate, numProcess1, numProcess2, numProcess3, numDispose, failure;
    private int state, maxqueue, queue;

    public Model2(double delayCreate, double delayProcess1, double delayProcess2, double delayProcess3) {
        this.delayCreate = delayCreate;
        this.delayProcess1 = delayProcess1;
        this.delayProcess2 = delayProcess2;
        this.delayProcess3 = delayProcess3;
        tnext = 0.0;
        tcurr = tnext;
        tCreate = tcurr;
        tProcess1 = Double.MAX_VALUE;
        tProcess2 = Double.MAX_VALUE;
        tProcess3 = Double.MAX_VALUE;
        tDispose = Double.MAX_VALUE;
        maxqueue = 0;
    }

    public void simulate(double timeModeling) {
        while (tcurr < timeModeling) {
            tnext = tCreate;
            int nextEvent = 0;

            if (tProcess1 < tnext) {
                tnext = tProcess1;
                nextEvent = 1;
            }
            if (tProcess2 < tnext) {
                tnext = tProcess2;
                nextEvent = 2;
            }
            if (tProcess3 < tnext) {
                tnext = tProcess3;
                nextEvent = 3;
            }
            if (tDispose < tnext) {
                tnext = tDispose;
                nextEvent = 4;
            }

            tcurr = tnext;
            switch (nextEvent) {
                case 0:
                    eventCreate();
                    break;
                case 1:
                    eventProcess1();
                    break;
                case 2:
                    eventProcess2();
                    break;
                case 3:
                    eventProcess3();
                    break;
                case 4:
                    eventDispose();
                    break;
            }

            printInfo();
        }
        printStatistic();
    }

    public void printStatistic() {
        System.out.println("numCreate = " + numCreate + " numProcess1 = " + numProcess1 +
                " numProcess2 = " + numProcess2 + " numProcess3 = " + numProcess3 + " numDispose = " + numDispose +
                " failure = " + failure);
    }

    public void printInfo() {
        System.out.println("t = " + tcurr + " state = " + state + " queue = " + queue);
    }

    public void eventCreate() {
        tCreate = tcurr + getDelayCreate();
        numCreate++;

        if (state == 0) {
            state = 1;
            tProcess1 = tcurr + getDelayProcess1();
        } else {
            if (queue < maxqueue) {
                queue++;
            } else {
                failure++;
            }
        }
    }

    public void eventProcess1() {
        tProcess1 = Double.MAX_VALUE;
        numProcess1++;

        if (state == 1) {
            state = 2;
            tProcess2 = tcurr + getDelayProcess2();
        }
    }

    public void eventProcess2() {
        tProcess2 = Double.MAX_VALUE;
        numProcess2++;

        if (state == 2) {
            state = 3;
            tProcess3 = tcurr + getDelayProcess3();
        }
    }

    public void eventProcess3() {
        tProcess3 = Double.MAX_VALUE;
        numProcess3++;

        if (state == 3) {
            state = 4;
            tDispose = tcurr + getDelayDispose();
        }
    }

    public void eventDispose() {
        tDispose = Double.MAX_VALUE;
        state = 0;

        if (queue > 0) {
            queue--;
            state = 1;
            tProcess1 = tcurr + getDelayProcess1();
        }
        numDispose++;
    }

    private double getDelayCreate() {
        return FunRand.Exp(delayCreate);
    }

    private double getDelayProcess1() {
        return FunRand.Exp(delayProcess1);
    }

    private double getDelayProcess2() {
        return FunRand.Exp(delayProcess2);
    }

    private double getDelayProcess3() {
        return FunRand.Exp(delayProcess3);
    }

    private double getDelayDispose() {
        return FunRand.Exp(delayCreate);
    }
}

class FunRand {
    /**
     * Generates a random value according to an exponential distribution.
     *
     * @param timeMean mean value of the exponential distribution
     * @return a random value according to an exponential distribution
     */
    public static double Exp(double timeMean) {
        Random random = new Random();
        return -timeMean * Math.log(1.0 - random.nextDouble());
    }

    // Other distribution methods (Unif, Norm) can be implemented here if needed.
}

