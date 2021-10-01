package src;

public class Timer {
    public int timeUnit;
    //Function used to wait the service time with the user input time unit
    public void waitServiceTime(long milliseconds) throws InterruptedException
    {
        Thread.sleep(timeUnit * milliseconds);
    }
    //Lets other classes set the time unit
    public void setTimeUnit(int newTimeUnit)
    {
        timeUnit = newTimeUnit;
        //System.out.print(timeUnit);
    }
}
