package src;

public class Threading implements Runnable
{
    Thread cpu1;
    Threading()
    {
        cpu1 = new Thread(this, "CPU 1");
        System.out.println("Child Thread: " + cpu1);
        cpu1.start();
    }

    public void run()
    {
        try {
            System.out.println("Child Thread");
            Thread.sleep(50);
        } catch (InterruptedException e) {
            System.out.println("The child thread is interrupted.");
        }
        System.out.println("Exiting the child thread");
    }


}
