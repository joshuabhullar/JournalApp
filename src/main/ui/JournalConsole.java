package ui;

import model.JournalLogger;
import model.Journal;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents the journal application
public class JournalConsole {
    private Journal journal;
    private JournalLogger log;
    private Scanner input;
    private static final String JSON_STORE = "./data/journalapp.json";
    private JsonReader jsonReader;
    private JsonWriter jsonWriter;

    // EFFECTS: constructs journalapp and runs application
    public JournalConsole() throws FileNotFoundException {
        input = new Scanner(System.in);
        journal = new Journal("My journal");
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runJournal() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        init();

        while (keepGoing) {
            journalDisplay();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("7")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nCya");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            doAddLog();
        } else if (command.equals("2")) {
            doDeleteLog();
        } else if (command.equals("3")) {
            doSearchLog();
        } else if (command.equals("4")) {
            doViewLogs();
        } else if (command.equals("5")) {
            doSaveJournal();
        } else if (command.equals("6")) {
            doLoadJournal();
        } else {
            System.out.println("Invalid input");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes a journal, a list of journal, and log
    private void init() {
        this.journal = new Journal("someone's journal");
        this.log = new JournalLogger("a title", "October 23, 2022", "some entry");
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    // EFFECTS: displays menu of options to user
    private void journalDisplay() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add a log");
        System.out.println("\t2 -> delete a log");
        System.out.println("\t3 -> search for a log");
        System.out.println("\t4 -> view logs");
        System.out.println("\t5 -> save journal");
        System.out.println("\t6 -> load journal");
        System.out.println("\t7 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds logger to journal
    public void doAddLog() {
        log = new JournalLogger("a title", "October 23, 2022", "some entry");
        System.out.println("input a title, date, and entry");
        input.nextLine();
        System.out.println("input a title first:");
        String title = input.nextLine();
        System.out.println("input a date next:");
        String date = input.nextLine();
        System.out.println("input a entry last:");
        String entry = input.nextLine();
        log.setJournalTitle(title);
        log.setJournalDate(date);
        log.setJournalEntry(entry);
        journal.addLog(log);
        System.out.println(" \n" + title + " \n" + date + " \n" + entry);
    }

    // MODIFIES: this
    // EFFECTS: deletes logger from journal
    public void doDeleteLog() {
        System.out.println("input the title of journal log to delete:");
        input.nextLine();
        String deletedLog = input.nextLine();
        journal.deleteLog(deletedLog);
        System.out.println("the entry has been deleted");
    }

    // EFFECTS: searches journal for a log based on title
    //          and prints it out with the journal title,
    //          journal date, and journal entry, else prints
    //          invalid entry
    public void doSearchLog() {
        System.out.println("input the title of journal log to search:");
        input.nextLine();
        String titleOfJournal = input.nextLine();
        if (this.log.equals(journal.searchLog(titleOfJournal))) {
            System.out.println(journal.searchLog(titleOfJournal).getJournalTitle() + " \n"
                    + journal.searchLog(titleOfJournal).getJournalDate() + " \n"
                    + journal.searchLog(titleOfJournal).getJournalEntry());
        } else {
            System.out.println("invalid entry");
        }
    }

    // EFFECTS: prints every journal title added to journal
    public void doViewLogs() {
        System.out.println(journal.getJournalTitles());
    }

    // EFFECTS: saves journal to file
    private void doSaveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            System.out.println("Saved " + journal.getJournalName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void doLoadJournal() {
        try {
            journal = jsonReader.read();
            System.out.println("Loaded " + journal.getJournalName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("unable to read from file: " + JSON_STORE);
        }
    }
}