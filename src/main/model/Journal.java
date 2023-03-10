package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a journal with a name and empty list of journal logs
public class Journal implements Writable {
    private ArrayList<JournalLogger> logs;
    private ArrayList<String> journalTitles;
    private String journalName;

    // EFFECTS: creates a new journal with a name and empty list of logs
    public Journal(String journalName) {
        this.logs = new ArrayList<>();
        this.journalTitles = new ArrayList<>();
        this.journalName = journalName;
    }

    // MODIFIES: this
    // EFFECTS: adds a journal log to the list of journal logs
    public void addLog(JournalLogger log) {
        this.logs.add(log);
        EventLog.getInstance().logEvent(new Event("Journal log " + log.getJournalTitle() + " was added to "
                + "the journal"));
    }

    // MODIFIES: this
    // EFFECTS: removes a journal log by using searchLog to find the journal name to remove from list of journal logs
    public void deleteLog(String journalName) {
        String removedLogTitle = searchLog(journalName).getJournalTitle();
        this.logs.remove(searchLog(journalName));
        EventLog.getInstance().logEvent(new Event("Journal log " + removedLogTitle
                + " was deleted from the journal"));
    }

    // EFFECTS: searches for a journal log based on the journal log's title
    //          returns the journal log if found, else returns null
    public JournalLogger searchLog(String journalName) {
        for (JournalLogger log : logs) {
            if (journalName.equals(log.getJournalTitle())) {
                return log;
            }
        }
        return null;
    }

    // EFFECTS: returns logs added to the list of journal logs
    public ArrayList<JournalLogger> viewLogs() {
        return this.logs;
    }

    // EFFECTS: returns a log added to the list of journal logs based on its index in the arraylist
    public JournalLogger viewLog(int index) {
        return this.logs.get(index);
    }

    // MODIFIES: this
    // EFFECTS: gets journal titles from all the logs and puts them into a list
    public ArrayList<String> getJournalTitles() {
        journalTitles = new ArrayList<>();
        for (JournalLogger log : logs) {
            this.journalTitles.add(log.getJournalTitle());
        }
        return this.journalTitles;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String name) {
        this.journalName = name;
    }

    // EFFECTS: returns the number of logs in the journal
    public int numLogs() {
        return this.logs.size();
    }

    // EFFECTS: returns true if logs is empty, else false
    public boolean isEmpty() {
        return this.logs.size() == 0;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("journalName", journalName);
        json.put("logs", logsToJson());
        return json;
    }

    // EFFECTS: returns entries in the diary as a JSON array
    private JSONArray logsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (JournalLogger l : logs) {
            jsonArray.put(l.toJson());
        }
        return jsonArray;
    }
}
