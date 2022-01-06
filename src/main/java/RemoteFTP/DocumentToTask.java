package RemoteFTP;

import org.apache.commons.net.ftp.FTPFile;
import org.bson.Document;

public class DocumentToTask {

    public RemoteFile DocToRemoteFile(Document doc) {
        FTPFile file = null;
        String path =
        RemoteFile remoteFile = new RemoteFile(file, path);
        return remoteFile;
    }
}
