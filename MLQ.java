import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class MLQ {

    static Scanner input = new Scanner(System.in);
    static LinkedList<PCB> Q1 = new LinkedList<PCB>();
    static LinkedList<PCB> Q2 = new LinkedList<PCB>();
    static LinkedList<PCB> MLQ = new LinkedList<PCB>();
     static LinkedList<PCB> temp = new LinkedList<PCB>(); //منب متأكدة وشو هذا 
    double averageTAT,averageWT,averageRT;
    
    public static void main(String[] args){
        Boolean x=true;
        System.out.println("Welcome to our program !");
        while (x) {
        
              System.out.println("1. Enter process' information.");
            System.out.println("2. Report detailed information about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.print("please Enter your choice: ");

            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    temp.addProcess();//هنا وش قصدك MLQ   
                    break;
                case "2":

                break;
                case "3":
                    System.out.println("Good bye !");
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
        private static void scheduleProcesses(List<PCB> q1, List<PCB> q2, List<PCB> completedProcesses) {
        int currentTime = 0;

        while (!q1.isEmpty() || !q2.isEmpty()) {
            if (!q1.isEmpty()) {
                PCB process = q1.get(0);
                process.setStartTime(currentTime);
                int remainingTime = process.getBurstTime();
                if (remainingTime <= TIME_QUANTUM) {
                    currentTime += remainingTime;
                    process.setTerminationTime(currentTime);
                    process.setTurnaroundTime(currentTime - process.getArrivalTime());
                    process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
                    process.setResponseTime(process.getStartTime() - process.getArrivalTime());
                    completedProcesses.add(process);
                    q1.remove(0);
                } else {
                    currentTime += TIME_QUANTUM;
           process.setBurstTime(remainingTime - TIME_QUANTUM);
                    q1.remove(0);
                    q1.add(process);
                }
            } else {
                if (!q2.isEmpty()) {
                    q2.sort((p1, p2) -> p1.getBurstTime() - p2.getBurstTime());
                    PCB process = q2.get(0);
                    process.setStartTime(currentTime);
                    currentTime += process.getBurstTime();
                    process.setTerminationTime(currentTime);
                    process.setTurnaroundTime(currentTime - process.getArrivalTime());
                    process.setWaitingTime(process.getTurnaroundTime() - process.getBurstTime());
                    process.setResponseTime(process.getStartTime() - process.getArrivalTime());
                    completedProcesses.add(process);
                    q2.remove(0);
                }
            }
        }
    }
private static void displayReport(List<PCB> completedProcesses) {
    System.out.println("Scheduling order of processes:");
    for (PCB process : completedProcesses) {
        System.out.println(process.getProcessID());
    }
    System.out.println();

    System.out.println("---------------------------------------------------------------------------------------------------------------------");
    System.out.println("|                              Process Details                                 |");
    System.out.println("---------------------------------------------------------------------------------------------------------------------");

    for (PCB process : completedProcesses) {
        System.out.println("ProcessID: " + process.getProcessID());
        System.out.println("Priority: " + process.getPriority());
        System.out.println("ArrivalTime: " + process.getArrivalTime());
        System.out.println("BurstTime: " + process.getBurstTime());
        System.out.println("StartTime: " + process.getStartTime());
        System.out.println("TerminationTime: " + process.getTerminationTime());
        System.out.println("TurnaroundTime: " + process.getTurnaroundTime());
        System.out.println("WaitingTime: " + process.getWaitingTime());
        System.out.println("ResponseTime: " + process.getResponseTime());
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }

    double avgTurnaroundTime = completedProcesses.stream().mapToDouble(PCB::getTurnaroundTime).average().orElse(0);
    double avgWaitingTime = completedProcesses.stream().mapToDouble(PCB::getWaitingTime).average().orElse(0);
    double avgResponseTime = completedProcesses.stream().mapToDouble(PCB::getResponseTime).average().orElse(0);

    System.out.println("---------------------------------------------------------------------------------------------------------------------");
    System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);
    System.out.printf("Average Waiting Time: %.2f\n", avgWaitingTime);
    System.out.printf("Average Response Time: %.2f\n", avgResponseTime);
    System.out.println("---------------------------------------------------------------------------------------------------------------------");
}

    private static void writeReportToFile(List<PCB> completedProcesses) {
        try {
            FileWriter writer = new FileWriter("Report.txt");
            writer.write("ProcessID | Priority | ArrivalTime | BurstTime | StartTime | TerminationTime | TurnaroundTime | WaitingTime | ResponseTime\n");
            for (PCB process : completedProcesses) {
                writer.write(process.getProcessID() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " +
                        process.getBurstTime() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " +
                        process.getTurnaroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
            }
            double avgTurnaroundTime = completedProcesses.stream().mapToDouble(PCB::getTurnaroundTime).average().orElse(0);
            double avgWaitingTime = completedProcesses.stream().mapToDouble(PCB::getWaitingTime).average().orElse(0);
            double avgResponseTime = completedProcesses.stream().mapToDouble(PCB::getResponseTime).average().orElse(0);
            writer.write("Average Turnaround Time: " + avgTurnaroundTime + "\n");
            writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
            writer.write("Average Response Time: " + avgResponseTime + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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


public void  orderMLQ(){
 // MLQ arraylist already created 
PCB temp ;
while (!Q1.isEmpty() ) {
    temp = Q1.remove(0);  
    int tempBurst = temp.getCPU_burst();
if(tempBurst>3){//check if this processes needs to enter the queue again
Q1.add(temp); }
tempBurst= tempBurst-3 ; //3 Quantm
temp.setCPU_burst(tempBurst);
MLQ.add(temp);
}
while (!Q2.isEmpty() ){
temp=Q2.remove(0);
MLQ.add(temp);
}
if(MLQ.isEmpty()){
System.out.println("there is no processes");
return ;
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
public  void printReport() {
    try {
        PrintWriter writer = new PrintWriter(new FileWriter("Report.txt"));
        
        writer.println("Process Details:");

        for (PCB process : MLQ) {
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
}