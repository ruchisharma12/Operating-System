package schduling;

import java.util.*;

class Processa {
    int pid;    // process id
    int at;     // arrival time
    int bt;     // burst time
    int ct;     // completion time
    int tat;    // turnaround time
    int wt;     // waiting time

    public Processa(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
    }
}

public class SJF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // create an array of processes
        Process[] processes = new Process[n];

        // input process details
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time and burst time for process " + (i+1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            processes[i] = new Process(i+1, at, bt);
        }

        // sort processes by burst time (non-preemptive)
        Arrays.sort(processes, Comparator.comparing(p -> p.bt));

        // simulate SJF scheduling
        int time = 0;
        int remaining = n;
        boolean[] arrived = new boolean[n];
        while (remaining > 0) {
            // find the next arrived process with smallest burst time (preemptive)
            int idx = -1;
            int shortest = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (arrived[i] && processes[i].bt < shortest && processes[i].at <= time) {
                    shortest = processes[i].bt;
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
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (Process p : processes) {
            System.out.println(p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
        }
    }
}
