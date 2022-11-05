package ui;

import java.io.FileNotFoundException;

// References: inspiration taken from the JsonSerializationDemo project
//             https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class Main {
    public static void main(String[] args) {
        try {
            new JournalApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
