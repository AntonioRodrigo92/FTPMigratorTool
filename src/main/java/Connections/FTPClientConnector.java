package Connections;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class FTPClientConnector {
    private FTPClient ftpClient;
    private final Logger LOG = LogManager.getLogger();


    public FTPClientConnector() {
        this.ftpClient = new FTPClient();
    }

    public void connect(String server, int port, String user, String pass)  throws IOException  {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);

        LOG.info("Connected to FTPClient successfully!");
    }

    public void disconnect() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();

        LOG.info("Disconnected from FTPClient successfully!");
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }
}