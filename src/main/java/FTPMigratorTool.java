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
            LOG.info("RETRIEVING FAILED TASKS");
            for (Document doc : mongo.getFailedTasks()) {
                RunnableTask task = Utils.docToRunnableTask(ftpClient.getFtpClient(), mongo, doc);
                tasks.add(task);
            }
            int taskSize = tasks.size();
            if (taskSize > 0) {
                LOG.warn("RETRIEVED " + taskSize + " FAILED DOCUMENTS");
            }
            while (! date.equals(Utils.today())) {
                LOG.info("BEGINNING: " + date);
                Date startTime = Utils.getCurrentDateTime();

                File outputDir = Utils.createNewDir(userInput.getBaseDirectory(), date);

                RemoteFTPServer remoteServer = new RemoteFTPServer(ftpClient.getFtpClient(), mongo, date);
                LOG.info("GETTING FILES AS TASKS");
                tasks.addAll(remoteServer.getFilesAsTasks(outputDir));
                taskSize = tasks.size();
                LOG.info("PROCESSING " + taskSize + " FILES");

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
            LOG.info("MIGRATIONS ARE UP-TO-DATE");
        }
        catch (IOException e) {
            LOG.error("FTPMigrator: IOException");
            LOG.error(e);
        }
        catch (InterruptedException e) {
            LOG.error("FTPMigrator: InterruptedException");
            LOG.error(e);
        }
        catch (Exception e) {
            LOG.error("FTPMigrator: GeneralException");
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

