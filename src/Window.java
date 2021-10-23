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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/*
 * Class declaration for Window class extending JPanel for component placement and
 * implementing ActionListener to allow for actions on button click and text entry.
 */
public class Window extends JPanel implements ActionListener{
    public Timer timer = new Timer();
    private CPU cpu1=new CPU(1,this);
    private CPU cpu2=new CPU(2,this);
    private boolean firstStart=true;
    //Constructor for Window class consisting of components in Panel
    public Window () {
        //Arbitrary size and color appearance of the window
        Dimension dim = new Dimension(600, 500);
        setMinimumSize(dim);
        setPreferredSize(dim);
        setLayout(null);
        setBackground(Color.darkGray);

        //Label stating current system state
        String systemState = "System Paused";
        systemStateLabel = new JLabel("<html><font color='FFFFFF'>"+ systemState +"</font></html>");
        size = systemStateLabel.getPreferredSize();
        systemStateLabel.setBounds(350, 110, size.width, size.height);
        add(systemStateLabel);

        //Label stating what a single time unit is equal to currently
        timeUnitLabel = new JLabel("<html><font color='FFFFFF'>1 time unit = </font></html>");
        size = timeUnitLabel.getPreferredSize();
        timeUnitLabel.setBounds(300, 160, size.width, size.height);
        add(timeUnitLabel);

        //Label stating time unit of ms
        unitTypeLabel = new JLabel("<html><font color='FFFFFF'>ms</font></html>");
        size = unitTypeLabel.getPreferredSize();
        unitTypeLabel.setBounds(460, 160, size.width, size.height);
        add(unitTypeLabel);

        //Label for process throughput value display
        throughputLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>Current Throughput: 0 process/unit of time</font></font></strong></html>");
        size = throughputLabel.getPreferredSize();
        throughputLabel.setBounds(150,450,size.width,size.height);
        add(throughputLabel);

        //Label for waiting process queue header
        waitingProcessLabel = new JLabel("<html><strong><font color='FFFFFF'><font size = 60px>Waiting Process Queue</font></font></strong></html>");
        size = waitingProcessLabel.getPreferredSize();
        waitingProcessLabel.setBounds(60,150,size.width,size.height);
        add(waitingProcessLabel);

        //Label depicting current process running information for cpu 1
        cpu1Label = new JLabel("<html>cpu 1<br/>exec: idle<br/>time remaining = n/a</html>");
        size = cpu1Label.getPreferredSize();
        cpu1Label.setBounds(320, 200, (size.width+10), (size.height+10));
        cpu1Label.setOpaque(true);
        cpu1Label.setBackground(Color.yellow);
        add(cpu1Label);

        //Label depicting current process running information for cpu 2
        cpu2Label = new JLabel("<html>cpu 2<br/>exec: idle<br/>time remaining = n/a</html>");
        size = cpu2Label.getPreferredSize();
        cpu2Label.setBounds(320, 270, (size.width+10), (size.height+10));
        cpu2Label.setOpaque(true);
        cpu2Label.setBackground(Color.yellow);
        add(cpu2Label);

        //Label for process file path
        filePathLabel = new JLabel("<html><font color='FFFFFF'>Process File Path : </font></html>");
        size = filePathLabel.getPreferredSize();
        filePathLabel.setBounds(50, 40, size.width, size.height);
        add(filePathLabel);

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
        timeUnitTextField.setBounds(385, 155, size.width, size.height);
        add(timeUnitTextField);

        //Text field accepting input for values for the file path
        fileNameTextField = new JTextField(30);
        fileNameTextField.addActionListener(this);
        fileNameTextField.setText("InputFiles/test.txt");
        file = fileNameTextField.getText();
        size = fileNameTextField.getPreferredSize();
        fileNameTextField.setBounds(170, 40, size.width, size.height);
        add(fileNameTextField);

        //Button for starting system processes
        startButton = new JButton("Start System");
        size = startButton.getPreferredSize();
        startButton.setBounds(20, 100, size.width, size.height);
        startButton.addActionListener(this);
        add(startButton);

        //Button for pausing system processes
        pauseButton = new JButton("Pause System");
        size = pauseButton.getPreferredSize();
        pauseButton.setBounds(170, 100, size.width, size.height);
        pauseButton.addActionListener(this);
        add(pauseButton);

        /*
         * Table model for waiting process table to dynamically add rows for each process
         */
        waitingTableModel = new DefaultTableModel();
        waitingTableModel.addColumn("Process Name");
        waitingTableModel.addColumn("Arrival Time");
        waitingTableModel.addRow(new Object[]{"",""});

        /*
         * Table with headers contained in scroll pane to depict
         * process name and service time for waiting processes.
         */
        waitingProcessQueueTable = new JTable(waitingTableModel);
        waitingProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader waitingProcessHeader = waitingProcessQueueTable.getTableHeader();
        waitingProcessHeader.setBackground(Color.gray);
        waitingProcessScrollingPane = new JScrollPane(waitingProcessQueueTable);
        waitingProcessScrollingPane.setBounds(20, 190, 250, 100);
        add(waitingProcessScrollingPane);

        /*
         * Table model for completed process table to dynamically add rows for each process
         */
        completedTableModel = new DefaultTableModel();
        completedTableModel.addColumn("Process Name");
        completedTableModel.addColumn("Arrival Time");
        completedTableModel.addColumn("Service Time");
        completedTableModel.addColumn("Finish Time");
        completedTableModel.addColumn("TAT");
        completedTableModel.addColumn("nTAT");

        /*
         * Table with headers contained in completed process
         * data.
         */
        completedProcessQueueTable = new JTable(completedTableModel);
        completedProcessQueueTable.setBackground(Color.lightGray);
        JTableHeader completedProcessHeader = completedProcessQueueTable.getTableHeader();
        completedProcessHeader.setBackground(Color.gray);
        completedProcessScrollingPane = new JScrollPane(completedProcessQueueTable);
        completedProcessScrollingPane.setBounds(20, 350, 540, 70);
        add(completedProcessScrollingPane);
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
                systemStateLabel.setBounds(350, 110, size.width, size.height);
                FileReader reader = new FileReader();


                Processes processInstance = new Processes();
                // Read the file
                List<String> processesRead = reader.ReadFile(file);

                if (processesRead == null) {
                    filePathWarningLabel.setVisible(true);
                }

                Queue<String> tempServ = new LinkedList<>();

                for (int i = 0; i < processesRead.size(); i++) {
                    DisplayProcesses(reader, processesRead, i, processInstance);
                }
                cpu1.start();
                cpu2.start();
            }
            else {
                cpu1.resume();
                cpu2.resume();

            }
        }

        
        if (action.getSource().equals(pauseButton))
        {
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
                    systemStateLabel.setBounds(350, 110, size.width, size.height);
//
                }
            });
        }

        if (action.getSource().equals(timeUnitTextField))
        {
            String input = timeUnitTextField.getText();
            int inputInt = Integer.parseInt(input);
            timer.setTimeUnit(inputInt);
            System.out.println(inputInt);
        }
        if (action.getSource().equals(fileNameTextField))
        {
            filePathWarningLabel.setVisible(false);
            file = fileNameTextField.getText();
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
            cpu1Label.setText("<html>" + "cpu 1" + "<br/>exec: " + waitingProcessQueueTable.getValueAt(0,0) + "<br/>time remaining = " + waitingProcessQueueTable.getValueAt(0,1) + "</html>");

            //Set table rows for current process
            waitingTableModel.insertRow(i,new Object[]{processName.peek(),arrivalTime.peek()});
            completedTableModel.insertRow(i,new Object[]{processName.peek(),arrivalTime.peek(),serviceTime.peek()});

            //CPU cpu1 = new CPU(1, this);
            //CPU cpu2 = new CPU(2, this);

//            if (!common.CPU1RUNNING) {
//                runCPU1(process, processName,serviceTime, cpu1);
//            } else if (!common.CPU2RUNNING) {
//                runCPU2(process, processName, serviceTime);
//            }
        }
    }

