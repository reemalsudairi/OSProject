import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

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
    // Sort Q1 and Q2 based on arrival time
    Q1.sort(Comparator.comparingInt(PCB::getArrivalTime));
    Q2.sort(Comparator.comparingInt(PCB::getArrivalTime));

    int currentTime = 0; // Initialize current time

    // SJF scheduling for Q2
    while (!Q2.isEmpty()) {
        PCB shortestJob = findShortestJob(Q2);
        shortestJob.setStartTime(currentTime);
        shortestJob.setResponseTime(currentTime - shortestJob.getArrivalTime());

        int remainingTime = shortestJob.getCPU_burst();
        while (remainingTime > 0) {
            currentTime++;
            remainingTime--;
            if (remainingTime == 0) {
                shortestJob.setTerminationTime(currentTime);
                shortestJob.setTurnArroundTime(shortestJob.getTerminationTime() - shortestJob.getArrivalTime());
                shortestJob.setWaitingTime(shortestJob.getStartTime() - shortestJob.getArrivalTime());
                // Add the processed job to temp list
                temp.add(shortestJob);
            }
        }
    }

        /// End od SJF 
        /// RR code should be here
}

// Method to find the shortest job in the queue
private PCB findShortestJob(LinkedList<PCB> queue) {
    PCB shortestJob = queue.getFirst();
    for (PCB job : queue) {
        // Case 1: If the current process has a shorter burst time than the shortest job
        if (job.getCPU_burst() < shortestJob.getCPU_burst()) {
            shortestJob = job;
        }
        // Case 2: If two processes have the same burst time, compare their arrival times
        else if (job.getCPU_burst() == shortestJob.getCPU_burst()) {
            if (job.getArrivalTime() < shortestJob.getArrivalTime()) {
                shortestJob = job;
            }
            // Case 3: If two processes have the same burst time and arrival time, compare their process IDs
            else if (job.getArrivalTime() == shortestJob.getArrivalTime() && job.getPId().compareTo(shortestJob.getPId()) < 0) {
                shortestJob = job;
            }
        }
    }
    queue.remove(shortestJob); // Remove the shortest job from the queue
    return shortestJob;
}


/* public void printReport() {
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

}*/ 
    
    // new printReport Code including fail Printing 
public void printReport() {
    try {
        PrintWriter writer = new PrintWriter(new FileWriter("Report.txt"));
        
        writer.println("Process Details:");
        for (PCB process : temp) {
            writer.println("Process ID: " + process.getPId());
            writer.println("Priority: " + process.getPriority());
            writer.println("Arrival Time: " + process.getArrivalTime());
            writer.println("CPU Burst: " + process.getCPU_burst());
            writer.println("Start Time: " + process.getStartTime());
            writer.println("Termination Time: " + process.getTerminationTime());
            writer.println("Turnaround Time: " + process.getTurnArroundTime());
            writer.println("Waiting Time: " + process.getWaitingTime());
            writer.println("Response Time: " + process.getResponseTime());
            writer.println("------------------"); 
        }

        // Close the writer to ensure all data is flushed and the file is properly closed
        writer.close();

        calculateAverages();
    } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
    }
}


/*public void calculateAverages() {
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
}*/ 
    // new calculateAverages Code including fail Printing 

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

    try {
        PrintWriter writer = new PrintWriter(new FileWriter("Report.txt", true)); // Append to the file

        writer.println("Average Turnaround Time: " + avgTurnaroundTime);
        writer.println("Average Waiting Time: " + avgWaitingTime);
        writer.println("Average Response Time: " + avgResponseTime);

        // Close the writer to ensure all data is flushed and the file is properly closed
        writer.close();
    } catch (IOException e) {
        System.err.println("Error writing to file: " + e.getMessage());
    }
}

    
}
