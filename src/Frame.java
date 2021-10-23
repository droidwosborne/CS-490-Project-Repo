/*
 * Frame.java
 * CS 490 Team 3 Fall 2021
 * Creates the Frame class
 */
package src;

import java.awt.FlowLayout;
import javax.swing.JFrame;

/**
 * Extends JFrame to create application window
 */
public class Frame extends JFrame
{

	/**
	 * Constructs the application frame
	 */
	public Frame()
	{
		//Adds title and closing functionality to window
		super("Process Manager");
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER);
		setLayout(flowLayout);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
