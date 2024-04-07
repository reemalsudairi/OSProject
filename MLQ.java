import java.util.*;
public class MLQ {

    static Scanner input = new Scanner(System.in);
    static LinkedList<PCB> Q1 = new LinkedList<PCB>();
    static LinkedList<PCB> Q2 = new LinkedList<PCB>();
    static LinkedList<PCB> temp = new LinkedList<PCB>();
    double averageTAT,averageWT,averageRT;
    
    public static void main(String[] args){
        while (true) {
            System.out.println("1. Enter process' information.");
            System.out.println("2. Report detailed information about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    MLQ.addProcess();
                    break;
                case 2:

                break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
    }
}
    }

    public static void addProcess() {
        System.out.println("Enter the number of processes:");
        int numberOfProcesses = input.nextInt();

        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("Enter proiority for process " + (i + 1));
            int priority = input.nextInt();
            do{ 
                if(priority!=1||priority!=2){
                    System.out.println("Invalid proiority time\nEnter proiorty for process" + (i + 1) );
                    priority = input.nextInt(); }
                else
                    break;
              }while(true);

            System.out.println("Enter arrival time for process " + (i + 1));
            int arrivalTime = input.nextInt();
            do{ 
                if(arrivalTime<0){
                    System.out.println("Invalid arrival time\nEnter arrival time for process" + (i + 1) );
                    arrivalTime = input.nextInt(); }
                else
                    break;
              }while(true);

            System.out.println("Enter CPU burst for process " + (i + 1));
            int cpuBurst = input.nextInt();
            do{ 
                if(cpuBurst<0){
                    System.out.println("Invalid CPU burst time\nEnter CPU burst for process" + (i + 1) );
                    arrivalTime = input.nextInt(); }
                else
                    break;
              }while(true);

            PCB pcb = new PCB("P" + (i + 1), priority, arrivalTime, cpuBurst);
            temp.add(pcb);

            if (priority == 1) {
                Q1.add(pcb);
            } else if (priority == 2) {
                Q2.add(pcb);
            } 
        }
    }

    public void schedule() {
        Q1.sort(Comparator.comparingInt(PCB::getArrivalTime));
        Q2.sort(Comparator.comparingInt(PCB::getArrivalTime));

}

public void printReport() {
    System.out.println("Process Details:");
    for (PCB process : temp) {
        System.out.println("Process ID: " + process.getPId());
        System.out.println("Priority: " + process.getPriority());
        System.out.println("Arrival Time: " + process.getArrivalTime());
        System.out.println("CPU Burst: " + process.getCPU_burst());
        System.out.println("Start Time: " + process.getStartTime());
        System.out.println("Termination Time: " + process.getTerminationTime());
        System.out.println("Turnaround Time: " + process.getTurnArroundTime());
        System.out.println("Waiting Time: " + process.getWaitingTime());
        System.out.println("Response Time: " + process.getResponseTime());
        System.out.println("------------------"); 
    }

    calculateAverages();

}

public void calculateAverages() {
    double totalTurnaroundTime = 0;
    double totalWaitingTime = 0;
    double totalResponseTime = 0;

    for (PCB process : temp) {
        totalTurnaroundTime += process.getTurnArroundTime();
        totalWaitingTime += process.getWaitingTime();
        totalResponseTime += process.getResponseTime();
    }

    double avgTurnaroundTime = totalTurnaroundTime / temp.size();
    double avgWaitingTime = totalWaitingTime / temp.size();
    double avgResponseTime = totalResponseTime / temp.size();

    System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    System.out.println("Average Waiting Time: " + avgWaitingTime);
    System.out.println("Average Response Time: " + avgResponseTime);
}
}
