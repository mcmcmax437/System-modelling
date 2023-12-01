package Durka;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

    public static int TIME_SIMULATION = 10000;

    public static int WORKERS_RECEPTION = 2;
    public static int WORKERS_ROOM= 2;
    public static int WORKERS_REGISTRY = 3;
    public static int WORKERS_LAB = 2;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    public static void main(String[] args) {
        durkaTask();
    }

    public static void durkaTask() {
        Create creator = new Create("CREATOR");
        Reception reception = new Reception("RECEPTION", WORKERS_RECEPTION);
        Process room = new Process("ROOM", WORKERS_ROOM);
        Process walkToRegistry = new Process("WALK_TO_REGISTRY");
        Process registry = new Process("REGISTRY", WORKERS_REGISTRY);
        Lab lab = new Lab("LAB", WORKERS_LAB);
        Process walkToReception = new Process("WALK_TO_RECEPTION");
        Despose despose = new Despose("DESPOSE");


        creator.Exp_Distribution(15);
        room.Unif_Distribution(3, 8);
        walkToRegistry.Unif_Distribution(2, 5);
        registry.Erlang_Distribution(4.5, 3);
        lab.Erlang_Distribution(4, 2);
        walkToReception.Unif_Distribution(2, 5);

        creator.setNextElement(reception);
        reception.setWalkToRegistry(walkToRegistry);
        reception.setRoom(room);
        room.setNextElement(despose);
        walkToRegistry.setNextElement(registry);
        registry.setNextElement(lab);
        lab.setWalkToReception(walkToReception);
        lab.setDespose(despose);
        walkToReception.setNextElement(reception);

        Model model = new Model(new ArrayList<>(){{
            add(creator);
            add(reception);
            add(room);
            add(walkToRegistry);
            add(registry);
            add(lab);
            add(walkToReception);
            add(despose);
        }});

        model.simulate(TIME_SIMULATION);

        System.out.println("(PATIENT) Time in system: " + df.format(despose.getAverageTimePatientStayInDurka()));
        System.out.println("\tType1: " + df.format(despose.getAverageTimePatientStayInDurkaByType(1)));
        System.out.println("\tType2: " + df.format(despose.getAverageTimePatientStayInDurkaByType(2)));
        System.out.println("\tType3: " + df.format(despose.getAverageTimePatientStayInDurkaByType(3)));

        System.out.println("Arrivals interval at the laboratory: " + df.format(TIME_SIMULATION / (double)creator.getQuantity()));
    }
}
