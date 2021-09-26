/*
 * Main.java
 * CS 490 Team 3 Fall 2021
 * Creates Frame and Window instances to produce GUI
 */
package src;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        Frame processWindow = new Frame();
        Window processPanel = new Window();
        processWindow.add(processPanel);
        processWindow.pack();
        processWindow.setVisible(true);
    }
}
