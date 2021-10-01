/*
 * CpuQueue.java
 * CS 490 Team 3 Fall 2021
 * Creates the CPU Queue
 */
package src;

import java.util.*;
public class CpuQueue {
    //Static Queue so that it can be accessed anywhere
    private static Queue<Process> queue = new LinkedList<Process>();

    public CpuQueue() {
    }

    public static void addQueue(Process a){
        queue.add(a);
    }
    public static Process removeQueue(){
        return queue.remove();
    }
    public static int queueSize(){
        return queue.size();
    }

    public static void printQueue()
    {
        for(Process s : queue)
        System.out.println(s);

    }

}
