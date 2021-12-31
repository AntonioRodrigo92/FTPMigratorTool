package RemoteFTP;

import org.apache.commons.net.ftp.FTPFile;


public class RemoteFile {
    private FTPFile file;
    private String path;

    public RemoteFile(FTPFile file, String path) {
        this.file = file;
        this.path = path;
    }

    public FTPFile getFile() {
        return file;
    }

    public String getAbsolutePath() {
        return path;
    }

    public String getFileName() {
        return file.getName();
    }

    public long getFileSize() {
        return file.getSize();
    }

}
