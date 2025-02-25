package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {
    private JTextField tfNrClienti = new JTextField();
    private JTextField tfNrQueues = new JTextField();
    private JTextField tfMaxSimulationTime = new JTextField();
    private JTextField tfMinArrivalTime = new JTextField();
    private JTextField tfMaxArrivalTime = new JTextField();
    private JTextField tfMinServiceTime = new JTextField();
    private JTextField tfMaxServiceTime = new JTextField();
    private JButton btStart = new JButton("START");
    private JButton btClear = new JButton("CLEAR");
    private JComboBox<String> cbSelectionPolicy = new JComboBox<String>();
    private JTextArea result;
    JScrollPane scrollPane;
    Font f1 = new Font("SansSerif", Font.BOLD, 20);
    Font outputFont=new Font("TimesNewRoman", Font.PLAIN, 15);
    Font f2 = new Font("SansSerif", Font.PLAIN, 15);
    Color bgColor =new Color(51, 204, 255, 50);

    public SimulationFrame() {
        this.setPreferredSize(new Dimension(1000, 1700));
        this.setMinimumSize(new Dimension(900, 1500));
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel titlu = new JLabel("Simulation");
        titlu.setFont(f1);
        mainPanel.add(titlu);
        titlu.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlu.setAlignmentY(Component.CENTER_ALIGNMENT);

        JPanel panelInput = new JPanel(new GridLayout(8, 2));
        panelInput.setLayout(new BoxLayout(panelInput, BoxLayout.Y_AXIS));
        panelInput.setSize(1000, 1000);

        JLabel l1 = new JLabel("Number of clients: ");
        l1.setFont(f2); l1.setBackground(bgColor);
        panelInput.add(l1);
        panelInput.add(tfNrClienti);

        JLabel l2 = new JLabel("Number of queues: ");
        l2.setFont(f2); l2.setBackground(bgColor);
        panelInput.add(l2);
        panelInput.add(tfNrQueues);

        JLabel l3 = new JLabel("Simulation time: ");
        l3.setFont(f2);
        panelInput.add(l3);
        panelInput.add(tfMaxSimulationTime);

        JLabel l4 = new JLabel("Time min arrival: ");
        l4.setFont(f2);
        panelInput.add(l4);
        panelInput.add(tfMinArrivalTime);

        JLabel l5 = new JLabel("Time max arrival: ");
        l5.setFont(f2);
        panelInput.add(l5);
        panelInput.add(tfMaxArrivalTime);

        JLabel l6 = new JLabel("Time min service: ");
        l6.setFont(f2);
        panelInput.add(l6);
        panelInput.add(tfMinServiceTime);

        JLabel l7 = new JLabel("Time max service: ");
        l7.setFont(f2);
        panelInput.add(l7);
        panelInput.add(tfMaxServiceTime);

        JPanel panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(panelButton, BoxLayout.X_AXIS));
        btStart.setFont(f2);
        btClear.setFont(f2);
        panelButton.add(btStart);
        panelButton.add(btClear);
        cbSelectionPolicy.addItem("SHORTEST_QUEUE");
        cbSelectionPolicy.addItem("SHORTEST_TIME");
        cbSelectionPolicy.setFont(f2);
        cbSelectionPolicy.setAlignmentY(Component.CENTER_ALIGNMENT);
        panelInput.add(cbSelectionPolicy);

        panelInput.setBackground(bgColor);
        panelInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelInput.setAlignmentY(Component.CENTER_ALIGNMENT);
        mainPanel.add(panelInput);
        mainPanel.add(panelButton);

        result = new JTextArea(100000, 1000);
        result.setSize(1000, 500);
        result.setFont(outputFont);
        result.setEditable(false);
        scrollPane = new JScrollPane(result);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane);

        this.setTitle("Simulation");
        this.add(mainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result.setText("");
                tfNrClienti.setText("");
                tfNrQueues.setText("");
                tfMaxSimulationTime.setText("");
                tfMinArrivalTime.setText("");
                tfMaxArrivalTime.setText("");
                tfMinServiceTime.setText("");
                tfMaxServiceTime.setText("");
            }
        });
    }
    public JTextField getTfNrClienti() {
        return tfNrClienti;
    }
    public JTextField getTfNrQueues() {return tfNrQueues;}
    public JTextField getTfMaxSimulationTime() {
        return tfMaxSimulationTime;
    }
    public JTextField getTfMinArrivalTime() {
        return tfMinArrivalTime;
    }
    public JTextField getTfMaxArrivalTime() {
        return tfMaxArrivalTime;
    }
    public JTextField getTfMinServiceTime() {
        return tfMinServiceTime;
    }
    public JTextField getTfMaxServiceTime() {
        return tfMaxServiceTime;
    }
    public void SetResult(String res) {
        String text = result.getText();
        result.setText(text + res);
    }
    public String getcbSelectionPolicy() {return cbSelectionPolicy.getSelectedItem().toString();}
    public void startListener(ActionListener start) {
        this.btStart.addActionListener(start);
    }
    public JTextArea getResult() {return result;}
}
