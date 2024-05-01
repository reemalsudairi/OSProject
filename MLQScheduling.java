import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class MLQScheduling {
	
	private String CPU_State; // IDLE, BUSY
	private int Clock;
	private PCB Current_Process;
	private static Scanner scr = null;
	private static PrintStream out = null;
	private static MLQScheduling scheduling;

	private ArrayList<PCB> Q1 = new ArrayList<PCB>();
	private ArrayList<PCB> Q2 = new ArrayList<PCB>();
	private ArrayList<String> MLQ = new ArrayList<String>();
	private static ArrayList<PCB> PQ = new ArrayList<PCB>();

	public static void main(String[] args) {
		try {
			scr = new Scanner(System.in);
			
			out = new PrintStream("Report.txt");

			char choice;
			do {

				System.out.println("1. Enter process information.");
				System.out.println("2. Report detailed information about each process and different scheduling criteria.");
				System.out.println("3. Exit the program.");
				System.out.print("Please Enter your choice: ");

				choice = scr.next().charAt(0);

				switch (choice) {
				case '1':
					setupPCB();
					break;
				case '2':
					MLQReport();
					break;
				case '3':
					break;
				default:
					System.out.println("Invalid choice. Please enter 1, 2, or 3.");
				}

				System.out.println();

			} while (choice != '3');

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (scr != null)
			scr.close();

		if (out != null)
			out.close();
	}

	private static void setupPCB() {
		
		PQ = new ArrayList<PCB>();

		System.out.print("Enter the number of processes:");
		int num = scr.nextInt();
		for (int i = 1; i <= num; i++) {
			System.out.println("Process P" + i + ":");

			System.out.print(" Enter Process Priority : ");
			int priority = scr.nextInt();
			while (priority < 1 || priority > 2) {
				System.out.println("priority must be in range [1 - 2]");
				System.out.print("Priority : ");
				priority = scr.nextInt();
			}

			System.out.print(" Enter Arrival Time: ");
			int arrivalTime = scr.nextInt();

			System.out.print(" Enter CPU burst: ");
			int burstTime = scr.nextInt();

			PCB pcb = new PCB(i, arrivalTime, burstTime, priority);
			PQ.add(pcb);
		}
	}

	public static void MLQReport() {
		startScheduling();
		printReport(System.out);
		printReport(out);
	}

	public static void startScheduling() {
		for (PCB p : PQ) {
			p.setStartTime(0);
			p.setTerminateTime(0);
			p.setWaitTime(0);
			p.setTimeInCPU(0);
		}

		scheduling = new MLQScheduling(PQ);
		scheduling.excute();
	}

	public static void printReport(PrintStream out) {
		for (PCB p : PQ) {
			out.println(p);
			out.println("---------------");
		}
		out.println("Process order chart: " + scheduling.getChartString());
		out.println("---------------");
		
		int size = PQ.size();
		double totalTurnAround = 0;
		double totalWait = 0;
		double totalResponse = 0;

		for (PCB p : PQ) {
			totalWait += p.getWaitTime();
			totalTurnAround += p.getTurnAroundTime();
			totalResponse += p.getResponseTime();
		}

		out.println("Average turnaround time : " + totalTurnAround / size);
		out.println("Average waiting time    : " + totalWait / size);
		out.println("Average response time   : " + totalResponse / size);

		out.println("---------------");
	}

	public MLQScheduling(ArrayList<PCB> PQ) {
		Q1 = new ArrayList<PCB>();
		Q2 = new ArrayList<PCB>();
		
		for (int i = 0; i < PQ.size(); i++) {
			PCB p = PQ.get(i);
			if (p.getPriority() == 1) {
				int j = 0;
				for (j = 0; j < Q1.size(); j++) {
					if (p.getArrivalTime() < Q1.get(j).getArrivalTime())
						break;
				}
				Q1.add(j, p);
			} else {
				int j = 0;
				for (j = 0; j < Q2.size(); j++) {
					if (p.getBurstTime() < Q2.get(j).getBurstTime())
						break;
				}
				Q2.add(j, p);
			}			
		}
	}

	private PCB getNextProcess(int time) {
		for (int i = 0; i < Q1.size(); i++) {
			if (Q1.get(i).getArrivalTime() <= time) {
				return Q1.remove(i);
			}
		}

		for (int i = 0; i < Q2.size(); i++) {
			if (Q2.get(i).getArrivalTime() <= time) {
				return Q2.remove(i);
			}
		}
		return null;
	}

	public void excute() {
		Clock = 0;
		CPU_State = "IDLE";
		int quantum = 3;
		int qCounter = 0;

		while (!Q1.isEmpty() || !Q2.isEmpty() || CPU_State.equals("BUSY")) {
			if (CPU_State.equals("IDLE")) {
				PCB next = getNextProcess(Clock);
				if (next != null) {
					runPCB(next);
					qCounter = 0;
				}
			} else if (Current_Process != null && Current_Process.getPriority() == 1 && !Q1.isEmpty()) {
				if (qCounter == quantum) {
					Q1.add(Current_Process);
					
					PCB next = getNextProcess(Clock);
					if (next != null) {
						runPCB(next);
						qCounter = 0;
					}
				}
			} else if (Current_Process != null && Current_Process.getPriority() == 2 && !Q1.isEmpty()) {
				
				if (Q1.get(0).getArrivalTime() <= Clock) {
					//Q2.add(Current_Process);
					
					// Sort queue by burst time
					int j = 0;
					for (j = 0; j < Q2.size(); j++) {
						if (Current_Process.getBurstTime() < Q2.get(j).getBurstTime())
							break;
					}
					Q2.add(j, Current_Process);
					
					PCB next = getNextProcess(Clock);
					if (next != null) {
						runPCB(next);
						qCounter = 0;
					}
				}
			}

			Clock++;

			if (CPU_State.equals("BUSY") && Current_Process != null) {
				Current_Process.setTimeInCPU(Current_Process.getTimeInCPU() + 1);
				qCounter++;
				if (Current_Process.getTimeInCPU() == Current_Process.getBurstTime()) {
					terminatePCB(Current_Process);
				}
			}
		}
	}

	private void runPCB(PCB p) {
		if (p.getTimeInCPU() == 0)
			p.setStartTime(this.Clock);

		MLQ.add("P" + p.getId());
		
		CPU_State = "BUSY";
		Current_Process = p;
	}

	private void terminatePCB(PCB p) {
		p.setTerminateTime(Clock);
		p.setWaitTime(p.getTerminateTime() - p.getArrivalTime() - p.getBurstTime());
		p.setResponseTime(p.getStartTime() - p.getArrivalTime());
		p.setTurnAroundTime(p.getTerminateTime() - p.getArrivalTime());
		
		CPU_State = "IDLE";
		Current_Process = null;
	}

	public String getChartString() {
		String chart = "[";
		
		for (String p : MLQ) {
			chart += " " + p + " |";
		}

		if (chart.length() > 1)
			chart = chart.substring(0, chart.length() - 1);
		
		chart += "]";
		
		return chart;
	}

}