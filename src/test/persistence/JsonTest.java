package persistence;

import model.JournalLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkLog(String journalTitle, String journalDate, String journalEntry, JournalLogger log) {
        assertEquals(journalTitle, log.getJournalTitle());
        assertEquals(journalDate, log.getJournalDate());
        assertEquals(journalEntry, log.getJournalEntry());
    }
}
