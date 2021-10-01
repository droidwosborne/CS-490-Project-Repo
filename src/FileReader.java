/*
 * FileReader.java
 * CS 490 Team 3 Fall 2021
 * Reads process information from given file
 */
package src;

import java.io.*;
import java.lang.*;
import java.util.*;


public class FileReader {
    // Default constructor for the class
    public FileReader()
    {
        System.out.println("I am the file reader");
    }

    // Read an input file specified elsewhere
    public List<String> ReadFile(String inFile)
    {
        // Declare the variables to read the file
        FileInputStream file = null;
        BufferedReader br = null;
        List<String> processes = new LinkedList();



        // Try to open the file
        try
        {
            file = new FileInputStream(inFile);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
            return null;
        }

        // Sets up way to read lines
        String line;
        if (file != null)
            br = new BufferedReader(new InputStreamReader(file));

        // Tries to read the file line by line
        try
        {
            while (((line = br.readLine()) != null))
                // Print the content on the console
            {
                //These two lines just add the Processes to the CPU Queue
                String[] tokens=line.split(", ");
                CpuQueue.addQueue(new Process(Integer.parseInt(tokens[0]),tokens[1],Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3])));
                processes.add(line);
                System.out.println(line);
            }
        }
        catch (IOException e)
        {
                System.out.println("IO Exception!");
                return null;
        }
        return processes;
    }

    /**
     * Get the process name from the input file
     * @param processes is the list of lines from the file
     * @param i is the index of the loop that is being called
     * @return processName
     */
    public Queue<String> getProcessName(List<String> processes, int i)
    {
        firstComma = processes.get(i).indexOf(" ", 1);
        secondComma = processes.get(i).indexOf(", ", firstComma + 1);

       // System.out.println("First: " + firstComma + " Second:" + secondComma);
        Queue<String> processName = new LinkedList<String>();
        Queue<String> temp = new LinkedList<>();
        processName.add(processes.get(i).substring(firstComma, secondComma));
        if(processName.contains(" "))
        {
            // Pop the element and place into temp queue
            temp.add(processName.remove());
            String tempName = temp.remove();
            tempName = tempName.substring(1);
            processName.add(tempName);
        }
       // System.out.println("Name:" + processName);
        return processName;
    }

    /**
     * Get the service time of the process
     * @param processes is the list of lines from the file
     * @param i is the index of the loop that is being called
     * @return service time
     */
    public Queue<String> getServiceTime(List<String> processes, int i)
    {
        finalComma = processes.get(i).lastIndexOf(",");
        System.out.println("Final Comma: " + finalComma);
        Queue<String> time = new LinkedList<>();
        time.add(processes.get(i).substring(secondComma + 2, finalComma));
        if(time.contains(" "))
        {
            // Pop the element and place into temp queue
           // temp.add(processName.remove());
            String tempName = time.peek();
            tempName = tempName.substring(1);
            time.add(tempName);

        }
        System.out.println("Time: " + time);
        return time;
    }

    private int firstComma;
    private int secondComma;
    private int finalComma;

}
