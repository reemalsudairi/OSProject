import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PCB {
    private String processID;
    private int priority;
    private int arrivalTime;
    private int burstTime;
    private int startTime;
    private int terminationTime;
    private int turnaroundTime;
    private int waitingTime;
    private int responseTime;

    public PCB(String processID, int priority, int arrivalTime, int burstTime) {
        this.processID = processID;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
    }

    // Getters and setters...
    // (Omitted for brevity)
  // Getters and setters
    public String getProcessID() {
        return processID;
    }

    public int getPriority() {
        return priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getTerminationTime() {
        return terminationTime;
    }

    public void setTerminationTime(int terminationTime) {
        this.terminationTime = terminationTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }
}
    // Methods for calculating times...
    // (Omitted for brevity)

public class Scheduler {
    private static final int TIME_QUANTUM = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<PCB> q1 = new ArrayList<>();
        List<PCB> q2 = new ArrayList<>();
        List<PCB> completedProcesses = new ArrayList<>();

        System.out.println("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Enter process priority (1 or 2), arrival time, and burst time: ");
            int priority = scanner.nextInt();
            int arrivalTime = scanner.nextInt();
            int burstTime = scanner.nextInt();

            PCB process = new PCB("P" + (i + 1), priority, arrivalTime, burstTime);

            if (priority == 1)
                q1.add(process);
            else
                q2.add(process);
        }

        // Implement scheduling algorithms
        scheduleProcesses(q1, q2, completedProcesses);

        // Output detailed information
        displayReport(completedProcesses);

        // Write report to file
        writeReportToFile(completedProcesses);

        scanner.close();
    }

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
