package Connections;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class FTPClientConnector {
    private FTPClient ftpClient;

    public FTPClientConnector() {
        this.ftpClient = new FTPClient();
    }

    public void connect(String server, int port, String user, String pass)  throws IOException  {
        ftpClient.connect(server, port);
        ftpClient.login(user, pass);
    }

    public void disconnect() throws IOException {
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }
}