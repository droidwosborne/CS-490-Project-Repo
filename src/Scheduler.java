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
       // System.out.println(_processes);
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
        int previousProcessFinishTime = 0;
        Queue<Process> remainingProcesses = new LinkedList<>();
        Queue<String> _processes = new LinkedList<String>();

        Timer time = new Timer();
        int timeRunning = 0;
        double hrr = 0;
        Queue<Process> collected = new LinkedList<>();
        Queue<Process> finished = new LinkedList<>();
        boolean isCurrent = false;
        Process current = new Process(0,"empty", 0,0);
        int currTime = 0;
        int remainingServiceTime = 0;

        while (CpuQueue2.queueSize() > 0) {

            // Gets the current process and removes it from the queue
            try {
                Process t_curr = CpuQueue2.peekQueue();
                if (t_curr.getArrivalTime() <= currTime && current.getCurrentServiceTime() <= 0) {
                    current = CpuQueue2.removeQueue(1);
                    current.setCurrentServiceTime(current.getServiceTime());
                    remainingServiceTime = current.getCurrentServiceTime();
                    if (!collected.contains(current))
                    {
                        collected.add(current);
                    }
                    System.out.println("first Curr service time: "  + current.getCurrentServiceTime());
                }
                else if (t_curr.getArrivalTime() <= currTime)
                {
                    if (!collected.contains(current))
                    {
                        Process t_current = CpuQueue2.removeQueue(1);
                        collected.add(t_current);
                    }
                }

                Thread.sleep(Timer.timeUnit);
                currTime += 1;
                remainingServiceTime -= 1;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            current.setCurrentServiceTime((remainingServiceTime));
            System.out.println("Curr service time: "  + current.getCurrentServiceTime());
            System.out.println("Remaining Service Time :" + remainingServiceTime);
            if (collected.contains(current))
            {
                Process t_curr = current;
                for (Process p : collected)
                {
                    System.out.println("first P is: " + p);
                    if (p.getR() > hrr && collected.contains(p) && !finished.contains(p))
                    {
                        hrr = p.getR();
                        System.out.println("Collecting HRR: " + hrr);
                        t_curr = p;
                        System.out.println("P is: " + p);
                    }
                }
                current = t_curr;
                hrr = 0;
                isCurrent = true;
            }

            System.out.println(current.getArrivalTime());
            // Update the wait table with the process
            window.UpdateWaitTable(1, current.getProcessID());
            waitTime = previousProcessFinishTime - current.getArrivalTime();
            System.out.println("wait time " + waitTime);
            // Get the service time of the process
            int serviceTime = current.getServiceTime();
           // current.setCurrentServiceTime(serviceTime);
            current.setR((waitTime + serviceTime) / serviceTime);
            if (!isCurrent && remainingServiceTime > 0)
            {
                CpuQueue2.addQueue(current);



                System.out.println("Is current process :" + current);
                System.out.println("remaining ST :" + remainingServiceTime);
            }
            else if (current.getCurrentServiceTime() <= 0)
            {
                finished.add(current);
            }

            window.UpdateCPU(1, current.getProcessID(), current.getCurrentServiceTime());
            //System.out.println("Previous " + previousProcessFinishTime);
            previousProcessFinishTime = previousProcessFinishTime + current.getServiceTime();
           // System.out.println("Previous " + previousProcessFinishTime);

            isCurrent = false;

        }
        System.out.println("Finished HRRN!");


    }
}
