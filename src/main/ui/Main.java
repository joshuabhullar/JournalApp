package ui;

import model.Event;
import model.EventLog;

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
