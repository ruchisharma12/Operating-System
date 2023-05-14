package schduler;
import java.util.*;

class Process {
    int pid;        // process ID
    int arrival;    // arrival time
    int burst;      // burst time
    int priority;   // priority level
    int wait;       // waiting time
    int turnaround; // turnaround time
    int remaining;  // remaining burst time

    Process(int pid, int arrival, int burst, int priority) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.priority = priority;
        this.wait = 0;
        this.turnaround = 0;
        this.remaining = burst;
    }
}

public class MFQScheduling {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // input the number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // input the processes
        List<Process> processes = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            System.out.println("Enter process " + i + " details:");
            System.out.print("Arrival time: ");
            int arrival = sc.nextInt();
            System.out.print("Burst time: ");
            int burst = sc.nextInt();
            System.out.print("Priority: ");
            int priority = sc.nextInt();
            processes.add(new Process(i, arrival, burst, priority));
        }

        // sort the processes by arrival time
        Collections.sort(processes, Comparator.comparingInt(p -> p.arrival));

        // input the time quantum and the number of queues
        System.out.print("Enter the time quantum: ");
        int quantum = sc.nextInt();
        System.out.print("Enter the number of queues: ");
        int queues = sc.nextInt();

        // create the queues
        List<List<Process>> queue = new ArrayList<>();
        for (int i = 0; i < queues; i++) {
            queue.add(new ArrayList<>());
        }

        // add the processes to the first queue
        queue.get(0).addAll(processes);

        // execute the queues
        int time = 0;
        int completed = 0;
        while (completed < n) {
            // get the next process from the highest-priority non-empty queue
            Process p = null;
            for (int i = queues - 1; i >= 0; i--) {
                if (!queue.get(i).isEmpty()) {
                    p = queue.get(i).get(0);
                    break;
                }
            }

            if (p != null) {
                // execute the process for the time quantum
                int remaining = p.remaining;
                if (remaining > quantum) {
                    remaining = quantum;
                }
                time += remaining;
                p.remaining -= remaining;

                // move the process to the appropriate queue
                if (p.remaining == 0) {
                    p.turnaround = time - p.arrival;
                    p.wait = p.turnaround - p.burst;
                    queue.get(p.priority).remove(p);
                    completed++;
                } else if (p.priority < queues - 1) {
                    queue.get(p.priority).remove(p);
                    p.priority++;
                    queue.get(p.priority).add(p);
                } else {
                    queue.get(p.priority).remove(p);
                    queue.get(p.priority).add(p);
                }
            } else {
                // no process is available to execute, increment time
                time++;
            }
        }

        // output the results
       
        // output the results
        System.out.println("Process\tArrival\tBurst\tPriority\tWait\tTurnaround");
        for (Process p : processes) {
            System.out.println(p.pid + "\t" + p.arrival + "\t" + p.burst + "\t" +
                    p.priority + "\t\t" + p.wait + "\t" + p.turnaround);
        }
    }
}
