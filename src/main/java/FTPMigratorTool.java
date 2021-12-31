import Connections.FTPClientConnector;
import RemoteFTP.RemoteFTPServer;
import RemoteFTP.RemoteFile;
import TaskHandler.RunnableTask;
import TaskHandler.ThreadLogic;
import Utils.Utils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
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
    private Date date;

    public FTPMigratorTool(String server, int port, String user, String pass) {
        this.server = server;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.tasks = new LinkedList<>();
//        this.mongo = new MongoConnector(tasks);
        this.ftpClient = new FTPClientConnector();
//        this.date = mongo.getLastDay();


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, 11);
        cal.set(Calendar.DAY_OF_MONTH, 4);

        date = cal.getTime();
    }

    private void migrate() {
        try {
            ftpClient.connect(server, port, user, pass);
            File outputDir = Utils.createNewDir(BASE_DIRECTORY, date);

            System.out.println("CONNECTED");

            RemoteFTPServer remoteServer = new RemoteFTPServer(ftpClient.getFtpClient(), date);
            tasks.addAll(remoteServer.getFilesAsTasks(outputDir));

            System.out.println("THREADS A COMEÇAR");
            ThreadLogic logic = new ThreadLogic(tasks, NUM_WORKERS);
            logic.executeTasks();
            logic.waitForAllThreadsToFinish();
            System.out.println("THREADS ACABARAM");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        FTPMigratorTool migrator = new FTPMigratorTool("192.168.1.1", 21, "sandisk16gb", "PalavraPasse1");
        migrator.migrate();

//        Calendar date = Calendar.getInstance();
//        date.set(Calendar.YEAR, 2021);
//        date.set(Calendar.MONTH, 11);
//        date.set(Calendar.DAY_OF_MONTH, 4);
//        Date d = date.getTime();
//
//        Calendar date2 = Calendar.getInstance();
//        date2.set(Calendar.YEAR, 2021);
//        date2.set(Calendar.MONTH, 11);
//        date2.set(Calendar.DAY_OF_MONTH, 4);
//        Date d1 = date.getTime();
//
//        System.out.println(d.compareTo(d1));
//        System.out.println(d.equals(d1));
    }
}

