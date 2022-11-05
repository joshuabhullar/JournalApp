package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

class JournalTest {
    private JournalLogger log1;
    private JournalLogger log2;
    private JournalLogger log3;
    private Journal journal;
    private ArrayList<String> listOfJournalTitles;
    private ArrayList<JournalLogger> listOfLogs;

    @BeforeEach
    void runBefore() {
        this.log1 = new JournalLogger("some random title", "October 21, 2022",
                "something random happened");
        this.log2 = new JournalLogger("another random title", "October 22, 2022",
                "another random thing happened");
        this.log3 = new JournalLogger("yet another random title", "October 23, 2022",
                "yet another random thing happened");
        this.journal = new Journal("someone's journal");
        this.listOfJournalTitles = new ArrayList<>(Arrays.asList("some random title", "another random title",
                "yet another random title"));
        this.listOfLogs = new ArrayList<>(Arrays.asList(log1, log2, log3));
    }

    @Test
    void testConstructor() {
        assertTrue(journal.isEmpty());
        assertEquals(0, journal.numLogs());
        assertEquals("someone's journal", journal.getJournalName());
        journal.setJournalName("someone else's journal");
        assertEquals("someone else's journal", journal.getJournalName());
    }

    @Test
    void testAddLog() {
        JournalLogger log4 = new JournalLogger("title4", "date4", "entry4");
        JournalLogger log5 = new JournalLogger("title5", "date5", "entry5");
        journal.addLog(log1);
        assertFalse(journal.isEmpty());
        assertEquals(1, journal.numLogs());
        journal.addLog(log2);
        assertEquals(2, journal.numLogs());
        journal.addLog(log3);
        assertEquals(3, journal.numLogs());
        journal.addLog(log4);
        journal.addLog(log5);
        assertEquals(5, journal.numLogs());
        journal.addLog(log1);
        assertEquals(6, journal.numLogs());
    }

    @Test
    void testDeleteLog() {
        journal.addLog(log1);
        assertFalse(journal.isEmpty());
        assertEquals(1, journal.numLogs());
        assertEquals(log1, journal.searchLog("some random title"));
        journal.deleteLog("some random title");
        assertEquals(0, journal.numLogs());
        assertTrue(journal.isEmpty());
        assertEquals(null, journal.searchLog("some random title"));
    }

    @Test
    void testSearchLog() {
        journal.addLog(log1);
        assertFalse(journal.isEmpty());
        assertEquals(1, journal.numLogs());
        assertEquals(log1, journal.searchLog("some random title"));
        journal.addLog(log2);
        journal.addLog(log3);
        assertEquals(3, journal.numLogs());
        assertEquals(log3, journal.searchLog("yet another random title"));
        assertEquals(log2, journal.searchLog("another random title"));
    }

    @Test
    void testViewLogsAndLog() {
        journal.addLog(log1);
        journal.addLog(log2);
        assertFalse(journal.isEmpty());
        assertEquals(2, journal.numLogs());
        assertEquals(log1, journal.viewLog(0));
        assertEquals(log2, journal.viewLog(1));
        journal.addLog(log3);
        assertEquals(3, journal.numLogs());
        assertEquals(log3, journal.viewLog(2));
        assertEquals(listOfLogs, journal.viewLogs());
    }

    @Test
    void testGetJournalTitles() {
        journal.addLog(log1);
        journal.addLog(log2);
        journal.addLog(log3);
        assertFalse(journal.isEmpty());
        assertEquals(3, journal.numLogs());
        assertEquals(listOfJournalTitles, journal.getJournalTitles());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("some random title", log1.getJournalTitle());
        log1.setJournalTitle("some title that is not random");
        assertEquals("some title that is not random", log1.getJournalTitle());
        assertEquals("October 22, 2022", log2.getJournalDate());
        log2.setJournalDate("January 1, 1900");
        assertEquals("January 1, 1900", log2.getJournalDate());
        assertEquals("yet another random thing happened", log3.getJournalEntry());
        log3.setJournalEntry("something very not random happened just now");
        assertEquals("something very not random happened just now", log3.getJournalEntry());
    }
}