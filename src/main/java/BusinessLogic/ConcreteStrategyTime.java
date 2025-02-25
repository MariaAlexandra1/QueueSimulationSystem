package BusinessLogic;
import Model.*;
import java.util.List;

public class ConcreteStrategyTime implements Strategy{
    @Override
    public void addTask(List<Server> servers, Task task) {
        Server minServer = servers.getFirst();
        for(Server server : servers){
            if(server.getWaitingPeriod().intValue() < minServer.getWaitingPeriod().intValue() && server.getTasks().size() < server.getMaxTasksPerServer()){
                minServer = server;
            }
        }
        SimulationManager.addAvgWTime(minServer.getWaitingPeriod().intValue());
        minServer.addTask(task);
    }
}


