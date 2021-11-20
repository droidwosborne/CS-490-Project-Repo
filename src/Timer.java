package src;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Class to maintain a timer
 */
public class Timer {
    public static int timeUnit;

    /**
     *  Method used to wait the service time with the user input time unit
     */
    public void waitServiceTime(long milliseconds) throws InterruptedException
    {
        Thread.sleep(timeUnit * milliseconds);
    }

    /**
     * Lets other classes set the time unit
     * @param newTimeUnit is time unit to be set
     */
    public void setTimeUnit(int newTimeUnit)
    {
        timeUnit = newTimeUnit;
    }
}
