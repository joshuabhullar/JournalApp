package model;

// References: inspiration taken from the TellerApp project
//             https://github.students.cs.ubc.ca/CPSC210/TellerApp

// Represents a journal log with a journal title, journal date, and journal entry
public class JournalLogger {
    private String journalTitle;
    private String journalDate;
    private String journalEntry;

    // EFFECTS: creates a journal log with a title, date, and entry
    public JournalLogger(String journalTitle, String journalDate, String journalEntry) {
        this.journalTitle = journalTitle;
        this.journalDate = journalDate;
        this.journalEntry = journalEntry;
    }

    public String getJournalTitle() {
        return this.journalTitle;
    }

    public void setJournalTitle(String journalTitle) {
        this.journalTitle = journalTitle;
    }

    public String getJournalDate() {
        return this.journalDate;
    }

    public void setJournalDate(String journalDate) {
        this.journalDate = journalDate;
    }

    public String getJournalEntry() {
        return this.journalEntry;
    }

    public void setJournalEntry(String journalEntry) {
        this.journalEntry = journalEntry;
    }
}
