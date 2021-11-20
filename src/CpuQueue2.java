/*
 * CpuQueue.java
 * CS 490 Team 3 Fall 2021
 * Creates the CPU Queue
 */
package src;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Holds all of the CPUs and their processes
 */
public class CpuQueue2 {
    //Static Queue so that it can be accessed anywhere
    public static Queue<Process> queue = new LinkedList<Process>();
    public static Queue<Process> hrrnQueue = new LinkedList<Process>();

    /**
     * Default constructor
     */
    public CpuQueue2() {
        FileReader reader = new FileReader();
        Window window = new Window();
        queue = reader.ReadFile2(window.file);

    }

    /**
     * Adds a process to the queue
     * @param a is the process to add
     */
    public static void addQueue(Process a){
        queue.add(a);
    }

    /**
     * Removes a process from the Queue
     * @param cpu
     * @return
     */
    public synchronized static Process removeQueue(int cpu){

        return queue.remove();

    }

    /**
     * Gets the size of the queue
     * @return the queue size
     */
    public static int queueSize(){
        return queue.size();
    }

    /**
     * Print the queue out
     */
    public static void printQueue()
    {
        for(Process s : queue)
        System.out.println(s);

    }

    public void duplicateQueue()
    {
        hrrnQueue = queue;
    }


}
