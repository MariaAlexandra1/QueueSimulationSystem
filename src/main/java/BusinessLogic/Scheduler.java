package BusinessLogic;
import Model.*;
import java.util.ArrayList;
import java.util.List;
public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    public Scheduler(int maxNoServers, int maxTasksPerServer, Strategy strategy){
        this.maxNoServers = maxNoServers;
        this.maxTasksPerServer = maxTasksPerServer;
        this.strategy = strategy;
        servers = new ArrayList<Server>(maxNoServers);
        for(int i = 0; i < maxNoServers; i++){
            Server server = new Server(i, maxTasksPerServer);
            servers.add(server);
            Thread t = new Thread(server);
            t.start();
        }
    }
    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }
    public void dispatchTask(Task task){
        strategy.addTask(servers, task);
    }
    public List<Server> getServers(){
        return servers;
    }
    public boolean hasTasks() {
        for(Server server : servers){
            if(!server.getTasks().isEmpty()){
                return true;
            }
        }
        return false;
    }
}
