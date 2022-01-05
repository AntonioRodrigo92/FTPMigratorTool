package TaskHandler;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLogic {
    private ExecutorService threads;
    private LinkedList<RunnableTask> tasks;

    public ThreadLogic(LinkedList<RunnableTask> tasks, int numWorkers) {
        this.tasks = tasks;
        this.threads = Executors.newFixedThreadPool(numWorkers);
    }

    public void executeTasks() {
        while (! tasks.isEmpty()) {
            RunnableTask task = tasks.pop();
            threads.execute(task);
        }
    }

    public void waitForAllThreadsToFinish() throws InterruptedException {
        threads.shutdown();
        threads.awaitTermination(6, TimeUnit.HOURS);
    }

}