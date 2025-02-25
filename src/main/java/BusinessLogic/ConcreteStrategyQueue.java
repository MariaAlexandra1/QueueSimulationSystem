package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server minServer = servers.getFirst();
        for(Server server : servers){
            if(server.getTasks().size() < minServer.getTasks().size() && server.getTasks().size() < server.getMaxTasksPerServer()){
                minServer = server;
            }
        }
        SimulationManager.addAvgWTime(minServer.getWaitingTime());
        minServer.addTask(task);
    }
}
