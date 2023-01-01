package persistence;

import model.Journal;
import model.JournalLogger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Journal logs = new Journal("My journal");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyJournal() {
        try {
            Journal j = new Journal("My journal");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyJournal.json");
            writer.open();
            writer.write(j);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyJournal.json");
            j = reader.read();
            assertEquals("My journal", j.getJournalName());
            assertEquals(0, j.numLogs());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralJournal() {
        try {
            Journal j = new Journal("My journal");
            j.addLog(new JournalLogger("something of importance", "October 31, 2022",
                    "a list of cool things"));
            j.addLog(new JournalLogger("yay another title", "November 1, 2022",
                    "descriptive entry"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralJournal.json");
            writer.open();
            writer.write(j);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralJournal.json");
            j = reader.read();
            assertEquals("My journal", j.getJournalName());
            ArrayList<JournalLogger> journalLogs = j.viewLogs();
            assertEquals(2, journalLogs.size());
            checkLog("something of importance", "October 31, 2022",
                    "a list of cool things", journalLogs.get(0));
            checkLog("yay another title", "November 1, 2022",
                    "descriptive entry", journalLogs.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}