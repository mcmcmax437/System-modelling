import java.util.ArrayList;

public class Main {
    public static int MaxQUEUE= 5;
    public static int ChannelsNumber = 3;
    public static double SIM_TIME = 1000;

    public static  double Creator_DELAY = 1;
    public static void main(String[] args) {
        //Task1_2();
        //Task3_4_5();
        Task6();
    }

    public static void Task1_2() {
        Create c = new Create(Creator_DELAY, "CREATOR");
        Process p = new Process(1, ChannelsNumber, "PROCESSOR");

        c.setDistribution("exp");
        c.setNextElement(p);

        p.setDistribution("exp");
        p.setMaxqueue(MaxQUEUE);

        Model model = new Model(new ArrayList<>(){{add(c); add(p);}});
        model.simulate(SIM_TIME);
    }

    public static void Task3_4_5() {
        Create creator = new Create(Creator_DELAY, "CREATOR");
        Process p1 = new Process(1, ChannelsNumber, "PROCESSOR1");
        Process p2 = new Process(1, ChannelsNumber, "PROCESSOR2");
        Process p3 = new Process(1, ChannelsNumber, "PROCESSOR3");

        creator.setDistribution("exp");
        creator.setNextElement(p1);

        p1.setDistribution("exp");
        p1.setMaxqueue(MaxQUEUE);
        p1.setNextElements(new ArrayList<>(){{add(p2);}}, new ArrayList<>(){{add(1.0);}});

        p2.setDistribution("exp");
        p2.setMaxqueue(MaxQUEUE);
        p2.setNextElements(new ArrayList<>(){{add(p3);}}, new ArrayList<>(){{add(1.0);}});

        p3.setDistribution("exp");
        p3.setMaxqueue(MaxQUEUE);

        Model model = new Model(new ArrayList<>(){{add(creator); add(p1); add(p2); add(p3);}});
        model.simulate(SIM_TIME);
    }

    public static void Task6() {
        Create creator = new Create(Creator_DELAY, "CREATOR");
        Process p1 = new Process(1, ChannelsNumber, "PROCESSOR1");
        Process p2 = new Process(1, ChannelsNumber, "PROCESSOR2"); //0.5
        Process p3 = new Process(1, ChannelsNumber, "PROCESSOR3"); //0.3
        Process p4 = new Process(1, ChannelsNumber, "PROCESSOR4");

        creator.setDistribution("exp");
        creator.setNextElement(p1);

        p1.setDistribution("exp");
        p1.setMaxqueue(MaxQUEUE);
        p1.setNextElements(new ArrayList<>(){{add(p2);}},new ArrayList<>(){{add(1.0);}});

        p2.setDistribution("exp");
        p2.setMaxqueue(MaxQUEUE);
        p2.setNextElements(new ArrayList<>(){{add(p3); add(p1); add(p4);}}, new ArrayList<>(){{add(0.5); add(0.25); add(0.25);}});

        p3.setDistribution("exp");
        p3.setMaxqueue(MaxQUEUE);
        p3.setNextElements(new ArrayList<>(){{add(p4);}},new ArrayList<>(){{add(1.0);}});

        p4.setDistribution("exp");
        p4.setMaxqueue(MaxQUEUE);

        Model model = new Model(new ArrayList<>(){{add(creator); add(p1); add(p2); add(p3); add(p4);}});
        model.simulate(SIM_TIME);
    }
}
