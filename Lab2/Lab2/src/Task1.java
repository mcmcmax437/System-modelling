import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Device {
    private boolean isBusy;
    private int totalJobs;
    private int totalServiceTime;
    public Queue<Integer> jobQueue;

    public Device() {
        isBusy = false;
        totalJobs = 0;
        totalServiceTime = 0;
        jobQueue = new LinkedList<>();
    }

    public void enqueueJob(int serviceTime) {
        jobQueue.offer(serviceTime);
        totalJobs++;
    }

    public void processJobs() {
        if (!isBusy && !jobQueue.isEmpty()) {
            int serviceTime = jobQueue.poll();
            totalServiceTime += serviceTime;
            isBusy = true;

            // Simulate job processing
            try {
                Thread.sleep(serviceTime); // Simulate processing time
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            isBusy = false;
        }
    }

    public double getAverageDeviceLoad() {
        return (double) totalServiceTime / totalJobs;
    }
}

public class Task1 {
    public static void main(String[] args) {
        Device device = new Device();
        Random random = new Random();

        // Simulate jobs arriving at the device
        for (int i = 0; i < 10; i++) {
            int serviceTime = random.nextInt(100) + 1; // Random service time between 1 and 100
            device.enqueueJob(serviceTime);
        }

        // Process jobs in the device
        while (!device.jobQueue.isEmpty()) {
            device.processJobs();
        }

        double averageLoad = device.getAverageDeviceLoad();
        System.out.println("Average Device Load: " + averageLoad);
    }
}
