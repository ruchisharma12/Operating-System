package schduling;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {
    int pid;    // process id
    int at;     // arrival time
    int bt;     // burst time
    int ct;     // completion time
    int tat;    // turnaround time
    int wt;     // waiting time

    public Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
    }
}
public class FCFS {
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

	        // sort processes by arrival time (non-preemptive)
	        Arrays.sort(processes, Comparator.comparing(p -> p.at));

	        // simulate FCFS scheduling
	        int time = 0;
	        for (Process p : processes) {
	            if (time < p.at) {
	                time = p.at;
	            }
	            p.ct = time + p.bt;
	            p.tat = p.ct - p.at;
	            p.wt = p.tat - p.bt;
	            time = p.ct;
	        }

	        // output process details
	        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
	        for (Process p : processes) {
	            System.out.println(p.pid + "\t" + p.at + "\t" + p.bt + "\t" + p.ct + "\t" + p.tat + "\t" + p.wt);
	        }
	    }
}
