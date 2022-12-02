package ui;

import model.Event;
import model.EventLog;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class Main {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                for (Event next : EventLog.getInstance()) {
                    System.out.println(next);
                }
            }
        });
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JournalApp();
            }
        });
    }
}
