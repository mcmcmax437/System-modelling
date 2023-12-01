package Bank;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Main {

    public static int MAX_QUEUE = 3;
    public static double CASHIER1_DELAY = 0.3;
    public static double CASHIER2_DELAY = 0.3;
    public static double CREATOR_DELAY = 0.1;

    public static int TIME_SIMULATION = 10000;



    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static void main(String[] args) {
        bankTask();
    }

    public static void bankTask() {
        Create creator = new Create("CREATOR", CREATOR_DELAY);
        Cashier cashier1 = new Cashier("CASHIER1", CASHIER1_DELAY, MAX_QUEUE );
        Cashier cashier2 = new Cashier("CASHIER2", CASHIER2_DELAY, MAX_QUEUE);
        Despose despose = new Despose("DESPOSE");

        creator.setPriorityCashier(cashier1);
        creator.setNonPriorityCashier(cashier2);

        cashier1.setNextElement(despose);
        cashier1.setAnotherCashier(cashier2);

        cashier2.setNextElement(despose);
        cashier2.setAnotherCashier(cashier1);

        Model model = new Model(new ArrayList<>(){{
            add(creator);
            add(cashier1);
            add(cashier2);
            add(despose);}});

        model.simulate(TIME_SIMULATION);

        System.out.println("1) Avarage load" + "\n\tCash№1: " + df.format(cashier1.getMeanLoad() / TIME_SIMULATION) + ";" + "\n\tCash№2: " + df.format(cashier2.getMeanLoad() / TIME_SIMULATION) + ";");
        System.out.println("2)Avarage client in bank: " + df.format((cashier1.getMeanLoad() + cashier1.getMeanQueue() + cashier2.getMeanLoad() + cashier2.getMeanQueue()) / TIME_SIMULATION));
        System.out.println("3) Average between customers leaving the windows: " + df.format(TIME_SIMULATION / (double)creator.getQuantity()));
        System.out.println("4) Avarage time in bank: " + df.format(despose.getAverageTimeClientStayInBank()) + "ms;");
        System.out.println("5) Avarage client in" + "\n\tQueue №1: " + df.format(cashier1.getMeanQueue() / TIME_SIMULATION) + ";" + "\n\tQueue №2: " + df.format(cashier2.getMeanQueue() / TIME_SIMULATION) + ";");
        System.out.println("6)Rejection: " + df.format((creator.getFailure() / (double)creator.getQuantity()) * 100) + "%;");
        System.out.println("7)Road swap:" + "\n\tFrom №1 to №2: " + cashier1.getAmountOfSwitchesToAnotherCashier() + ";" + "\n\tFrom №2 to №1: " + cashier2.getAmountOfSwitchesToAnotherCashier() + ";");
    }
}