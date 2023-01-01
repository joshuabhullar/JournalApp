package persistence;

import model.Journal;
import model.JournalLogger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads a journal from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads diary from file and returns it
    // throws IOException if an error occurs reading date from file
    public Journal read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseJournal(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses journal from JSON object and returns it
    private Journal parseJournal(JSONObject jsonObject) {
        String journalName = jsonObject.getString("journalName");
        Journal j = new Journal(journalName);
        addLogs(j, jsonObject);
        return j;
    }

    // MODIFIES: journal
    // EFFECTS: parses logs from JSON object and adds them to journal
    private void addLogs(Journal j, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("logs");
        for (Object json : jsonArray) {
            JSONObject nextLog = (JSONObject) json;
            addLog(j, nextLog);
        }
    }

    // MODIFIES: journal
    // EFFECTS: parses entry from JSON object and adds it to the journal
    private void addLog(Journal j, JSONObject jsonObject) {
        String logTitle = jsonObject.getString("logTitle");
        String logDate = jsonObject.getString("logDate");
        String logEntry = jsonObject.getString("logEntry");
        JournalLogger log = new JournalLogger(logTitle, logDate, logEntry);
        j.addLog(log);
    }



}
