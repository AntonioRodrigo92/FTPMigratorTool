package Connections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.ws.Service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoConnectorTest {

    @Mock
    MongoConnector mongoConnectorMock;

    @Test
    public void testQuery()  {
        assertNotNull(mongoConnectorMock);
    }

    @Test
    void getLastDay() {
        LocalDate localDateTest = LocalDate.of(2022, 1, 15);
        when(mongoConnectorMock.getLastDay()).thenReturn(localDateTest);


    }

    @Test
    void writeFinalizedDay() {
    }

    @Test
    void getFailedTasks() {
    }

    @Test
    void writeFailedDownload() {
    }

    @Test
    void removeFromFailedDownloads() {
    }

    @Test
    void closeConnection() {
    }
}