/*
 * Process.java
 * CS 490 Team 3 Fall 2021
 * Process class that holds process data
 */
package src;

/**
 * Holds all the information about a process
 */
public class Process {

    // Create all variables
    private int arrivalTime;
    private String processID;
    private int serviceTime;
    private int priority;
    private int currentServiceTime;
    private double R;

    /**
     * Default constructor to set up variables
     * @param arrivalTime
     * @param processID
     * @param serviceTime
     * @param priority
     */
    public Process(int arrivalTime,String processID, int serviceTime, int priority ) {
        this.arrivalTime=arrivalTime;
        this.processID=processID;
        this.serviceTime=serviceTime;
        this.priority=priority;
    }

    /**
     * Gets the arrival time
     * @return
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets the process ID
     * @return
     */
    public String getProcessID() {
        return processID;
    }

    /**
     * Gets the service time
     * @return
     */
    public int getServiceTime() {
        return serviceTime;
    }

    /**
     * Gets the current service time
     * @return
     */
    public int getCurrentServiceTime()
    {
        return currentServiceTime;
    }

    /**
     * Get the priority
     * @return
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Sets the arrival time
     * @param arrivalTime
     */
    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Sets the process ID
     * @param processID
     */
    public void setProcessID(String processID) {
        this.processID = processID;
    }

    /**
     * Sets the service time
     * @param serviceTime
     */
    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * Sets the priority
     * @param priority
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }

    /**
     * Sets the current service time
     * @param current
     */
    public void setCurrentServiceTime (int current)
    {
        this.currentServiceTime = current;
    }

    /**
     * Turns everything into one string
     * @return
     */

    public void setR (double responseRatio)
    {
        this.R = responseRatio;
    }
    public double getR ()
    {
        return this.R;
    }
    @Override
    public String toString() {
        return "Process{" +
                "arrivalTime=" + arrivalTime +
                ", processID='" + processID + '\'' +
                ", serviceTime=" + serviceTime +
                ", priority=" + priority +
                '}';
    }
}
