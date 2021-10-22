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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.*;
import javax.swing.table.JTableHeader;

/*
 * Class declaration for Window class extending JPanel for component placement and
 * implementing ActionListener to allow for actions on button click and text entry.
 */
public class Window extends JPanel implements ActionListener{
    public Timer timer = new Timer();
    //Constructor for Window class consisting of components in Panel
    public Window () {
        //Arbitrary size and color appearance of the window
        Dimension dim = new Dimension(600, 500);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setLayout(null);
        setBackground(Color.darkGray);

        //Label1 stating current system state
        String systemState = "System Paused";
        label1 = new JLabel("<html><font color='FFFFFF'>"+ systemState +"</font></html>");
        size = label1.getPreferredSize();
        label1.setBounds(350, 110, size.width, size.height);
        add(label1);

        //Label2 stating what a single time unit is equal to currently
        label2 = new JLabel("<html><font color='FFFFFF'>1 time unit = </font></html>");
        size = label2.getPreferredSize();
        label2.setBounds(300, 160, size.width, size.height);
        add(label2);

        //Text field accepting input for values for the time unit interval
        timeUnit = new JTextField(5);
        timeUnit.addActionListener(this);
        size = timeUnit.getPreferredSize();
        timeUnit.setText("100");
        timeUnit.setBounds(385, 155, size.width, size.height);
        add(timeUnit);

        //Label3 stating time unit of ms
        label3 = new JLabel("<html><font color='FFFFFF'>ms</font></html>");
        size = label3.getPreferredSize();
        label3.setBounds(460, 160, size.width, size.height);
        add(label3);
        
        //Button for starting system processes
        start = new JButton("Start System");
        size = start.getPreferredSize();
        start.setBounds(20, 100, size.width, size.height);
        start.addActionListener(this);
        add(start);

        //Button for pausing system processes
        pause = new JButton("Pause System");
        size = pause.getPreferredSize();
        pause.setBounds(170, 100, size.width, size.height);
        pause.addActionListener(this);
        add(pause);

        /*
        * Table with headers contained in scroll pane to depict
        * process name and service time.
         */
        String[] waitingProcessHeaders= {"Process Name","Service Time"};
        String[][] waitingProcessesArray = {{"", ""},
                                     {"", ""},
                                     {"", ""},
                                     {"", ""},
                                     {"", ""}};

        waitingProcessQueueTable = new JTable(waitingProcessesArray, waitingProcessHeaders);
        waitingProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader waitingProcessHeader = waitingProcessQueueTable.getTableHeader();
        waitingProcessHeader.setBackground(Color.gray);
        tablePane = new JScrollPane(waitingProcessQueueTable);
        tablePane.setBounds(20, 190, 250, 100);
        add(tablePane);

        //Label4 depicting current process running information for cpu 1
        String timeRemaining = "n/a";
        label4 = new JLabel("<html>cpu 1<br/>exec: idle<br/>time remaining = n/a</html>");
        size = label4.getPreferredSize();
        label4.setBounds(320, 200, (size.width+10), (size.height+10));
        label4.setOpaque(true);
        label4.setBackground(Color.yellow);
        add(label4);

        //Label5 depicting current process running information for cpu 2
        label5 = new JLabel("<html>cpu 2<br/>exec: idle<br/>time remaining = n/a</html>");
        size = label5.getPreferredSize();
        label5.setBounds(320, 270, (size.width+10), (size.height+10));
        label5.setOpaque(true);
        label5.setBackground(Color.yellow);
        add(label5);

        //Label5 depicting system report stats
        /*label5 = new JLabel( "<html>System Report Stats:<br/>n/a</html>");
        size = label5.getPreferredSize();
        label5.setBounds(150, 310, (size.width+100), (size.height+100));
        label5.setOpaque(true);
        label5.setBackground(Color.white);
        add(label5);*/
        String[] completedProcessHeaders= {"Process Name","Arrival Time","Service Time","Finish Time","TAT","nTAT"};
        String[][] completedProcessesArray = {{"","","","","",""},
                {"","","","","",""},
                {"","","","","",""}};

        completedProcessQueueTable = new JTable(completedProcessesArray, completedProcessHeaders);
        completedProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader completedProcessHeader = completedProcessQueueTable.getTableHeader();
        completedProcessHeader.setBackground(Color.gray);
        tablePane2 = new JScrollPane(completedProcessQueueTable);
        tablePane2.setBounds(20, 350, 540, 70);
        add(tablePane2);

        //Label6 stating what a single time unit is equal to currently
        label6 = new JLabel("<html><font color='FFFFFF'>Process File Path : </font></html>");
        size = label6.getPreferredSize();
        label6.setBounds(50, 40, size.width, size.height);
        add(label6);

        //Label7 stating invalid file path warning
        label7 = new JLabel("<html><font color='FF0000'>Invalid File Path</font></html>");
        size = label7.getPreferredSize();
        label7.setBounds(180, 70, size.width, size.height);
        add(label7);
        label7.setVisible(false);

        //Text field accepting input for values for the file path
        fileName = new JTextField(30);
        fileName.addActionListener(this);
        fileName.setText("InputFiles/test.txt");
        file = fileName.getText();
        size = fileName.getPreferredSize();
        fileName.setBounds(170, 40, size.width, size.height);
        add(fileName);

        throughputLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>Current Throughput: 0 process/unit of time</font></font></strong></html>");
        size = throughputLabel.getPreferredSize();
        throughputLabel.setBounds(150,450,size.width,size.height);
        add(throughputLabel);

        label9 = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>Waiting Process Queue</font></font></strong></html>");
        size = label9.getPreferredSize();
        label9.setBounds(60,150,size.width,size.height);
        add(label9);
    }

