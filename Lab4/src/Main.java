import java.util.ArrayList;
import java.util.List;


public class Main {
    public static int SIMUL_TIME = 1000;
    public static int DELAY = 10;

    public static int ITERETIONS = 100;


    public static void main(String[] args) {

        //List<Integer> valuesOfN = new ArrayList<>();
        //List<Double> simulationTimeModel1 = new ArrayList<>();
        //List<Double> simulationTimeModel2 = new ArrayList<>();

        for (int N = 100; N < 1001; N+=100) {
            long avrgSimulTime1 = basic(SIMUL_TIME, ITERETIONS, N, DELAY);
            long avrgSimulTime2 = advanced(SIMUL_TIME, ITERETIONS, N, DELAY);

           // valuesOfN.add(N);
           // simulationTimeModel1.add((avrgSimulTime1 / (double) ITERETIONS));
            //simulationTimeModel2.add(avrgSimulTime2 / (double) ITERETIONS);
        }

    }

    public static long basic(double simulationTime, int iterations, int N, int delay) {
        long avrgSimulTime1 = 0;

        for (int oi = 1; oi < iterations; oi++) {
            Element.resetNextId();
            Create creator = new Create("CREATOR", delay);

            Process prevProcessor = new Process("PROCESSOR_#0", delay);
            creator.setNextElement(prevProcessor);

            ArrayList<Process> listOfProcesses = new ArrayList<>();
            listOfProcesses.add(prevProcessor);

            for (int i = 1; i < N; i++) {
                Process process = new Process("PROCESSOR_Main" + i, delay);
                listOfProcesses.add(process);
                prevProcessor.addElement(new Pair(process, 1));

                prevProcessor = process;
            }
            Model model = new Model(new ArrayList<>(){{ add(creator); addAll(listOfProcesses); }});
            avrgSimulTime1 += measureTime(() -> model.simulate(simulationTime));
        }

        System.out.println("N = " + N);
        System.out.println("Simulation Time for Model 1: " + (avrgSimulTime1 /(double)iterations));
        return avrgSimulTime1;
    }

    public static long advanced(double simulationTime, int iterations, int N, int delay) {
        long avrgSimulTime2 = 0;

        for (int oi = 1; oi < iterations; oi++) {
            Element.resetNextId();
            Create creator = new Create("CREATOR", delay);

            Process prevProcessor = new Process("PROCESSOR_#0", delay);
            creator.setNextElement(prevProcessor);

            ArrayList<Process> listOfProcesses = new ArrayList<>();
            listOfProcesses.add(prevProcessor);

            for (int i = 1; i < N; i++) {
                Process process = new Process("PROCESSOR_Main" + i, delay);
                listOfProcesses.add(process);
                prevProcessor.addElement(new Pair(process, 1));

                prevProcessor = process;
            }

            for (int i = 0; i < listOfProcesses.size(); i++) {
                Process process = listOfProcesses.get(i);

                for (int j = i+1; j < listOfProcesses.size(); j++) {
                    process.addElement(new Pair(listOfProcesses.get(j), 1));
                }
            }

            Model model = new Model(new ArrayList<>(){{ add(creator); addAll(listOfProcesses); }});
            avrgSimulTime2 += measureTime(() -> model.simulate(simulationTime));
        }

        System.out.println("Simulation Time for Model 2: " + (avrgSimulTime2 /(double)iterations));
        System.out.println("-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/-/");
        return avrgSimulTime2;
    }

    private static long measureTime(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }


}