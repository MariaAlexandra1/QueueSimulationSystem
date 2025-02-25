package Model;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    private int maxTasksPerServer;
    private final int id;
    public Server(int id, int maxT){
        this.maxTasksPerServer = maxT;
        tasks = new ArrayBlockingQueue<>(maxTasksPerServer);
        waitingPeriod = new AtomicInteger();
        this.id = id;
    }
    public void addTask(Task newTask){
        tasks.add(newTask);
        waitingPeriod.getAndAdd(newTask.getServiceTime());
    }
    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }
    public BlockingQueue<Task> getTasks() {return tasks;}
    public int getMaxTasksPerServer() {return maxTasksPerServer;}
    public int getWaitingTime() { return waitingPeriod.get();}
    @Override
    public void run() {
         while(true) {
             while (tasks.peek() != null) {
                 Task t = tasks.peek();
                 try {
                     Thread.sleep(1000);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
                 this.waitingPeriod.getAndDecrement();
                 t.setServiceTime(t.getServiceTime() - 1);
                 if (t.getServiceTime() == 0) {
                     try {
                         tasks.take();
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                 }
             }
         }
    }

    public String toString() {
        String serv = "";
        if (tasks.isEmpty()) {
            serv += "Queue " + id + ":" + "closed";
        } else {
            serv += "Queue " + id + ":";
            for (Task t : tasks) {
                serv += t.toString() + "; ";
            }
        }
        return serv;
    }
}