//    public void runCPU1(Processes process, Queue<String> processName, Queue<String> serviceTime, CPU cpu1)
//    {
//        synchronized (this) {
//            common.CPU1RUNNING = !process.RunProcess(processName.peek(), Integer.parseInt(serviceTime.peek()), common.CPU1RUNNING);
//            // common.CPU1RUNNING = false;
//            System.out.println("I ran on CPU 1");
//            cpu1.CalculateThroughput();
//        }
//    }
//
//    public void runCPU2(Processes process, Queue<String> processName, Queue<String> serviceTime)
//    {
//        synchronized (this) {
//            common.CPU1RUNNING = !process.RunProcess(processName.peek(), Integer.parseInt(serviceTime.peek()), common.CPU1RUNNING);
//            // common.CPU1RUNNING = false;
//            System.out.println("I ran on CPU 2");
//        }
//    }
    /**
     * Updates the throughput time in the GUI
     * @param throughput
     */
    public void UpdateThroughput(double throughput)
    {
        throughputLabel.setText("<html><strong><font color='FFFFFF'><font size = 60px>Current Throughput: " + (throughput) + " process/unit of time</font></font></strong></html>");
        size = throughputLabel.getPreferredSize();
        throughputLabel.setBounds(150,450,size.width,size.height);
    }
    public void UpdateCPU(int cpu,String exec, int time)
    {


        //if(waitingProcessQueueTable.getModel().getValueAt(0,0).toString().trim().equals(exec)) System.out.println("exec");
        //System.out.println(waitingProcessQueueTable.getModel().getValueAt(0,0).toString());
        //System.out.println(exec);
        switch(cpu){
            case 1:
                cpu1Label.setText("<html>" + "cpu 1" + "<br/>exec: " + exec + "<br/>time remaining = "
                        + time + "</html>");
                break;
            case 2:
                cpu2Label.setText("<html>" + "cpu 2" + "<br/>exec: " + exec + "<br/>time remaining = "
                        + time + "</html>");
                break;
            case 3:
                break;
        }


    }
    public void UpdateWaitTable(String exec) {

        for(int i = 0; i < waitingProcessQueueTable.getRowCount(); i++) {//For each row
            for (int j = 0; j < waitingProcessQueueTable.getColumnCount(); j++) {//For each column in that row
                if (waitingProcessQueueTable.getModel().getValueAt(i, j).toString().trim().equals(exec)) {
                    System.out.println("A");
                    waitingTableModel.removeRow(i);
                }
            }
        }
    }
    public void UpdateFinishedTable(String exec, int totaltime, int arrival, int service){
        int temp=0;
        for(int i = 0; i < completedProcessQueueTable.getRowCount(); i++) {//For each row
                if(!(completedProcessQueueTable.getModel().getValueAt(i, 0)==null)) {
                    if (completedProcessQueueTable.getModel().getValueAt(i, 0).toString().trim().equals(exec)) {
                        if(i>0) temp=Integer.parseInt(completedProcessQueueTable.getModel().getValueAt(i-1,3).toString());
                        completedTableModel.setValueAt(totaltime-temp, i, 3);
                        completedTableModel.setValueAt((totaltime-temp)+arrival, i, 4);
                        completedTableModel.setValueAt(((totaltime-temp)+arrival)/service, i, 5);

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

    //Window component variables
    private JLabel systemStateLabel;
    private JLabel timeUnitLabel;
    private JLabel unitTypeLabel;
    private JLabel cpu1Label;
    private JLabel cpu2Label;
    private JLabel filePathLabel;
    private JLabel filePathWarningLabel;
    private JLabel throughputLabel;
    private JLabel waitingProcessLabel;

    private JButton startButton;
    private JButton pauseButton;

    private JTable waitingProcessQueueTable;
    private JTable completedProcessQueueTable;

    private JScrollPane waitingProcessScrollingPane;
    private JScrollPane completedProcessScrollingPane;

    private JTextField timeUnitTextField;
    private JTextField fileNameTextField;

    private DefaultTableModel waitingTableModel;
    private DefaultTableModel completedTableModel;

    private Dimension size;
    public static String file;
}
