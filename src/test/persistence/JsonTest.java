package persistence;

import model.JournalLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkLog(String journalTitle, String journalDate, String journalEntry, JournalLogger log) {
        assertEquals(journalTitle, log.getJournalTitle());
        assertEquals(journalDate, log.getJournalDate());
        assertEquals(journalEntry, log.getJournalEntry());
    }
}
