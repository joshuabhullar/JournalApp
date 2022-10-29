package ui;

import model.JournalLogger;
import model.Journal;

import java.util.Scanner;

// References: inspiration taken from the TellerApp project
//             https://github.students.cs.ubc.ca/CPSC210/TellerApp

public class JournalApp {
    private Journal journal;
    private JournalLogger log;
    private Scanner input;

    // EFFECTS: runs the journal application
    public JournalApp() {
        runJournal();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runJournal() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            journalDisplay();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nCya!");
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
    }

    // MODIFIES: this
    // EFFECTS: adds logger to journal
    public void doAddLog() {
        log = new JournalLogger("a title", "October 23, 2022", "some entry");
        System.out.println("input a title, date, and entry");
        input.nextLine();
        String title = input.nextLine();
        String date = input.nextLine();
        String entry = input.nextLine();
        log.setJournalTitle(title);
        log.setJournalDate(date);
        log.setJournalEntry(entry);
        journal.addLog(log);
        System.out.println(title + " \n" + date + " \n" + entry);
    }

    // MODIFIES: this
    // EFFECTS: deletes logger from journal
    public void doDeleteLog() {
        System.out.println("input the title of journal log to delete");
        input.nextLine();
        String deletedLog = input.nextLine();
        journal.deleteLog(deletedLog);
        System.out.println("the entry has been deleted");
    }

    public void doSearchLog() {
        System.out.println("input the title of journal log to search");
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

    public void doViewLogs() {
        System.out.println(journal.getJournalTitles());
        input.nextLine();
    }
}