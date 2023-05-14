package schdeular;
import java.util.*;

class Process {
    private String name;
    private int arrivalTime;
    private int burstTime;
    private int priority;

    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", burstTime=" + burstTime +
                ", priority=" + priority +
                '}';
    }
}

public class MultilevelQueueScheduling {
    private static final int QUANTUM = 2; // Time quantum for Round Robin scheduling

    public static void main(String[] args) {
        // Create a list of processes
        List<Process> processes = new ArrayList<>();
        processes.add(new Process("P1", 0, 8, 1));
        processes.add(new Process("P2", 1, 4, 2));
        processes.add(new Process("P3", 2, 9, 3));
        processes.add(new Process("P4", 3, 5, 2));
        processes.add(new Process("P5", 4, 2, 1));

        // Sort the processes by arrival time
        processes.sort(Comparator.comparing(Process::getArrivalTime));

        // Create the multilevel queue
        Queue<Process> queue1 = new LinkedList<>(); // Queue with highest priority
        Queue<Process> queue2 = new LinkedList<>(); // Queue with middle priority
        Queue<Process> queue3 = new LinkedList<>(); // Queue with lowest priority

        // Add the processes to the appropriate queues based on priority
        for (Process process : processes) {
            switch (process.getPriority()) {
                case 1:
                    queue1.add(process);
                    break;
                case 2:
                    queue2.add(process);
                    break;
                case 3:
                    queue3.add(process);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid priority for process: " + process);
            }
        }

        // Initialize the time and completion time
        int time = 0;
        int[] completionTime = new int[processes.size()];

        // Execute the processes in each queue
        while (!queue1.isEmpty() || !queue2.isEmpty() || !queue3.isEmpty()) {
            // Check the highest-priority queue first
            if (!queue1.isEmpty()) {
                Process process = queue1.peek();

                // Check if the process has arrived
                if (process.getArrivalTime() <= time) {
                    // Execute the process for the required time quantum
                    if (process.getBurstTime() > QUANTUM) {
                        process.setBurstTime(process.getBurstTime() - QUANTUM);
                        time += QUANTUM;
                    } else {
                        time += process.getBurstTime();
                        completionTime[getIndex(process, processes)] = time;
                        queue1
                        .poll();
                    }
                } else {
                    // Move to the next queue if no process has arrived in this queue
                    queue1.add(queue2.poll());
                }
            } else if (!queue2.isEmpty()) {
                // Check the middle-priority queue next
                Process process = queue2.peek();

                // Check if the process has arrived
                if (process.getArrivalTime() <= time) {
                    // Execute the process for the required time quantum
                    if (process.getBurstTime() > QUANTUM) {
                        process.setBurstTime(process.getBurstTime() - QUANTUM);
                        time += QUANTUM;
                    } else {
                        time += process.getBurstTime();
                        completionTime[getIndex(process, processes)] = time;
                        queue2.poll();
                    }
                } else {
                    // Move to the next queue if no process has arrived in this queue
                    queue2.add(queue3.poll());
                }
            } else {
                // Check the lowest-priority queue last
                Process process = queue3.poll();

                // Execute the process to completion
                time += process.getBurstTime();
                completionTime[getIndex(process, processes)] = time;
            }
        }

        // Print the completion time for each process
        for (int i = 0; i < processes.size(); i++) {
            System.out.println("Process " + processes.get(i).getName() + " completed at time " + completionTime[i]);
        }
    }

    private static int getIndex(Process process, List<Process> processes) {
        for (int i = 0; i < processes.size(); i++) {
            if (process.equals(processes.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
