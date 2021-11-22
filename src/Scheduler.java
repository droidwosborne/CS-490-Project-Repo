/*
 * CpuQueue.java
 * CS 490 Team 3 Fall 2021
 * Creates the CPU Queue
 */
package src;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Contains the scheduling algorithms to run
 */
public class Scheduler {
    private Window window;

    /**
     * Default constructor to set up the window
     * @param cpuWindow
     */
    public Scheduler(Window cpuWindow) {
        System.out.println("I am the scheduler");
        this.window = cpuWindow;
    }

    /**
     * A method to run Round Robin on the CPU. It will go through each process for a specified time slice
     * until all processes are finished.
     * @param timeSlice
     */
    public void RoundRobin(int timeSlice) {
        System.out.println("I am round robin");

        float _timeSlice = timeSlice;
        int timeRunning = 0;
        Timer time = new Timer();

        // Run until the CPU is empty
        while (CpuQueue.queueSize() > 0) {

            // Gets the current process and removes it from the queue
            Process current = CpuQueue.removeQueue(1);
            int processServiceTime = 0;

            // Update the wait table with the process
            window.UpdateWaitTable(2, current.getProcessID());

            // Get and set the service time of the process
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
            // Update the current time
            common.totalTime += timeSlice;
            common.completedProcesses++;
            if (processServiceTime != 0) {
                window.UpdateFinishedTable(2, current.getProcessID(), common.totalTime, current.getArrivalTime(), processServiceTime);
            }
        }
        System.out.println("Round Robin Finished!");
    }

    /**
     * Method to run the Highest Response Ratio next algorithm. It will calculate the HRR for every
     * process and run the one with the HRR.
     */
    public void HRRN() {
        System.out.println("I am HRRN");

        // Declare all method variables
        int waitTime = 0;
        int previousProcessFinishTime = 0;
        double hrr = 0;
        Queue<Process> collected = new LinkedList<>();
        Queue<Process> finished = new LinkedList<>();
        boolean isCurrent = false;
        Process current = new Process(0,"empty", 0,0);
        int currTime = 0;
        int remainingServiceTime = 0;
        int processServiceTime = 0;

        // Run until all processes are finished
        while (CpuQueue2.queueSize() > 0) {

            // Gets the current process and removes it from the queue
            try {
                // Checks if the current running process is finished and the next process is ready to be loaded in
                Process t_curr = CpuQueue2.peekQueue();
                if (t_curr.getArrivalTime() <= currTime && current.getCurrentServiceTime() <= 0) {
                    current = CpuQueue2.removeQueue(1);
                    processServiceTime = current.getServiceTime();
                    current.setCurrentServiceTime(current.getServiceTime());
                    remainingServiceTime = current.getCurrentServiceTime();

                    // Verify the process is not already collected
                    if (!collected.contains(current))
                    {
                        collected.add(current);
                    }
                }
                // Still add to the queue even if a process is running
                else if (t_curr.getArrivalTime() <= currTime)
                {
                    if (!collected.contains(current))
                    {
                        Process t_current = CpuQueue2.removeQueue(1);
                        collected.add(t_current);
                    }
                }
                // Sleep for the time unit and update timers
                Thread.sleep(Timer.timeUnit);
                currTime += 1;
                remainingServiceTime -= 1;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            current.setCurrentServiceTime((remainingServiceTime));

            // Go through the queue and find the process with the highest response ratio
            if (collected.contains(current))
            {
                Process t_curr = current;
                for (Process p : collected)
                {
                    if (p.getR() > hrr && collected.contains(p) && !finished.contains(p))
                    {
                        hrr = p.getR();
                        t_curr = p;
                    }
                }
                current = t_curr;
                hrr = 0;
                isCurrent = true;
            }

            // Update the wait table with the process
            window.UpdateWaitTable(1, current.getProcessID());

            // Update the wait time
            waitTime = previousProcessFinishTime - current.getArrivalTime();

            // Get the service time of the process
            int serviceTime = current.getServiceTime();

            // Set the response ratio
            current.setR((waitTime + serviceTime) / serviceTime);

            // Add the process back to the queue if it isn't the one being ran and another is still running
            if (!isCurrent && remainingServiceTime > 0)
            {
                CpuQueue2.addQueue(current);
            }
            // Else it is finished and needs to be added to the finished queue
            else if (current.getCurrentServiceTime() <= 0)
            {
                finished.add(current);
            }

            window.UpdateCPU(1, current.getProcessID(), current.getCurrentServiceTime());

            previousProcessFinishTime = previousProcessFinishTime + current.getServiceTime();

            isCurrent = false;
            window.UpdateFinishedTable(1, current.getProcessID(), common.totalTime, current.getArrivalTime(), processServiceTime);
        }
        System.out.println("Finished HRRN!");


    }
}
