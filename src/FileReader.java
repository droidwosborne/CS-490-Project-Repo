package src;

import java.io.*;
import java.lang.*;


public class FileReader {
    // Default constructor for the class
    public FileReader()
    {
        System.out.println("I am the file reader");
    }

    // Read an input file specified elsewhere
    public void ReadFile(String inFile)
    {
        // Declare the variables to read the file
        FileInputStream file = null;
        BufferedReader br = null;

        // Try to open the file
        try
        {
            file = new FileInputStream(inFile);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found!");
            return;
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
                System.out.println (line);;
        }
        catch (IOException e)
        {
                System.out.println("IO Exception!");
                return;
        }
    }
}
