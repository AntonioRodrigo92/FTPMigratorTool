package Connections;

import org.apache.commons.net.ftp.FTPClient;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FTPClientConnectorTest {

    @Mock
    FTPClient ftpClientMock;
    @Mock
    FTPClientConnector ftpClientConnectorMock;

    @Test
    void connect() {
    }

    @Test
    void disconnect() {
    }

    @Test
    void getFtpClient() {
    }
}