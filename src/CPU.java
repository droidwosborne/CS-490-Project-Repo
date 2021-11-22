/*
 * CPU.java
 * CS 490 Team 3 Fall 2021
 * CPU Class that makes threads and goes through queue.
 */
package src;

/**
 * CPU class in order to run process threads on the correct CPU
 */
public class CPU extends Thread{
    private int cpuNumber;
    private Window window;

    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    /**
     * Default constructor of the CPU class
     * @param cpuNumber is the number of the CPU
     * @param window is the window that the CPU is using
     */
    public CPU(int cpuNumber, Window window) {
        this.cpuNumber = cpuNumber;
        this.window = window;

    }

    /**
     * Runs the CPU and the processes on it
     */
    public void run() {
        Scheduler scheduler = new Scheduler(window);
        scheduler.RoundRobin(window.getTimeSliceTextField());
    }

    /**
     * Run a Process on the CPU
     * @param processName string of the process name
     * @param serviceTime  integer of the service time
     * @param CPUIsRunning  a bool if the passed in CPU is running or not
     * @return if the process finishes or not
     */
    public boolean RunProcess(String processName, int serviceTime, boolean CPUIsRunning)
    {

        boolean success = false;
        Sleeper sleeper = new Sleeper();

        // Ensure the passed in CPU is not running something else
        if(!CPUIsRunning)
        {

            System.out.println("Process Thread for process: " + processName + ", with service name: " + serviceTime);
            // Set the CPU to be running
            CPUIsRunning = true;

            // Run the background process of sleeper
            sleeper.doInBackground();

            // Increase the number of completed processes and set the success
            common.completedProcesses += 1;
            success = true;
        }

        // Exit the thread and return the success status
        System.out.println("Exiting the Process thread");
        return success;
    }


}
