/*
 * CPU.java
 * CS 490 Team 3 Fall 2021
 * CPU Class that makes threads and goes through queue.
 */
package src;

import java.util.LinkedList;
import java.util.List;

public class CPU extends Thread{
    private int cpuNumber;
    private Window window;
    private List<Process> processes = new LinkedList();

    public CPU(int cpuNumber, Window window) {
        this.cpuNumber = cpuNumber;
        this.window = window;
    }

    public void run() {
        Timer time=new Timer();
        //Prints out which CPU this is and the size of the CPU Queue, obviously if the CPU queue is empty it can't process anything
        System.out.println("thread: "+cpuNumber);
        System.out.println(CpuQueue.queueSize());
        while(CpuQueue.queueSize()>0)

        {
            Process current=CpuQueue.removeQueue(); //Grabs process from queue
            System.out.println(current); //Prints out current process
            System.out.println(cpuNumber);
            //This next bit will just sleep for however long depending on how long the one unit of time is set to in the GUI, then it will decrement the serviceTime in the process by 1, and then sleep again in a loop
            //So NYI
            try {
                time.waitServiceTime(current.getServiceTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void CalculateThroughput()
    {
        int _unitOfTime = 1;
        double throughput = common.completedProcesses / _unitOfTime;
        System.out.println("Throughput: " + throughput);
        window.UpdateThroughput(throughput);
    }
}
