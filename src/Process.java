/*
 * Process.java
 * CS 490 Team 3 Fall 2021
 * Process class that holds process data
 */
package src;

public class Process {

    private int arrivalTime;
    private String processID;
    private int serviceTime;
    private int priority;


    public Process(int arrivalTime,String processID, int serviceTime, int priority ) {
        this.arrivalTime=arrivalTime;
        this.processID=processID;
        this.serviceTime=serviceTime;
        this.priority=priority;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public String getProcessID() {
        return processID;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setProcessID(String processID) {
        this.processID = processID;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
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
