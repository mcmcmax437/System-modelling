
import java.util.ArrayList;

public class Model {
    private ArrayList<Element> elements = new ArrayList<>();
    double tnext, tcurr;
    int event;

    public Model(ArrayList<Element> elements) {
        this.elements = elements;
        tnext = 0.0;
        event = 0;
        tcurr = tnext;
    }

    public void simulate(double time) {
        while (tcurr < time) {
            tnext = Double.MAX_VALUE;
            for (Element e : elements) {
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    event = e.getId();
                }
            }

            System.out.println("\nTime for an event in " + elements.get(event).getName() + ", time = " + tnext);

            for (Element e : elements) {
                e.doStatistics(tnext - tcurr);   //дельта Т - рух до найближчої події
            }

            tcurr = tnext;

            for (Element e : elements) {
                e.setTcurr(tcurr);
            }

            elements.get(event).outAct();
            for (Element e : elements) {
                if (e.getTnext() == tcurr) {
                    e.outAct();
                }
            }

            printInfo();
        }
        printResult();
    }
    public void printInfo() {
        for (Element e : elements) {
            e.printInfo();
        }
    }
    public void printResult() {
        System.out.println("\n-----------------------RESULT-----------------------");

        for (Element e : elements) {
            System.out.println(e.getName() + ": {");
            e.printResult();

            if (e instanceof Process) {
                Process p = (Process) e;
                System.out.println("\tAverage queue length: " +
                        p.getMeanQueue() / tcurr
                        + ";\n" + "\tRejection: " +
                        Math.round((p.getFailure() / (double) (p.getQuantity()+p.getFailure())) * 100 * 10000) / (double)10000 + "%;\n" +
                        "\tAverage load: " + p.getMeanLoad() / tcurr + ";"
                );
            }
            System.out.println("}\n");
        }
    }
}
