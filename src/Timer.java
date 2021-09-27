package src;

public class Timer {
    public void waitServiceTime(long milliseconds) throws InterruptedException
    {
        Thread.sleep(milliseconds);
    }
}
