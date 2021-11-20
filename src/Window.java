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
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/*
 * Class declaration for Window class extending JPanel for component placement and
 * implementing ActionListener to allow for actions on button click and text entry.
 */
public class Window extends JPanel implements ActionListener{
    // Declare default fields
    public Timer timer = new Timer();
    private CPU1 cpu1=new CPU1(1,this);
    private CPU2 cpu2=new CPU2(2,this);
    private boolean firstStart=true;
    double cpu1nTATSum = 0.0;
    double cpu1nTAT = 0.0;
    double cpu2nTATSum = 0.0;
    double cpu2nTAT = 0.0;

    //Constructor for Window class consisting of components in Panel
    public Window () {
        //Arbitrary size and color appearance of the window
        Dimension dim = new Dimension(950, 500);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setLayout(null);
        setBackground(Color.darkGray);

        //Label stating current system state
        String systemState = "System Paused";
        systemStateLabel = new JLabel("<html><font color='FFFFFF'>"+ systemState +"</font></html>");
        size = systemStateLabel.getPreferredSize();
        systemStateLabel.setBounds(450, 100, size.width, size.height);
        add(systemStateLabel);

        //Label stating what a single time unit is equal to currently
        timeUnitLabel = new JLabel("<html><font color='FFFFFF'>1 time unit = </font></html>");
        size = timeUnitLabel.getPreferredSize();
        timeUnitLabel.setBounds(600, 100, size.width, size.height);
        add(timeUnitLabel);

        //Label stating to press enter when time unit is entered
        timeUnitInstructionLabel = new JLabel("<html><font color='FFFFFF'>Press 'Enter' once unit is in</font></html>");
        size = timeUnitInstructionLabel.getPreferredSize();
        timeUnitInstructionLabel.setBounds(620, 120, size.width, size.height);
        add(timeUnitInstructionLabel);

        //Label stating to press enter when slice length is entered
        sliceLengthInstructionLabel = new JLabel("<html><font color='FFFFFF'>Press 'Enter' once slice length is in</font></html>");
        size = sliceLengthInstructionLabel.getPreferredSize();
        sliceLengthInstructionLabel.setBounds(700, 300, size.width, size.height);
        add(sliceLengthInstructionLabel);

        //Label stating time unit of ms
        unitTypeLabel = new JLabel("<html><font color='FFFFFF'>ms</font></html>");
        size = unitTypeLabel.getPreferredSize();
        unitTypeLabel.setBounds(760, 100, size.width, size.height);
        add(unitTypeLabel);

        //Label for cpu1 nTAT value display
        cpu1nTATLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>Current average nTAT: 0</font></font></strong></html>");
        size = cpu1nTATLabel.getPreferredSize();
        cpu1nTATLabel.setBounds(100,450,size.width,size.height);
        add(cpu1nTATLabel);

        //Label for cpu2 nTAT value display
        cpu2nTATLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>Current average nTAT: 0</font></font></strong></html>");
        size = cpu2nTATLabel.getPreferredSize();
        cpu2nTATLabel.setBounds(600,450,size.width,size.height);
        add(cpu2nTATLabel);

        //Label for cpu1 waiting process queue header
        cpu1WaitingProcessLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>CPU 1 Waiting Process Queue</font></font></strong></html>");
        size = cpu1WaitingProcessLabel.getPreferredSize();
        cpu1WaitingProcessLabel.setBounds(40,150,size.width,size.height);
        add(cpu1WaitingProcessLabel);

        //Label for cpu2 waiting process queue header
        cpu2WaitingProcessLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>CPU 2 Waiting Process Queue</font></font></strong></html>");
        size = cpu2WaitingProcessLabel.getPreferredSize();
        cpu2WaitingProcessLabel.setBounds(520,150,size.width,size.height);
        add(cpu2WaitingProcessLabel);

        //Label depicting current process running information for cpu 1
        cpu1Label = new JLabel("<html>cpu 1 (HRRN)<br/>exec: idle<br/>time remaining = n/a</html>");
        size = cpu1Label.getPreferredSize();
        cpu1Label.setBounds(300, 200, (size.width+10), (size.height+10));
        cpu1Label.setOpaque(true);
        cpu1Label.setBackground(Color.yellow);
        add(cpu1Label);

        //Label depicting current process running information for cpu 2
        cpu2Label = new JLabel("<html>cpu 2 (RR)<br/>exec: idle<br/>time remaining = n/a</html>");
        size = cpu2Label.getPreferredSize();
        cpu2Label.setBounds(760, 200, (size.width+10), (size.height+10));
        cpu2Label.setOpaque(true);
        cpu2Label.setBackground(Color.yellow);
        add(cpu2Label);

        //Label stating round robin slice length
        roundRobinLabel = new JLabel("<html><font color='FFFFFF'>Round Robin Time<br/>Slice Length</font></html>");
        size = roundRobinLabel.getPreferredSize();
        roundRobinLabel.setBounds(760, 270, size.width, size.height);
        add(roundRobinLabel);

        //Label for process file path
        filePathLabel = new JLabel("<html><font color='FFFFFF'>Process File Path : </font></html>");
        size = filePathLabel.getPreferredSize();
        filePathLabel.setBounds(50, 40, size.width, size.height);
        add(filePathLabel);

        //Label stating to press enter when file path is entered
        filePathInstructionLabel = new JLabel("<html><font color='FFFFFF'>Press 'Enter' once file path is in</font></html>");
        size = filePathInstructionLabel.getPreferredSize();
        filePathInstructionLabel.setBounds(570, 40, size.width, size.height);
        add(filePathInstructionLabel);

        //Label stating invalid file path warning
        filePathWarningLabel = new JLabel("<html><font color='FF0000'>Invalid File Path</font></html>");
        size = filePathWarningLabel.getPreferredSize();
        filePathWarningLabel.setBounds(180, 70, size.width, size.height);
        add(filePathWarningLabel);
        filePathWarningLabel.setVisible(false);

        //Text field accepting input for values for the time unit interval
        timeUnitTextField = new JTextField(5);
        timeUnitTextField.addActionListener(this);
        size = timeUnitTextField.getPreferredSize();
        timeUnitTextField.setText("100");
        timer.setTimeUnit(100);
        timeUnitTextField.setBounds(685, 95, size.width, size.height);
        add(timeUnitTextField);

        //Text field accepting input for values for the file path
        fileNameTextField = new JTextField(30);
        fileNameTextField.addActionListener(this);
        fileNameTextField.setText("InputFiles/test.txt");
        file = fileNameTextField.getText();
        size = fileNameTextField.getPreferredSize();
        fileNameTextField.setBounds(170, 40, size.width, size.height);
        add(fileNameTextField);

        //Text field accepting input for round robin time slice length
        sliceLengthTextField = new JTextField(2);
        sliceLengthTextField.addActionListener(this);
        sliceLengthTextField.setText("2");
        size = sliceLengthTextField.getPreferredSize();
        sliceLengthTextField.setBounds(880, 270, size.width, size.height);
        add(sliceLengthTextField);

        //Button for starting system processes
        startButton = new JButton("Start System");
        size = startButton.getPreferredSize();
        startButton.setBounds(200, 95, size.width, size.height);
        startButton.addActionListener(this);
        add(startButton);

        //Button for pausing system processes
        pauseButton = new JButton("Pause System");
        size = pauseButton.getPreferredSize();
        pauseButton.setBounds(320, 95, size.width, size.height);
        pauseButton.addActionListener(this);
        add(pauseButton);

        /*
         * cpu1 Table model for waiting process table to dynamically add rows for each process
         */
        cpu1WaitingTableModel = new DefaultTableModel();
        cpu1WaitingTableModel.addColumn("Process Name");
        cpu1WaitingTableModel.addColumn("Arrival Time");
        cpu1WaitingTableModel.addRow(new Object[]{"",""});

        /*
         * cpu2 Table model for waiting process table to dynamically add rows for each process
         */
        cpu2WaitingTableModel = new DefaultTableModel();
        cpu2WaitingTableModel.addColumn("Process Name");
        cpu2WaitingTableModel.addColumn("Arrival Time");
        cpu2WaitingTableModel.addRow(new Object[]{"",""});

        /*
         * cpu1 Table model for completed process table to dynamically add rows for each process
         */
        cpu1CompletedTableModel = new DefaultTableModel();
        cpu1CompletedTableModel.addColumn("Process Name");
        cpu1CompletedTableModel.addColumn("Arrival Time");
        cpu1CompletedTableModel.addColumn("Service Time");
        cpu1CompletedTableModel.addColumn("Finish Time");
        cpu1CompletedTableModel.addColumn("TAT");
        cpu1CompletedTableModel.addColumn("nTAT");

        /*
         * cpu2 Table model for completed process table to dynamically add rows for each process
         */
        cpu2CompletedTableModel = new DefaultTableModel();
        cpu2CompletedTableModel.addColumn("Process Name");
        cpu2CompletedTableModel.addColumn("Arrival Time");
        cpu2CompletedTableModel.addColumn("Service Time");
        cpu2CompletedTableModel.addColumn("Finish Time");
        cpu2CompletedTableModel.addColumn("TAT");
        cpu2CompletedTableModel.addColumn("nTAT");

        /*
         * cpu1 Table with headers contained in scroll pane to depict
         * process name and service time for waiting processes.
         */
        cpu1WaitingProcessQueueTable = new JTable(cpu1WaitingTableModel);
        cpu1WaitingProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader cpu1WaitingProcessHeader = cpu1WaitingProcessQueueTable.getTableHeader();
        cpu1WaitingProcessHeader.setBackground(Color.gray);
        cpu1WaitingProcessScrollingPane = new JScrollPane(cpu1WaitingProcessQueueTable);
        cpu1WaitingProcessScrollingPane.setBounds(20, 190, 250, 100);
        add(cpu1WaitingProcessScrollingPane);

        /*
         * cpu1 Table with headers contained in scroll pane to depict
         * completed process data.
         */
        cpu1CompletedProcessQueueTable = new JTable(cpu1CompletedTableModel);
        cpu1CompletedProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader cpu1CompletedProcessHeader = cpu1CompletedProcessQueueTable.getTableHeader();
        cpu1CompletedProcessHeader.setBackground(Color.gray);
        cpu1CompletedProcessQueueTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        cpu1CompletedProcessQueueTable.getColumnModel().getColumn(4).setPreferredWidth(30);
        cpu1CompletedProcessQueueTable.getColumnModel().getColumn(5).setPreferredWidth(35);
        cpu1CompletedProcessQueueTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        cpu1CompletedProcessScrollingPane = new JScrollPane(cpu1CompletedProcessQueueTable);
        cpu1CompletedProcessScrollingPane.setBounds(20, 350, 400, 70);
        add(cpu1CompletedProcessScrollingPane);

        /*
         * cpu2 Table with headers contained in scroll pane to depict
         * process name and service time for waiting processes.
         */
        cpu2WaitingProcessQueueTable = new JTable(cpu2WaitingTableModel);
        cpu2WaitingProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader waitingProcessHeader = cpu2WaitingProcessQueueTable.getTableHeader();
        waitingProcessHeader.setBackground(Color.gray);
        cpu2WaitingProcessScrollingPane = new JScrollPane(cpu2WaitingProcessQueueTable);
        cpu2WaitingProcessScrollingPane.setBounds(500, 190, 250, 100);
        add(cpu2WaitingProcessScrollingPane);

        /*
         * cpu2 Table with headers contained in scroll pane to depict
         * completed process data.
         */
        cpu2CompletedProcessQueueTable = new JTable(cpu2CompletedTableModel);
        cpu2CompletedProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader completedProcessHeader = cpu2CompletedProcessQueueTable.getTableHeader();
        completedProcessHeader.setBackground(Color.gray);
        cpu2CompletedProcessQueueTable.getColumnModel().getColumn(0).setPreferredWidth(90);
        cpu2CompletedProcessQueueTable.getColumnModel().getColumn(4).setPreferredWidth(30);
        cpu2CompletedProcessQueueTable.getColumnModel().getColumn(5).setPreferredWidth(35);
        cpu2CompletedProcessQueueTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        cpu2CompletedProcessScrollingPane = new JScrollPane(cpu2CompletedProcessQueueTable);
        cpu2CompletedProcessScrollingPane.setBounds(500, 350, 400, 70);
        add(cpu2CompletedProcessScrollingPane);
    }

