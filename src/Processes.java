package src;

public class Processes {
    public void Processes()
    {
        System.out.println("Running Processes");
    }

    /**
     * Run a process from the queue
     * @param processName is the name of the process to be ran
     * @param serviceTime is the amount of time it will take the process to run
     * @return true or false depending on if the process finished successfully
     */
    public boolean RunProcess(String processName, int serviceTime, boolean CPUIsRunnning)
    {
        new Threading();
        boolean success = false;

        // Ensure the passed in CPU is not running something else
        if(!CPUIsRunnning)
        {
            try {
                System.out.println("Process Thread for process: " + processName + ", with service name: " + serviceTime);
                CPUIsRunnning = true;
                Thread.sleep(serviceTime * 1000);
                common.completedProcesses += 1;
                success = true;
            } catch (InterruptedException e) {
                System.out.println("The Process thread is interrupted");
            }
        }
        System.out.println("Exiting the Process thread");
        return success;
    }




}
