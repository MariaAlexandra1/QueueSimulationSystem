package BusinessLogic;
import Model.*;
import java.util.List;

public interface Strategy {
    public void addTask(List<Server> servers, Task task);
}
