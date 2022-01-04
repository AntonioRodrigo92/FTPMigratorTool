import Connections.FTPClientConnector;
import Connections.MongoConnector;
import RemoteFTP.RemoteFTPServer;
import TaskHandler.RunnableTask;
import TaskHandler.ThreadLogic;
import Utils.Utils;
import Utils.UserInput;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.LinkedList;

public class FTPMigratorTool {
//    private final int NUM_WORKERS = Runtime.getRuntime().availableProcessors();
    private final int NUM_WORKERS = 1;
    private LinkedList<RunnableTask> tasks;
    private UserInput userInput;
    private MongoConnector mongo;
    private FTPClientConnector ftpClient;
    private LocalDate date;

    public FTPMigratorTool(UserInput userInput) {
        this.userInput = userInput;
        this.tasks = new LinkedList<>();
        this.mongo = new MongoConnector(userInput.getMongoURI(), userInput.getMongoDatabase(), userInput.getMongoCollectionDays(), userInput.getMongoCollectionFailures());
        this.ftpClient = new FTPClientConnector();
        this.date = mongo.getLastDay();


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 4);

        date = Utils.calendarToLocalDate(cal);
    }

    private void migrate() {
        try {
            ftpClient.connect(userInput.getServer(), userInput.getPort(), userInput.getUser(), userInput.getPass());
            File outputDir = Utils.createNewDir(userInput.getBaseDirectory(), date);

            System.out.println("CONNECTED");

            long st = System.nanoTime();

            RemoteFTPServer remoteServer = new RemoteFTPServer(ftpClient.getFtpClient(), date);
            tasks.addAll(remoteServer.getFilesAsTasks(outputDir));

            System.out.println("THREADS A COMEÇAR: " + tasks.size());
            ThreadLogic logic = new ThreadLogic(tasks, NUM_WORKERS);
            logic.executeTasks();
            logic.waitForAllThreadsToFinish();
            System.out.println("THREADS ACABARAM");

            long timeSpent = System.nanoTime() - st;
            System.out.println(timeSpent);

            ftpClient.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
//        FTPMigratorTool migrator = new FTPMigratorTool("192.168.1.1", 21, "sandisk16gb", "PalavraPasse1");
//        migrator.migrate();
    }
}

