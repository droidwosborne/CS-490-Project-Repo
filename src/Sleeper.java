package src;

import javax.swing.*;
import java.util.Date;
import java.time.LocalDateTime;

public class Sleeper extends SwingWorker {
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

    public void dateTimeChecker()
    {

    }

}
