package TaskHandler;

import Connections.MongoConnector;
import RemoteFTP.RemoteFile;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RunnableTaskTest {

    @Mock
    FTPClient ftpClientMock;
    @Mock
    MongoConnector mongoConnectorMock;
    @Mock
    RemoteFile remoteFileMock;
    @Mock
    File destinyDirectoryMock;

    @Test
    void run() {

    }
}