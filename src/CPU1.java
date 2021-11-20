/*
 * CPU.java
 * CS 490 Team 3 Fall 2021
 * CPU Class that makes threads and goes through queue.
 */
package src;

/**
 * CPU class in order to run process threads on the correct CPU
 */
public class CPU1 extends Thread{
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
    public CPU1(int cpuNumber, Window window) {
        this.cpuNumber = cpuNumber;
        this.window = window;

    }

    /**
     * Runs the CPU and the processes on it
     */
    public void run() {
            System.out.println("HRRN Algorithm");
        //Prints out which CPU this is and the size of the CPU Queue. If the CPU queue is empty it can't process anything

       /* while (CpuQueue.queueSize() > 0) {

            // Gets the current process and removes it from the queue
            Process current = CpuQueue.removeQueue(cpuNumber);

            // Update the wait table with the process
            System.out.println(current + " TEST " + cpuNumber); //Prints out current process
            window.UpdateWaitTable(current.getProcessID());

            // Get the service time of the process
            int serviceTime = current.getServiceTime();
            current.setCurrentServiceTime(serviceTime);

            // Runs as long as the service time exists
            for (int i = 0; i < current.getServiceTime(); i++) {

                System.out.println("Currently running process " + current.getProcessID() + " on thread " + cpuNumber + " with " + current.getCurrentServiceTime() + " time left");

                // Sleeps the thread for the required amount of time
                try {
                    Thread.sleep(Timer.timeUnit);
                    serviceTime = serviceTime - 1;
                    current.setCurrentServiceTime(serviceTime);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Update the CPU in the GUI
                window.UpdateCPU(cpuNumber, current.getProcessID(), current.getCurrentServiceTime());
            }
            // Calculates the total time and increases the amount of processes ran
            common.totalTime += current.getServiceTime();
            common.completedProcesses++;

            // Updates the GUI finished table and the throughput
            window.UpdateFinishedTable(current.getProcessID(), common.totalTime, current.getArrivalTime(), current.getServiceTime());
            CalculateThroughput();
        }*/
        Timer time = new Timer();
        Scheduler scheduler = new Scheduler(window);
        scheduler.HRRN();


    }

    /**
     * Calculate the throughput of the program
     */
    public void CalculateThroughput()
    {
        // Get the total time
        double _unitOfTime = common.totalTime;

        // Calculate the throughput
        double throughput = common.completedProcesses / _unitOfTime;
        System.out.println("Throughput: " + throughput);

        // Update the GUI
        window.UpdateThroughput(throughput);
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
