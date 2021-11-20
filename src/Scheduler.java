package src;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler {
    private Window window;

    public Scheduler(Window cpuWindow) {
        System.out.println("I am the scheduler");
        this.window = cpuWindow;
    }

    public void RoundRobin(int timeSlice) {
        System.out.println("I am round robin");

        float _timeSlice = timeSlice;
        Queue<Process> remainingProcesses = new LinkedList<>();
        Queue<String> _processes = new LinkedList<String>();
        //_processes.add("0, 3, Process A, 1");
        //_processes.add("2, 2, Process B, 1");
        System.out.println(_processes);
        boolean timeSliceUp = false;
        int timeRunning = 0;
        Timer time = new Timer();


        //Prints out which CPU this is and the size of the CPU Queue. If the CPU queue is empty it can't process anything

        while (CpuQueue.queueSize() > 0) {

            // Gets the current process and removes it from the queue
            Process current = CpuQueue.removeQueue(1);
            int processServiceTime = 0;

            // Update the wait table with the process
            System.out.println(current + " TEST " + 1); //Prints out current process
            window.UpdateWaitTable(2, current.getProcessID());

            // Get the service time of the process
            int serviceTime = current.getServiceTime();
            processServiceTime = current.getServiceTime();
            current.setCurrentServiceTime(serviceTime);

            // Runs as long as the service time exists
            for (int i = 0; i < current.getServiceTime(); i++) {

                // Sleeps the thread for the required amount of time
                try {
                    Thread.sleep(Timer.timeUnit);
                    serviceTime = serviceTime - 1;
                    current.setCurrentServiceTime(serviceTime);
                    timeRunning++;

                    // If the process has been running for longer than the allotted time, move it to the back of the queue
                    if (timeRunning >= _timeSlice) {
                        int remainingTime = current.getServiceTime() - timeRunning;
                        timeRunning = 0;
                        current.setServiceTime(remainingTime);
                        CpuQueue.addQueue(current);
                        break;
                    } else if (current.getServiceTime() <= 0) {
                        timeRunning = 0;
                        break;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                window.UpdateCPU(2, current.getProcessID(), current.getCurrentServiceTime());
            }
            common.totalTime += timeSlice;
            common.completedProcesses++;
            if (processServiceTime != 0) {
                window.UpdateFinishedTable(2, current.getProcessID(), common.totalTime, current.getArrivalTime(), processServiceTime);
            }
        }
        System.out.println("Finished!");
    }

    public void HRRN() {
        System.out.println("I am HRRN");
        int waitTime = 0;
        Queue<Process> remainingProcesses = new LinkedList<>();
        Queue<String> _processes = new LinkedList<String>();
        System.out.println(_processes);
        Timer time = new Timer();
        int timeRunning = 0;
        while (CpuQueue.queueSize() > 0) {

            // Gets the current process and removes it from the queue
            Process current = CpuQueue.removeQueue(1);

            // Update the wait table with the process
            window.UpdateWaitTable(1, current.getProcessID());

            // Get the service time of the process
            int serviceTime = current.getServiceTime();
            current.setCurrentServiceTime(serviceTime);
            current.setR((waitTime + serviceTime) / serviceTime);
            remainingProcesses.add(current);
        }
    }
}
