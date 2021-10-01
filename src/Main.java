/*
 * Main.java
 * CS 490 Team 3 Fall 2021
 * Creates Frame and Window instances to produce GUI
 */
package src;

import java.io.*;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        Frame processWindow = new Frame();
        Window processPanel = new Window();
        processWindow.add(processPanel);
        processWindow.pack();
        processWindow.setVisible(true);

        FileReader reader = new FileReader();
        Processes process = new Processes();

        // Read the file. The file will need to be a variable later
        List<String> processes = reader.ReadFile("InputFiles/test.txt");

        for (int i = 0; i < processes.size(); i++)
        {
            processPanel.DisplayProcesses(reader, processes, i, process);
        }
        /*Right now the CPU Queue is empty because it doesn't get filled until the start button on the GUI is pushed. If you want to test it then make a CPU instance at the end of the
            actionPerformed function since that is when the CPU Queue is filled. Depending on how we implement it, these next two lines might be here or somewhere else or where ever, as long as the CPU Queue is filled
            which happens when the FileReader class does its thing it will work.
         */
         CPU cpu1 =new CPU(1);
         cpu1.start();
    }
}
