package persistence;

import model.Journal;
import model.JournalLogger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Journal j = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyJournal() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyJournal.json");
        try {
            Journal j = reader.read();
            assertEquals("My journal", j.getJournalName());
            assertEquals(0, j.numLogs());
        } catch (IOException e) {
            // fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralJournal() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralJournal.json");
        try {
            Journal j = reader.read();
            assertEquals("My journal", j.getJournalName());
            ArrayList<JournalLogger> journalLogs = j.viewLogs();
            assertEquals(2, journalLogs.size());
            checkLog("something of importance", "October 31, 2022",
                    "a list of cool things", journalLogs.get(1));
            checkLog("yay another title", "November 1, 2022",
                    "descriptive entry", journalLogs.get(0));
        } catch (IOException e) {
            // fail("Couldn't read from file");
        }
    }
}
