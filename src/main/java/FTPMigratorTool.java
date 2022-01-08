import Connections.FTPClientConnector;
import Connections.MongoConnector;
import RemoteFTP.RemoteFTPServer;
import RemoteFTP.RemoteFile;
import TaskHandler.RunnableTask;
import TaskHandler.ThreadLogic;
import Utils.Utils;
import Utils.UserInput;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
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
        this.date = Utils.sumOneDay(mongo.getLastDay());
    }

    private void migrate() {
        try {

            System.out.println(date);
            System.out.println(Utils.yesterday());

//            for (Document doc : mongo.getFailedTasks()) {
//                RunnableTask task = Utils.docToTunnableTask(ftpClient.getFtpClient(), mongo, doc);
//                tasks.add(task);
//            }

            while (! date.equals(Utils.yesterday())) {
                System.out.println("INICIO: " + date);
                Date startTime = Utils.getCurrentDateTime();

                ftpClient.connect(userInput.getFtpServer(), userInput.getFtpPort(), userInput.getFtpUser(), userInput.getFtpPass());
                File outputDir = Utils.createNewDir(userInput.getBaseDirectory(), date);

                RemoteFTPServer remoteServer = new RemoteFTPServer(ftpClient.getFtpClient(), mongo, date);
                tasks.addAll(remoteServer.getFilesAsTasks(outputDir));
                int taskSize = tasks.size();

                ThreadLogic logic = new ThreadLogic(tasks, NUM_WORKERS);
                logic.executeTasks();
                logic.waitForAllThreadsToFinish();

                Date endTime = Utils.getCurrentDateTime();
//                mongo.writeFinalizedDay(date, startTime, endTime, taskSize);
//                date = Utils.sumOneDay(date);
            }

            ftpClient.disconnect();
            mongo.closeConnection();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        String path = "/home/antonio/IdeaProjects/FTPMigratorTool/src/main/resources/input.txt";
        UserInput userInput = new UserInput(path);
        FTPMigratorTool migrator = new FTPMigratorTool(userInput);
        migrator.migrate();
    }
}

