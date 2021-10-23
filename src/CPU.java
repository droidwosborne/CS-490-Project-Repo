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

    public CPU(int cpuNumber, Window window) {
        this.cpuNumber = cpuNumber;
        this.window = window;

    }

    public void run() {
        Timer time=new Timer();
        //Prints out which CPU this is and the size of the CPU Queue, obviously if the CPU queue is empty it can't process anything

        while(CpuQueue.queueSize()>0)
        {
//



            Process current = CpuQueue.removeQueue(cpuNumber);





            System.out.println(current+" TEST "+cpuNumber); //Prints out current process
            window.UpdateWaitTable(current.getProcessID());
            int serviceTime = current.getServiceTime();
            current.setCurrentServiceTime(serviceTime);
            for(int i=0;i<current.getServiceTime();i++)
            {

                System.out.println("Currently running process "+current.getProcessID()+" on thread "+cpuNumber+" with "+current.getCurrentServiceTime()+" time left");
                //window.UpdateCPU(cpuNumber,current.getProcessID(), current.getCurrentServiceTime());

                try {
                    Thread.sleep(Timer.timeUnit);
                    serviceTime =serviceTime  -1;
                    current.setCurrentServiceTime(serviceTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                window.UpdateCPU(cpuNumber,current.getProcessID(), current.getCurrentServiceTime());

            }
            common.totalTime+=current.getServiceTime();
            common.completedProcesses++;
            window.UpdateFinishedTable(current.getProcessID(),common.totalTime, current.getArrivalTime(), current.getServiceTime());
            CalculateThroughput();












            //This next bit will just sleep for however long depending on how long the one unit of time is set to in the GUI, then it will decrement the serviceTime in the process by 1, and then sleep again in a loop
            //So NYI




        }

    }

    public void CalculateThroughput()
    {
        int _unitOfTime = 1;
        double throughput = common.completedProcesses / _unitOfTime;
        System.out.println("Throughput: " + throughput);
        window.UpdateThroughput(throughput);
    }
    public boolean RunProcess(String processName, int serviceTime, boolean CPUIsRunning)
    {
        // new Threading();
        boolean success = false;
        Sleeper sleeper = new Sleeper();

        // Ensure the passed in CPU is not running something else
        if(!CPUIsRunning)
        {
            // try {
            System.out.println("Process Thread for process: " + processName + ", with service name: " + serviceTime);
            CPUIsRunning = true;
            // Thread.sleep(serviceTime * 1000);
            sleeper.doInBackground();

            common.completedProcesses += 1;
            success = true;
            //  }// catch (InterruptedException e) {
            // System.out.println("The Process thread is interrupted");
            //}
        }
        System.out.println("Exiting the Process thread");
        return success;
    }


}
