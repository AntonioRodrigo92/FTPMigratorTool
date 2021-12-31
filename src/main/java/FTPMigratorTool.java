import Connections.FTPClientConnector;
import TaskHandler.RunnableTask;
import TaskHandler.ThreadLogic;
import Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;

public class FTPMigratorTool {
    private final int NUM_WORKERS = Runtime.getRuntime().availableProcessors();
    private final String BASE_DIRECTORY = "";
    private LinkedList<RunnableTask> tasks;
    private String server;
    private int port;
    private String user;
    private String pass;
//    private MongoConnector mongo;
    private FTPClientConnector ftpClient;
    private Calendar date;

    public FTPMigratorTool(String server, int port, String user, String pass) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.tasks = new LinkedList<>();
//        this.mongo = new MongoConnector(tasks);
        this.ftpClient = new FTPClientConnector();
//        this.date = mongo.getLastDay();


        this.date = Calendar.getInstance();
        this.date.set(2021, 12, 11);
    }

    private void migrate() {
        try {
            ftpClient.connect(server, port, user, pass);
            File outputDir = Utils.createNewDir(BASE_DIRECTORY, date);

//            TODO - ter todas as tasks para executar (i.e., remote files)

            ThreadLogic logic = new ThreadLogic(tasks, NUM_WORKERS);
            logic.executeTasks();
            logic.waitForAllThreadsToFinish();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {

    }
}

