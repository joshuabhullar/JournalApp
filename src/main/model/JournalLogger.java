package model;

import org.json.JSONObject;
import persistence.Writable;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a journal log with a journal title, journal date, and journal entry
public class JournalLogger implements Writable {
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("logTitle", journalTitle);
        json.put("logDate", journalDate);
        json.put("logEntry", journalEntry);
        return json;
    }

}
