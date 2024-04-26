import java.util.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class MLQ {

    static Scanner input = new Scanner(System.in);
    static ArrayList<PCB> Q1 = new ArrayList<PCB>();
    static  ArrayList<PCB> Q2 = new  ArrayList<PCB>();
    static ArrayList<PCB> MLQ = new ArrayList<PCB>();
   
    static int currentTime = 0; 
    //number of processes in MLQ
    static int numberOfProcesses=0;
     public static final int TIME_QUANTUM = 3;
    double averageTAT,averageWT,averageRT;
    
    public static void main(String[] args){
       
        int choice;
        System.out.println("Welcome to our program !");
        do{
            System.out.println("1. Enter process' information.");
            System.out.println("2. Report detailed information about each process and different scheduling criteria.");
            System.out.println("3. Exit the program.");
            System.out.print("please Enter your choice: ");
            choice = input.nextInt(); 
            switch (choice) {
                case 1:
                    addProcess();
                    shedule();
                    orderMLQ();
                    break;
                case 2:
                if(Q1.isEmpty()&& Q2.isEmpty() && MLQ.isEmpty()){
                 System.out.println("there are no processes in the queue");
                 break;}
               print(); 
                break;
                case 3:
                    System.out.println("Good bye !");
                    
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;}
    
        }while(choice != 3);
   
    }

    public static void addProcess() {
        System.out.println("Enter the number of processes:");
        int noOfProcesses = input.nextInt();
        int priority ;
        int arrivalTime ;
        int cpuBurst ;
        for (int i = 0; i < noOfProcesses; i++) {
            System.out.println("Process " + (numberOfProcesses + 1)+" :" );
            do{ 
                System.out.println("Enter priority for process " + (numberOfProcesses + 1)+" (enter 1 or 2)" );
                   priority = input.nextInt(); 
               if(priority!=1 && priority!=2){
                System.out.println("Invalid proiority! please enter 1 or 2");
               }
              }while(priority!=1 && priority!=2);

            do{ 
                
                    System.out.println("Enter arrival time for process" + (numberOfProcesses + 1) +" (enter a value more than or equal to 0)" );
                    arrivalTime = input.nextInt(); 
                    if(arrivalTime<0){
                        System.out.println("Invalid arrival time! please enter a value more than or equal to 0");
                       }
              }while(arrivalTime<0);
             
            do{ 
               
                    System.out.println("Enter CPU burst for process" + (numberOfProcesses + 1) +" (enter a value more than 0)" );
                    cpuBurst = input.nextInt(); 
                    if(cpuBurst<=0){
                        System.out.println("Invalid CPU burst time! please enter a value more than 0");
                       }
              }while(cpuBurst<=0);
             
            PCB pcb = new PCB((numberOfProcesses + 1), priority, arrivalTime, cpuBurst,cpuBurst);
            
          MLQ.add(pcb); 
          numberOfProcesses=numberOfProcesses+1;
        }
        printOutput();
    }

    public static void shedule() {
    // Sort the MLQ based on arrival time and priority so that we can use it in  orderMLQ method
    Collections.sort(MLQ, PCB.ORDER_BY_PRIORITY);
    Collections.sort(MLQ, PCB.ORDER_BY_ARRIVALTIME);
   
   
    }


       
   
       
        
// Method to find the shortest job in the queue
private static PCB findShortestJob(LinkedList<PCB> queue) {
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


public static void orderMLQ() {
    System.out.println("entered MLQ method ");
    boolean x=true;
    currentTime=MLQ.get(0).getArrivalTime();
    for (int i = 0; i < MLQ.size(); i++) {
        System.out.println("entered the first for loop enetering in q1 or q2 ");
        System.out.println(MLQ.get(i).toString());
        
            System.out.println("entered the if statment in the first for loop for entering the Q1 and Q2 ");
            if (MLQ.get(i).getPriority() == 1) {
                System.out.println("entered the is statment for the priority 1 ");
                Q1.add(MLQ.get(i));
                MLQ.get(i).setprocessinQ(true);
                MLQ.get(i).setStartTime(currentTime);
            } else {
                System.out.println("entered the else statment for the priority 2");
                Q2.add(MLQ.get(i));
                MLQ.get(i).setprocessinQ(true);
              
            }
        
    }while (x) {
        System.out.println("entered the isempty while loop ");
        
      
        if (Q1.size() != 0) {
            System.out.println("entered the first if statment for priority 1 ");
            for (int i = 0; i < Q1.size(); i++) {
                System.out.println("entered the second for loop for shifting through q1 ");
               
                if (Q1.get(i).getRemainingburstTime() > 0) {

                    if (Q1.get(i).getRemainingburstTime() > TIME_QUANTUM) {
                        System.out.println("entered if statment when the remaining time is more than the quantum time ");
                        currentTime += TIME_QUANTUM;
                        Q1.get(i).setRemainingburstTime(Q1.get(i).getRemainingburstTime() - TIME_QUANTUM);
                    } else {
                        System.out.println("entered the if statment when the remaning time is less than or equal to the quantum time  ");
                        currentTime += Q1.get(i).getRemainingburstTime();
                        System.out.println(currentTime);
                        Q1.get(i).setTerminationTime(currentTime);
                        Q1.get(i).setTurnArroundTime(Q1.get(i).getTerminationTime() - Q1.get(i).getArrivalTime());
                        Q1.get(i).setWaitingTime( Q1.get(i).getTurnArroundTime()- Q1.get(i).getCPU_burst());
                       
                        Q1.get(i).setRemainingburstTime(0);
                        Q1.get(i).setprocess_terminated(true);
                        Q1.get(i).setResponseTime(Q1.get(i).getStartTime()-Q1.get(i).getArrivalTime());
                        Q1.remove(i); // Corrected removal
                        i--;
                    }
                    // For any new processes that have arrived while doing the previous steps
                    for (int j = 0; j < numberOfProcesses; j++) {
                        System.out.println("entered the for loop to check if there are new processes that entered while doing the previous steps ");
                        if (!MLQ.get(j).getprocess_terminated() && !MLQ.get(j).getprocessinQ() && MLQ.get(j).getArrivalTime() <= currentTime) {
                           
                            if (MLQ.get(j).getPriority() == 1) {
                                Q1.add(MLQ.get(j));
                                MLQ.get(j).setprocessinQ(true);
                            }
                        }
                    }
                }
            }
        } else if (Q2.size() != 0) {
            System.out.println("entered the if statment for q2 and checking if it is not empty ");
            Collections.sort(Q2, PCB.ORDER_BY_BURSTTIME);
                Q2.get(0).setStartTime(currentTime);
                currentTime += Q2.get(0).getCPU_burst();
                Q2.get(0).setResponseTime(Q2.get(0).getStartTime()-Q2.get(0).getArrivalTime());
                Q2.get(0).setTerminationTime(currentTime);
                Q2.get(0).setTurnArroundTime(Q2.get(0).getTerminationTime() - Q2.get(0).getArrivalTime()) ;
                Q2.get(0).setWaitingTime(Q2.get(0).getTurnArroundTime() - Q2.get(0).getCPU_burst()) ;
                Q2.get(0).setprocess_terminated(true) ;
                Q2.get(0).setRemainingburstTime(0);
                Q2.remove(0);
           
        } else if(Q1.isEmpty() && Q2.isEmpty()){
            System.out.println("the if statment when both lists are empty");
            x=false;
        }
         else{
            System.out.println("entered else statment when we increment the current time ");
            currentTime++;}
    }
}


public static void print (){
	writeReportToFile();
     printOutput();
     printReport();    
}

public static void printOutput() {
    System.out.println("Process Details:");
    for (PCB process : MLQ ) {
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
}


private static void writeReportToFile() {
    try {
        FileWriter writer = new FileWriter("Report.txt");
        writer.write("ProcessID | Priority | ArrivalTime | BurstTime | StartTime | TerminationTime | TurnaroundTime | WaitingTime | ResponseTime\n");
        for (PCB process : MLQ) {
            writer.write(process.getPId() + " | " + process.getPriority() + " | " + process.getArrivalTime() + " | " +
                    process.getCPU_burst() + " | " + process.getStartTime() + " | " + process.getTerminationTime() + " | " +
                    process.getTurnArroundTime() + " | " + process.getWaitingTime() + " | " + process.getResponseTime() + "\n");
        }
        double avgTurnaroundTime = MLQ.stream().mapToDouble(PCB::getTerminationTime).average().orElse(0);
        double avgWaitingTime = MLQ.stream().mapToDouble(PCB::getWaitingTime).average().orElse(0);
        double avgResponseTime = MLQ.stream().mapToDouble(PCB::getResponseTime).average().orElse(0);
        writer.write("Average Turnaround Time: " + avgTurnaroundTime + "\n");
        writer.write("Average Waiting Time: " + avgWaitingTime + "\n");
        writer.write("Average Response Time: " + avgResponseTime + "\n");
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    // new printReport Code including fail Printing 
public static void printReport() {
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

public static void calculateAverages() {
    double totalTurnaroundTime = 0;
    double totalWaitingTime = 0;
    double totalResponseTime = 0;

    for (PCB process : MLQ ) {
        totalTurnaroundTime += process.getTurnArroundTime();
        totalWaitingTime += process.getWaitingTime();
        totalResponseTime += process.getResponseTime();
    }

    double avgTurnaroundTime = totalTurnaroundTime /  MLQ.size();
    double avgWaitingTime = totalWaitingTime /  MLQ.size();
    double avgResponseTime = totalResponseTime /  MLQ.size();

     System.out.println("Average Turnaround Time: " + avgTurnaroundTime);
    System.out.println("Average Waiting Time: " + avgWaitingTime);
    System.out.println("Average Response Time: " + avgResponseTime);

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