    /*
    * Method for all actions that occur due to user action,
    * such as button click or text entry.
     */
    public void actionPerformed(ActionEvent action)
    {
        // Start button pressed
        if(action.getSource().equals(start))
        {
            label1.setText("<html><font color='FFFFFF'>"+ "System Running" +"</font></html>");
            size = label1.getPreferredSize();
            label1.setBounds(350, 110, size.width, size.height);
            FileReader reader = new FileReader();
            Processes processInstance = new Processes();
           // CPU cpu1 =new CPU(1);
           // cpu1.start();
            // Read the file
            List<String> processesRead = reader.ReadFile(file);

            if(processesRead == null)
            {
                label7.setVisible(true);
            }


            Queue<String> tempServ = new LinkedList<>();

            for (int i = 0; i < processesRead.size(); i++)
            {
                DisplayProcesses(reader, processesRead, i, processInstance);
            }
        }

        if (action.getSource().equals(pause))
        {
            label1.setText("<html><font color='FFFFFF'>"+ "System Paused" +"</font></html>");
            size = label1.getPreferredSize();
            label1.setBounds(350, 110, size.width, size.height);
        }
        if (action.getSource().equals(timeUnit))
        {
            String input = timeUnit.getText();
            int inputInt = Integer.parseInt(input);
            timer.setTimeUnit(inputInt);
            System.out.println(inputInt);
        }
        if (action.getSource().equals(fileName))
        {
            label7.setVisible(false);
            file = fileName.getText();
        }
        //This is just showing in the console that the CPU Queue is filled.
        //CpuQueue.printQueue();
    }

    public void DisplayProcesses(FileReader reader, List<String> processes, int i, Processes process)
    {
        Queue<String> temp = new LinkedList<>();
        // Makes sure there are rows available
        if(i < waitingProcessQueueTable.getRowCount())
        {
            Queue<String> processName = reader.getProcessName(processes, i);
            Queue<String> serviceTime = reader.getServiceTime(processes, i);
            Queue<String> arrivalTime = reader.getArrivalTime(processes, i);
            temp.add(processName.peek());

            // Set process name in GUI
            waitingProcessQueueTable.setValueAt(processName.peek(), i, 0);
            completedProcessQueueTable.setValueAt(processName.peek(),i,0);
            label4.setText("<html>" + "cpu 1" + "<br/>exec: " + waitingProcessQueueTable.getValueAt(0,0) + "<br/>time remaining = " + waitingProcessQueueTable.getValueAt(0,1) + "</html>");

            // label1.setBackground(Color.WHITE);

            // Set service time in GUI
            waitingProcessQueueTable.setValueAt(serviceTime.peek(), i, 1);
            completedProcessQueueTable.setValueAt(serviceTime.peek(),i,2);

            // Set arrival time in GUI
            completedProcessQueueTable.setValueAt(arrivalTime.peek(),i, 1);

            if(!common.CPU1RUNNING)
            {
                if(process.RunProcess(processName.peek(), Integer.parseInt(serviceTime.peek()), common.CPU1RUNNING));
                common.CPU1RUNNING = false;
                CPU cpu1 = new CPU(1, this);
                cpu1.CalculateThroughput();
            }
        }
    }

    /**
     * Updates the throughput time in the GUI
     * @param throughput
     */
    public void UpdateThroughput(double throughput)
    {
        throughputLabel.setText("<html><strong><font color='FFFFFF'><font size = 60px>Current Throughput: " + (throughput / getTimeUnit()) + " process/unit of time</font></font></strong></html>");
        size = throughputLabel.getPreferredSize();
        throughputLabel.setBounds(150,450,size.width,size.height);
    }

    /**
     * Gets the time unit text field as an int
     * @return
     */
    public int getTimeUnit()
    {
        return Integer.parseInt(timeUnit.getText());
    }

    //Window component variables
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private JLabel label5;
    private JLabel label6;
    private JLabel label7;
    private JLabel throughputLabel;
    private JLabel label9;
    private Dimension size;
    private JButton start;
    private JButton pause;
    private JTable waitingProcessQueueTable;
    private JTable completedProcessQueueTable;
    private JScrollPane tablePane;
    private JScrollPane tablePane2;
    private JTextField timeUnit;
    private JTextField fileName;
    public static String file;
}
