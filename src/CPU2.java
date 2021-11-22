/*
 * CPU.java
 * CS 490 Team 3 Fall 2021
 * CPU Class that makes threads and goes through queue.
 */
package src;

/**
 * CPU class in order to run process threads on the correct CPU
 */
public class CPU2 extends Thread{
    private int cpuNumber;
    private Window window;


    /**
     * Default constructor of the CPU class
     * @param cpuNumber is the number of the CPU
     * @param window is the window that the CPU is using
     */
    public CPU2(int cpuNumber, Window window) {
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
}
