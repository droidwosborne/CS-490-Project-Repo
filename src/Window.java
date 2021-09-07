/*
 * Window.java
 * CS 490 Team 3 Fall 2021
 * Creates the Window class for the Main Class to build GUI
 */
package src;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.JScrollPane;

/*
 * Class declaration for Window class extending JPanel for component placement and
 * implementing ActionListener to allow for actions on button click and text entry.
 */
public class Window extends JPanel implements ActionListener{
    //Constructor for Window class consisting of components in Panel
    public Window () {
        //Arbitrary size and color appearance of the window
        Dimension dim = new Dimension(600, 400);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setLayout(null);
        setBackground(Color.darkGray);

        //Label1 stating current system state
        String systemState = "System Paused";
        label1 = new JLabel("<html><font color='FFFFFF'>"+ systemState +"</font></html>");
        size = label1.getPreferredSize();
        label1.setBounds(350, 50, size.width, size.height);
        add(label1);

        //Label2 stating what a single time unit is equal to currently
        label2 = new JLabel("<html><font color='FFFFFF'>1 time unit = </font></html>");
        size = label2.getPreferredSize();
        label2.setBounds(300, 100, size.width, size.height);
        add(label2);

        //Text field accepting input for values for the time unit interval
        timeUnit = new JTextField(5);
        timeUnit.addActionListener(this);
        size = timeUnit.getPreferredSize();
        timeUnit.setBounds(385, 95, size.width, size.height);
        add(timeUnit);

        //Label3 stating time unit of ms
        label3 = new JLabel("<html><font color='FFFFFF'>ms</font></html>");
        size = label3.getPreferredSize();
        label3.setBounds(460, 100, size.width, size.height);
        add(label3);
        
        //Button for starting system processes
        start = new JButton("Start System");
        size = start.getPreferredSize();
        start.setBounds(20, 40, size.width, size.height);
        start.addActionListener(this);
        add(start);

        //Button for pausing system processes
        pause = new JButton("Pause System");
        size = pause.getPreferredSize();
        pause.setBounds(170, 40, size.width, size.height);
        pause.addActionListener(this);
        add(pause);

        /*
        * Table with headers contained in scroll pane to depict
        * process name and service time.
         */
        String[] columnHeaders= {"Process Name","Service Time"};
        String[][] processes = {{"n/a", "0"},
                                {"n/a", "0"},
                                {"n/a", "0"},
                                {"n/a", "0"},
                                {"n/a", "0"}};

        processList = new JTable(processes, columnHeaders);
        processList.setBackground(Color.lightGray);
        JTableHeader header = processList.getTableHeader();
        header.setBackground(Color.gray);
        tablePane = new JScrollPane(processList);
        tablePane.setBounds(20, 100, 250, 100);
        add(tablePane);

        //Label4 depicting current process running information
        String cpu = "CPU 1";
        String processName = "idle";
        String timeRemaining = "n/a";
        label4 = new JLabel("<html>" + cpu + "<br/>exec: " + processName + "<br/>time remaining = " + timeRemaining + "</html>");
        size = label4.getPreferredSize();
        label4.setBounds(320, 150, (size.width+10), (size.height+10));
        label4.setOpaque(true);
        label4.setBackground(Color.yellow);
        add(label4);

        //Label5 depicting system report stats
        label5 = new JLabel( "<html>System Report Stats:<br/>n/a</html>");
        size = label5.getPreferredSize();
        label5.setBounds(150, 250, (size.width+100), (size.height+100));
        label5.setOpaque(true);
        label5.setBackground(Color.white);
        add(label5);
    }

    /*
    * Method for all actions that occur due to user action,
    * such as button click or text entry.
     */
    public void actionPerformed(ActionEvent action)
    {

    }

    //Window component variables
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private Dimension size;
    private JButton start;
    private JButton pause;
    private JTable processList;
    private JScrollPane tablePane;
    private JTextField timeUnit;
}
