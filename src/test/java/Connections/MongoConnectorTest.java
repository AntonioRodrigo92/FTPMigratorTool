package Connections;

import Utils.Utils;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.xml.ws.Service;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static sun.rmi.transport.TransportConstants.Return;

@ExtendWith(MockitoExtension.class)
class MongoConnectorTest {

    @Mock
    MongoClient mongoClientMock;
    @Mock
    MongoDatabase databaseMock;
    @Mock
    MongoCollection<Document> finalizedDaysMock;
    @Mock
    MongoCollection<Document> failedDownloadsMock;


    @Test
    void getLastDay() {
//        FindIterable<Document> cursor = finalizedDays.find().sort(
//                new BasicDBObject("consumption_date", -1)
//        ).limit(1);
//        Document lastDay = cursor.iterator().next();
//        Date date = (Date) lastDay.get("consumption_date");
//        return Utils.dateToLocalDate(date);

        FindIterable iterableMock = mock(FindIterable.class);
        Document docMock = new Document("_id", new ObjectId(""))
                .append("", "")
                .append("", "");



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