package BusinessLogic;
import Model.*;
import gui.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class SimulationManager implements Runnable{
    public int timeLimit;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int minServiceTime;
    public int maxServiceTime;
    public int numberOfServers; //Q
    public int numberOfClients; //N
    private static float avgWaitingTime;
    private static float avgServiceTime;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Task> generatedTasks;
    private Logger logger;
    public SimulationManager(SimulationFrame frame){
        this.frame = frame;
        String STimeLimit = frame.getTfMaxSimulationTime().getText().trim();
        String SMinArrivalTime = frame.getTfMinArrivalTime().getText().trim();
        String SMaxArrivalTime = frame.getTfMaxArrivalTime().getText().trim();
        String SMinServiceTime = frame.getTfMinServiceTime().getText().trim();
        String SMaxServiceTime = frame.getTfMaxServiceTime().getText().trim();
        String SNumberOfServers = frame.getTfNrQueues().getText().trim();
        String SNumberOfClients = frame.getTfNrClienti().getText().trim();
        try {
            this.timeLimit = Integer.parseInt(STimeLimit);
            this.minArrivalTime = Integer.parseInt(SMinArrivalTime);
            this.maxArrivalTime = Integer.parseInt(SMaxArrivalTime);
            this.minServiceTime = Integer.parseInt(SMinServiceTime);
            this.maxServiceTime = Integer.parseInt(SMaxServiceTime);
            this.numberOfServers = Integer.parseInt(SNumberOfServers);
            this.numberOfClients = Integer.parseInt(SNumberOfClients);
        } catch(NumberFormatException e){
            frame.getResult().setText("Invalid input\n");
            throw new IllegalArgumentException("Invalid input");
        }
        if(this.timeLimit <= 0 || this.minArrivalTime < 0 || this.maxArrivalTime <= 0 || this.minServiceTime < 0 || this.maxServiceTime <= 0 || this.numberOfServers <= 0 || this.numberOfClients <= 0){
            frame.getResult().setText("Invalid input\n");
            throw new IllegalArgumentException("Invalid input");
        }
        if(this.minArrivalTime >= this.maxArrivalTime || this.minServiceTime >= this.maxServiceTime){
            frame.getResult().setText("Invalid input\n");
            throw new IllegalArgumentException("Invalid input");
        }

            this.generatedTasks = new ArrayList<Task>();
            Strategy strategy = frame.getcbSelectionPolicy().equals("SHORTEST_QUEUE") ? new ConcreteStrategyQueue() : new ConcreteStrategyTime();
            this.scheduler = new Scheduler(numberOfServers, 100000, strategy);
            generateRandomTasks(numberOfClients, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime);

            this.logger = Logger.getLogger("SimulationLogger");
            try {
                FileHandler fileHandler = new FileHandler("simulation.log");
                fileHandler.setFormatter(new SimpleFormatter() {
                    private static final String format = " %1$s %n";
                    @Override
                    public synchronized String format(java.util.logging.LogRecord lr) {
                        return String.format(format,lr.getMessage());
                    }
                });
                logger.addHandler(fileHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void generateRandomTasks(int numberOfClients, int minArrivalTime, int maxArrivalTime, int minServiceTime, int maxServiceTime){
        for(int i = 0; i < numberOfClients; i++){
            int arrivalTime = (int)(Math.random() * (maxArrivalTime - minArrivalTime) + minArrivalTime);
            int serviceTime = (int)(Math.random() * (maxServiceTime - minServiceTime) + minServiceTime);
            Task t = new Task(i, arrivalTime, serviceTime);
            this.generatedTasks.add(t);
        }
        Collections.sort(generatedTasks);
    }
    @Override
    public void run() {
        String simulation = "Simulation started\n";
        int peak_hour = 0;
        int maxTasks = 0;
        frame.SetResult(simulation);
        int currentTime = 0;
        while(currentTime < timeLimit) {
            int aux = 0;
            while (!generatedTasks.isEmpty() && generatedTasks.get(0).getArrivalTime() == currentTime) {
                Task t = generatedTasks.get(0);
                if (t.getArrivalTime() + t.getServiceTime() < timeLimit) {
                    scheduler.dispatchTask(t);
                    addAvgSTime(t.getServiceTime());
                    generatedTasks.remove(0);
                }
            }
            simulation += "\nTime: " + currentTime + "\n";
            frame.SetResult("Time: " + currentTime + "\n");
            simulation += "Waiting clients: " + generatedTasks + "\n";
            frame.SetResult("Waiting clients: " + generatedTasks + "\n");
            for (Server s : scheduler.getServers()) {
                aux += s.getTasks().size();
                simulation += s.toString() + "\n";
                frame.SetResult(s.toString() + "\n");
            }
            if (aux > maxTasks) {
                maxTasks = aux;
                peak_hour = currentTime;
            }
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(generatedTasks.isEmpty() && !scheduler.hasTasks()){
                break;
            }
        }
            simulation += "\nTime: " + currentTime + "\n";
            frame.SetResult("Time: " + currentTime + "\n");
            simulation += "Waiting clients: " + generatedTasks + "\n";
            frame.SetResult("Waiting clients: " + generatedTasks + "\n");
            for (Server s : scheduler.getServers()) {
                simulation += s.toString() + "\n";
                frame.SetResult(s.toString() + "\n");
            }
            simulation += "\n" + "Simulation ended\n";
            frame.SetResult("Simulation ended\n");
            float avgWT = avgWaitingTime / (float)numberOfClients;
            float avgST = avgServiceTime / (float)numberOfClients;
            frame.SetResult("Average waiting time: " + avgWT + "\n");
            simulation += "Average waiting time: " + avgWT + "\n";
            frame.SetResult("Average service time: " + avgST + "\n");
            simulation += "Average service time: " + avgST + "\n";
            frame.SetResult("Peak hour: " + peak_hour + "\n");
            simulation += "Peak hour: " + peak_hour + "\n";
            logger.info(simulation);
    }

    public static void addAvgWTime(int avgWTime){
        avgWaitingTime += avgWTime;
    }
    public static void addAvgSTime(int avgSTime){
        avgServiceTime += avgSTime;
    }

}