    /*
    * Method for all actions that occur due to user action,
    * such as button click or text entry.
     */
    public void actionPerformed(ActionEvent action)
    {
        // Start button pressed
        if(action.getSource().equals(startButton))
        {
            if(firstStart) {
                firstStart=false;
                systemStateLabel.setText("<html><font color='FFFFFF'>" + "System Running" + "</font></html>");
                size = systemStateLabel.getPreferredSize();
                systemStateLabel.setBounds(450, 100, size.width, size.height);
                FileReader reader = new FileReader();
                Processes processInstance = new Processes();

                // Read the file
                List<String> processesRead = reader.ReadFile(file);

                // Ensures there are processes
                if (processesRead == null) {
                    filePathWarningLabel.setVisible(true);
                }

                Queue<String> tempServ = new LinkedList<>();

                // Run the DisplayProcesses method for each process in the queue
                for (int i = 0; i < processesRead.size(); i++) {
                    DisplayProcesses(reader, processesRead, i, processInstance);
                }
                // Start both CPUs
                //CPU.setCpuNumber(1);
                whichCPU = 1;
                cpu1.start();

                //CPU.setCpuNumber(2);
                whichCPU = 2;
                cpu2.start();
            }
            // Resume both CPUs if the system is paused
            else {
                systemStateLabel.setText("<html><font color='FFFFFF'>" + "System Running" + "</font></html>");
                cpu1.resume();
                cpu2.resume();
            }
        }

        // Paused button pressed
        if (action.getSource().equals(pauseButton))
        {
            // Pauses both CPUs
            cpu1.suspend();
            cpu2.suspend();
            /*
             * Runs code to update GUI on event thread (not certain correct)
             */
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    systemStateLabel.setText("<html><font color='FFFFFF'>"+ "System Paused" +"</font></html>");
                    size = systemStateLabel.getPreferredSize();
                    systemStateLabel.setBounds(450, 110, size.width, size.height);
//
                }
            });
        }
        // If the user presses the enter key after editing the time unit text field
        if (action.getSource().equals(timeUnitTextField))
        {
            // Get the text from the field and apply it to the timer
            String input = timeUnitTextField.getText();
            int inputInt = Integer.parseInt(input);
            timer.setTimeUnit(inputInt);
            System.out.println(inputInt);
        }

        // If the user presses the enter key after editing the slice length text field
        if (action.getSource().equals(sliceLengthTextField))
        {
            // Get the text from the field and apply it for round robin slice length
            String input = sliceLengthTextField.getText();
            int inputInt = Integer.parseInt(input);
            System.out.println(inputInt);
        }

        // If the user presses the enter key after editing the file name text field
        if (action.getSource().equals(fileNameTextField))
        {
            filePathWarningLabel.setVisible(false);
            file = fileNameTextField.getText();
        }
    }

    /**
     * Display the processes in the different GUI queues
     * @param reader the reader to read the file
     * @param processes the list of process
     * @param i is the index of the current process
     * @param process  is the process instance
     */
    public void DisplayProcesses(FileReader reader, List<String> processes, int i, Processes process)
    {
        Queue<String> temp = new LinkedList<>();

        // Makes sure there are rows available
        if(i < cpu1WaitingProcessQueueTable.getRowCount())
        {
            // Gets each value from the reader
            Queue<String> processName = reader.getProcessName(processes, i);
            Queue<String> serviceTime = reader.getServiceTime(processes, i);
            Queue<String> arrivalTime = reader.getArrivalTime(processes, i);
            temp.add(processName.peek());

            // Set process name in GUI
            cpu1Label.setText("<html>" + "cpu 1 (HRRN)" + "<br/>exec: " + cpu1WaitingProcessQueueTable.getValueAt(0,0) + "<br/>time remaining = " + cpu1WaitingProcessQueueTable.getValueAt(0,1) + "</html>");

            //Set table rows for current process
            cpu1WaitingTableModel.insertRow(i,new Object[]{processName.peek(),arrivalTime.peek()});
            cpu1CompletedTableModel.insertRow(i,new Object[]{processName.peek(),arrivalTime.peek(),serviceTime.peek()});
        }
        if(i < cpu2WaitingProcessQueueTable.getRowCount())
        {
            // Gets each value from the reader
            Queue<String> processName = reader.getProcessName(processes, i);
            Queue<String> serviceTime = reader.getServiceTime(processes, i);
            Queue<String> arrivalTime = reader.getArrivalTime(processes, i);
            temp.add(processName.peek());

            // Set process name in GUI
            cpu2Label.setText("<html>" + "cpu 2(RR)" + "<br/>exec: " + cpu2WaitingProcessQueueTable.getValueAt(0,0) + "<br/>time remaining = " + cpu1WaitingProcessQueueTable.getValueAt(0,1) + "</html>");

            //Set table rows for current process
            cpu2WaitingTableModel.insertRow(i,new Object[]{processName.peek(),arrivalTime.peek()});
            cpu2CompletedTableModel.insertRow(i,new Object[]{processName.peek(),arrivalTime.peek(),serviceTime.peek()});
        }
    }

    /**
     * Updates the throughput time in the GUI
     * @param throughput
     */
    public void UpdateThroughput(double throughput)
    {
        //cpu1nTATLabel.setText("<html><strong><font color='FFFFFF'><font size = 60px>Current average nTAT: " + new DecimalFormat("#.###").format(throughput) + " process/unit of time</font></font></strong></html>");
        //size = cpu1nTATLabel.getPreferredSize();
        //cpu1nTATLabel.setBounds(100,450,size.width,size.height);
    }

    /**
     * Update the CPUs in the GUI
     * @param cpu is the cpu to update
     * @param exec is the running process
     * @param time is the time remaining on the CPU
     */
    public void UpdateCPU(int cpu,String exec, int time)
    {
        switch(cpu){
            case 1:
                cpu1Label.setText("<html>" + "cpu 1 (HRRN)" + "<br/>exec: " + exec + "<br/>time remaining = "
                        + time + "</html>");
                break;
            case 2:
                cpu2Label.setText("<html>" + "cpu 2 (RR)" + "<br/>exec: " + exec + "<br/>time remaining = "
                        + time + "</html>");
                break;
            case 3:
                break;
        }
    }

    /**
     * Update the wait table with the processes
     * @param exec is the executing process
     */
    public void UpdateWaitTable(int cpu, String exec) {
        if (cpu == 1) {
            // Goes through all the processes in the queue and add them to the  queue
            for (int i = 0; i < cpu1WaitingProcessQueueTable.getRowCount(); i++) {//For each row
                for (int j = 0; j < cpu1WaitingProcessQueueTable.getColumnCount(); j++) {//For each column in that row
                    if (cpu1WaitingProcessQueueTable.getModel().getValueAt(i, j).toString().trim().equals(exec)) {
                        System.out.println("A");
                        cpu1WaitingTableModel.removeRow(i);
                    }
                }
            }
        }
        else{
            // Goes through all the processes in the queue and add them to the  queue
            for (int i = 0; i < cpu2WaitingProcessQueueTable.getRowCount(); i++) {//For each row
                for (int j = 0; j < cpu2WaitingProcessQueueTable.getColumnCount(); j++) {//For each column in that row
                    if (cpu2WaitingProcessQueueTable.getModel().getValueAt(i, j).toString().trim().equals(exec)) {
                        System.out.println("A");
                        cpu2WaitingTableModel.removeRow(i);
                    }
                }
            }
        }
    }

    /**
     * Updates the finished GUI table
     * @param exec is the process executing
     * @param totaltime is the total time the program took
     * @param arrival is the arrival time
     * @param service is the service time
     */
    public void UpdateFinishedTable(int cpu, String exec, int totaltime, int arrival, int service){
        int temp=0;
        if (cpu == 1) {
            // Loop through each item in the completed queue and place it in the GUI table
            /*
             * Currently working from a sequential order for the finish time from old code.
             * Couldn't figure out way to pass in finish time from round robin scheduler on process
             * to process basis.
             */
            for (int i = 0; i < cpu1CompletedProcessQueueTable.getRowCount(); i++) {//For each row
                if (!(cpu1CompletedProcessQueueTable.getModel().getValueAt(i, 0) == null)) {
                    if (cpu1CompletedProcessQueueTable.getModel().getValueAt(i, 0).toString().trim().equals(exec)) {
                        if (i > 0)
                            temp = Integer.parseInt(cpu1CompletedProcessQueueTable.getModel().getValueAt(i - 1, 3).toString());
                        cpu1CompletedTableModel.setValueAt(totaltime - temp, i, 3);
                        cpu1CompletedTableModel.setValueAt((totaltime - temp) + arrival, i, 4);
                        cpu1CompletedTableModel.setValueAt(((totaltime - temp) + arrival) / service, i, 5);

                        //Calculate avegage nTAT time per process for cpu1
                        cpu1nTATSum += ((totaltime - temp) + arrival) / service;
                        cpu1nTAT = (cpu1nTATSum) / (i + 1);

                        //Update cpu1 average nTAT label
                        cpu1nTATLabel.setText("<html><strong><font color='FFFFFF'><font size = 60px>Current average nTAT: " + new DecimalFormat("#.###").format(cpu1nTAT));
                        size = cpu1nTATLabel.getPreferredSize();
                        cpu1nTATLabel.setBounds(100, 450, size.width, size.height);
                    }
                }
            }
        }
        else{
            // Loop through each item in the completed queue and place it in the GUI table
            for (int i = 0; i < cpu2CompletedProcessQueueTable.getRowCount(); i++) {//For each row
                if (!(cpu2CompletedProcessQueueTable.getModel().getValueAt(i, 0) == null)) {
                    if (cpu2CompletedProcessQueueTable.getModel().getValueAt(i, 0).toString().trim().equals(exec)) {
                        if (i > 0)
                            temp = Integer.parseInt(cpu2CompletedProcessQueueTable.getModel().getValueAt(i - 1, 3).toString());
                        cpu2CompletedTableModel.setValueAt(totaltime-temp, i, 3);
                        cpu2CompletedTableModel.setValueAt((totaltime - temp) + arrival, i, 4);
                        cpu2CompletedTableModel.setValueAt(((totaltime - temp) + arrival) / service, i, 5);

                        //Calculate avegage nTAT time per process for cpu1
                        cpu2nTATSum += ((totaltime - temp) + arrival) / service;
                        System.out.println("nTAT Sum: "+cpu2nTATSum);
                        cpu2nTAT = (cpu2nTATSum) / (i + 1);
                        System.out.println("nTAT Average"+cpu2nTAT);

                        //Update cpu1 average nTAT label
                        cpu2nTATLabel.setText("<html><strong><font color='FFFFFF'><font size = 60px>Current average nTAT: " + new DecimalFormat("##.###").format(cpu2nTAT));
                        size = cpu2nTATLabel.getPreferredSize();
                        cpu2nTATLabel.setBounds(600, 450, size.width, size.height);
                    }
                }
            }
        }
    }

    /**
     * Gets the time unit text field as an int
     * @return
     */
    public int getTimeUnitTextField()
    {
        return Integer.parseInt(timeUnitTextField.getText());
    }

    public int getTimeSliceTextField()
    {
        return Integer.parseInt(sliceLengthTextField.getText());
    }


    //Window component variables
    private JLabel systemStateLabel;
    private JLabel timeUnitLabel;
    private JLabel timeUnitInstructionLabel;
    private JLabel unitTypeLabel;
    private JLabel cpu1Label;
    private JLabel cpu2Label;
    private JLabel filePathLabel;
    private JLabel filePathInstructionLabel;
    private JLabel sliceLengthInstructionLabel;
    private JLabel filePathWarningLabel;
    private JLabel cpu1nTATLabel;
    private JLabel cpu2nTATLabel;
    private JLabel cpu1WaitingProcessLabel;
    private JLabel cpu2WaitingProcessLabel;
    private JLabel roundRobinLabel;

    private JButton startButton;
    private JButton pauseButton;

    private JTable cpu1WaitingProcessQueueTable;
    private JTable cpu1CompletedProcessQueueTable;
    private JTable cpu2WaitingProcessQueueTable;
    private JTable cpu2CompletedProcessQueueTable;

    private JScrollPane cpu1WaitingProcessScrollingPane;
    private JScrollPane cpu1CompletedProcessScrollingPane;
    private JScrollPane cpu2WaitingProcessScrollingPane;
    private JScrollPane cpu2CompletedProcessScrollingPane;

    private JTextField timeUnitTextField;
    private JTextField fileNameTextField;
    private JTextField sliceLengthTextField;

    private DefaultTableModel cpu1WaitingTableModel;
    private DefaultTableModel cpu2WaitingTableModel;
    private DefaultTableModel cpu1CompletedTableModel;
    private DefaultTableModel cpu2CompletedTableModel;

    private Dimension size;
    public static String file;
    public static int whichCPU;
}
