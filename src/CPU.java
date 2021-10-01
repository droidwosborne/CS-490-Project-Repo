/*
 * CPU.java
 * CS 490 Team 3 Fall 2021
 * CPU Class that makes threads and goes through queue.
 */
package src;

public class CPU extends Thread{
    private int cpuNumber;

    public CPU(int cpuNumber) {
        this.cpuNumber = cpuNumber;
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
            //This next bit will just sleep for however long depending on how long the one unit of time is set to in the GUI, then it will decrement the serviceTime in the process by 1, and then sleep again in a loop
            //So NYI
            try {
                Thread.sleep(Timer.timeUnit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
