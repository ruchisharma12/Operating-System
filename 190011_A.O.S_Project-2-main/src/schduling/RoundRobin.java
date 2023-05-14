package schduler;
import java.util.LinkedList;
import java.util.Queue;

class Process {
    private String name;
    private int burstTime;
    private int arrivalTime;
    private int remainingTime;

    public Process(String name, int burstTime, int arrivalTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.arrivalTime = arrivalTime;
        this.remainingTime = burstTime;
    }

    public String getName() {
        return name;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        Queue<Process> queue = new LinkedList<>();
        queue.add(new Process("P1", 10, 0));
        queue.add(new Process("P2", 5, 2));
        queue.add(new Process("P3", 8, 3));

        int timeQuantum = 2; // Time quantum for Round Robin

        // Preemptive version
        System.out.println("Preemptive Round Robin");
        int currentTime = 0;
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            System.out.println(currentProcess.getName() + " starts at time " + currentTime);
            if (currentProcess.getRemainingTime() > timeQuantum) {
                // Process is not finished, add it back to the queue with updated remaining time
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeQuantum);
                currentTime += timeQuantum;
                queue.add(currentProcess);
            } else {
                // Process is finished, just print completion time
                currentTime += currentProcess.getRemainingTime();
                System.out.println(currentProcess.getName() + " completes at time " + currentTime);
            }
        }

        // Non-preemptive version (based on arrival time)
        System.out.println("\nNon-preemptive Round Robin (based on arrival time)");
        Queue<Process> queue2 = new LinkedList<>();
        queue2.add(new Process("P1", 10, 0));
        queue2.add(new Process("P2", 5, 2));
        queue2.add(new Process("P3", 8, 3));

        int currentArrivalTime = 0;
        while (!queue2.isEmpty()) {
            Process currentProcess = null;
            for (Process p : queue2) {
                if (p.getArrivalTime() <= currentArrivalTime &&
                        (currentProcess == null || p.getRemainingTime() < currentProcess.getRemainingTime())) {
                    currentProcess = p;
                }
            }
            if (currentProcess == null) {
                // No processes available at this time
                currentArrivalTime++;
            } else {
                System.out.println(currentProcess.getName() + " starts at time " + currentArrivalTime);
                queue2.remove(currentProcess);
                currentArrivalTime += currentProcess.getRemainingTime();
                System.out.println(currentProcess.getName() + " completes at time " + currentArrivalTime);
            }
        }
    }
}
