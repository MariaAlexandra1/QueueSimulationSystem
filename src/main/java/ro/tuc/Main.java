package ro.tuc;

import BusinessLogic.*;
import Model.*;
import gui.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        SimulationFrame simulationFrame = new SimulationFrame();
        simulationFrame.setVisible(true);

        simulationFrame.startListener(e -> {
            simulationFrame.SetResult("Simulation started\n");
            SimulationManager simulationManager = new SimulationManager(simulationFrame);
            Thread thread = new Thread(simulationManager);
            thread.start();
        });
    }
}