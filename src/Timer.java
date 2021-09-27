package src;

import java.lang.System;

public class Timer {
    long startTime = 0;
    long currentTime = 0;
    //Records the time at the beginning of the program and after each time the system is stopped
    //and started again.
    public void startTimer()
    {
        startTime = System.nanoTime();
        //System.out.println(startTime);
    }
    //Covnverts the value from nanoseconds to milliseconds
    private long convertMilliseconds(long nanoseconds)
    {
        long milliseconds = nanoseconds / 1000000;
        return milliseconds;
    }
    //Returns value of time elapsed
    public long getTimeElapsed ()
    {
        return currentTime;
    }
    //Allows for timer to be paused and keep up with the current time elapsed
    public void pauseTimer ()
    {
        currentTime = currentTime + (System.nanoTime()-startTime);
    }
}
