public class PCB {

    private int id;
    private int priority;
    private int arrivalTime;
    private int burstTime;
    private int startTime;
    private int terminateTime;
    private int turnAroundTime;
    private int waitTime;
    private int responseTime;
    private int timeInCPU;

    public PCB(int ID, int ArrivalTime, int BurstTime, int Priority) {
        id = ID;
        arrivalTime = ArrivalTime;
        burstTime = BurstTime;
        priority = Priority;
        waitTime = 0;
        startTime = 0;
        terminateTime = 0;
        turnAroundTime = 0;
        responseTime = 0;
        timeInCPU = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public int getTerminateTime() {
        return terminateTime;
    }

    public void setTerminateTime(int terminateTime) {
        this.terminateTime = terminateTime;
    }

    public int getTurnAroundTime() {
        return turnAroundTime;
    }

    public void setTurnAroundTime(int turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime = responseTime;
    }

    public int getTimeInCPU() {
        return timeInCPU;
    }

    public void setTimeInCPU(int timeInCPU) {
        this.timeInCPU = timeInCPU;
    }

    @Override
    public String toString() {
        return "Process ID: P" + id + "\n" + "Priority: " + priority + "\n"
                + "Burst Time: " + burstTime + "\n" + "Arrival Time: "
                + arrivalTime + "\n" + "Start Time: " + startTime + "\n"
                + "Termination Time: " + terminateTime + "\n"
                + "Turn around Time: " + turnAroundTime + "\n"
                + "Waiting Time: " + waitTime + "\n" + "Response Time: "
                + responseTime;
    }
}
