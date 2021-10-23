package src;

import javax.swing.*;

/**
 * Acts as a way to make the program sleep
 */
public class Sleeper extends SwingWorker {
    /**
     * Sleep in the background
     * @return
     */
    @Override
    public String doInBackground() {
        try {
            Thread.sleep(Timer.timeUnit * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("pass");
        return "pass";
    }
}
