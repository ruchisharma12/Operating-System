package schedule;
import java.util.*;

class Process {
    int pid;    // process id
    int at;     // arrival time
    int bt;     // burst time
    int priority;   // priority
    int ct;     // completion time
    int tat;    // turnaround time
    int wt;     // waiting time

    public Process(int pid, int at, int bt, int priority) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.priority = priority;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // create an array of processes
        Process[] processes = new Process[n];

        // input process details
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time, burst time and priority for process " + (i+1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int priority = sc.nextInt();
            processes[i] = new Process(i+1, at, bt, priority);
        }

        // sort processes by priority (non-preemptive)
        Arrays.sort(processes, Comparator.comparingInt(p -> p.priority));

        // simulate priority scheduling
        int time = 0;
        int remaining = n;
        boolean[] arrived = new boolean[n];
        while (remaining > 0) {
            // find the next arrived process with highest priority (preemptive)
            int idx = -1;
            int highest = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                if (arrived[i] && processes[i].priority > highest && processes[i].at <= time) {
                    highest = processes[i].priority;
                    idx = i;
                }
            }
            if (idx != -1) {
                Process p = processes[idx];
                p.bt--;
                time++;
                if (p.bt == 0) {
                    p.ct = time;
                    p.tat = p.ct - p.at;
                    p.wt = p.tat - p.bt;
                    remaining--;
                }
            } else {
                time++;
            }
            // mark newly arrived processes (non-preemptive)
            for (int i = 0; i < n; i++) {
                if (processes[i].at == time) {
                    arrived[i] = true;
                }
            }
        }

        // output process details
        System.out.println("\nProcess\tAT\tBT\tPriority\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println(p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.priority + "\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
        }
    }
}
