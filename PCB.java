import java.util.Comparator;

public class PCB { 
	private String PId;
	private int priority;
	private int ArrivalTime;
	private int CPU_burst;  
	private int StartTime;
	private int terminationTime;
	private int TurnArroundTime; 
	private int WaitingTime; 
	private int ResponseTime;
	private boolean check;
	private int RemainingburstTime;
	boolean process_terminated;
    boolean processinQ;

	 public PCB(int PId, int priority, int ArrivalTime, int CPU_burst,int RemainingburstTime) {
		this.PId = "P"+PId;
		this.priority = priority;
		this.ArrivalTime = ArrivalTime;
		this.CPU_burst = CPU_burst; 
		StartTime = 0;
		terminationTime = 0;
		TurnArroundTime = 0; 
		WaitingTime = 0; 
		ResponseTime = 0;
		this.RemainingburstTime = RemainingburstTime; 
		process_terminated = false;
        processinQ = false;
	}
	public void setRemainingburstTime(int p) {
		RemainingburstTime = p;
	}	

	 public int getRemainingburstTime() {
		return RemainingburstTime;
	}
	public void setPId(String pId) {
		PId = pId;
	}	

	 public String getPId() {
		return PId;
	}
	public void setprocess_terminated(boolean p) {
		process_terminated = p;
	}	

	 public boolean getprocess_terminated() {
		return process_terminated;
	}
	public void setprocessinQ(boolean p) {
		processinQ = p;
	}	

	 public boolean getprocessinQ() {
		return processinQ;
	}

	public int getPriority() {
			return priority;
		}

	public void setArrivalTime(int arrivalTime) {
		ArrivalTime = arrivalTime;
	}    

	public int getArrivalTime() {
		return ArrivalTime;
	}

	public void setCPU_burst(int cPU_burst) {
		CPU_burst = cPU_burst;
	}

	public int getCPU_burst() {
		return CPU_burst;
	}

	public void setStartTime(int startTime) {
		StartTime = startTime;
	}	

	public int getStartTime() {
		return StartTime;
	}

	public void setTerminationTime(int terminationTime) {
		this.terminationTime = terminationTime;
	}

	public int getTerminationTime() {
		return terminationTime;
	}

	public void setTurnArroundTime(int turnArroundTime) {
		TurnArroundTime = turnArroundTime;
	}

	public int getTurnArroundTime() {
		return TurnArroundTime;
	}

	public void setWaitingTime(int waitingTime) {
		WaitingTime = waitingTime;
	}

	public int getWaitingTime() {
		return WaitingTime;
	}

	public void setResponseTime(int responseTime) {
		ResponseTime = responseTime;
	}

	public int getResponseTime() {
		return ResponseTime;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isCheck() {
		return check;
	}
	static final Comparator<PCB> ORDER_BY_ARRIVALTIME = new Comparator<PCB>()
    {
        public int compare(PCB a1, PCB a2)
        {
            double c = a1.ArrivalTime - a2.ArrivalTime;
            if (c > 0) return 1;
            else if (c < 0) return -1;
            return 0;
        }
    };
	static final Comparator<PCB> ORDER_BY_PRIORITY = new Comparator<PCB>()
    {
        public int compare(PCB a1, PCB a2)
        {
            double c = a1.priority - a2.priority;
            if (c > 0) return 1;
            else if (c < 0) return -1;

            c = ORDER_BY_ARRIVALTIME.compare(a1, a2);
            if (c > 0) return 1;
            else if (c < 0) return -1;
            return 0;
        }
    };


	@Override
	 public String toString() {
		return "Process Id: "+PId+ " | Priority: " +priority+ " | CPU burst: "+CPU_burst+ " | Arrival time: "+ArrivalTime+ " | Start time: "+StartTime+ 
				" | Termination time: "+terminationTime+ " | Turnarround time: "+TurnArroundTime+ " | Waiting time: "+WaitingTime+" | Response time: "+ResponseTime;
	}

}
