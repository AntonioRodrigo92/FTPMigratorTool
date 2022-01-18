import Connections.FTPClientConnector;
import Connections.MongoConnector;
import RemoteFTP.RemoteFTPServer;
import TaskHandler.RunnableTask;
import TaskHandler.ThreadLogic;
import Utils.Utils;
import Utils.UserInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger LOG = LogManager.getLogger();


    public FTPMigratorTool(UserInput userInput) {
        this.userInput = userInput;
        this.tasks = new LinkedList<>();
        this.mongo = new MongoConnector(userInput.getMongoURI(), userInput.getMongoDatabase(), userInput.getMongoCollectionDays(), userInput.getMongoCollectionFailures());
        this.ftpClient = new FTPClientConnector();
        this.date = Utils.sumOneDay(mongo.getLastDay());
    }

    private void migrate() {
        try {
            ftpClient.connect(userInput.getFtpServer(), userInput.getFtpPort(), userInput.getFtpUser(), userInput.getFtpPass());
            LOG.info("Retrieving failed Tasks");
            for (Document doc : mongo.getFailedTasks()) {
                RunnableTask task = Utils.docToRunnableTask(ftpClient.getFtpClient(), mongo, doc);
                tasks.add(task);
            }
            while (! date.equals(Utils.yesterday())) {
                LOG.info("BEGINNING: " + date);
                Date startTime = Utils.getCurrentDateTime();

                File outputDir = Utils.createNewDir(userInput.getBaseDirectory(), date);

                RemoteFTPServer remoteServer = new RemoteFTPServer(ftpClient.getFtpClient(), mongo, date);
                LOG.info("GETTING FILES AS TASKS");
                tasks.addAll(remoteServer.getFilesAsTasks(outputDir));
                int taskSize = tasks.size();

                ThreadLogic logic = new ThreadLogic(tasks, NUM_WORKERS);
                logic.executeTasks();
                logic.waitForAllThreadsToFinish();

                Date endTime = Utils.getCurrentDateTime();
                mongo.writeFinalizedDay(date, startTime, endTime, taskSize);
                LOG.info("END: " + date);
                date = Utils.sumOneDay(date);
            }
            ftpClient.disconnect();
            mongo.closeConnection();
        }
        catch (IOException e) {
            LOG.error(e);
        }
        catch (InterruptedException e) {
            LOG.error(e);
        }
    }


    public static void main(String[] args) {
        String path = args[0];
        UserInput userInput = new UserInput(path);
        FTPMigratorTool migrator = new FTPMigratorTool(userInput);
        migrator.migrate();
    }
}

