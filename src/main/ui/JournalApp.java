package ui;

import model.Journal;
import model.JournalLogger;
import persistence.JsonWriter;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Reference: references used for each method
//            stated above each method respectively
// Represents the GUI for the project
public class JournalApp extends JFrame {
    private Journal journal;
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 800;
    private static final int SPACING_HEIGHT = 25;
    private final JPanel mainFrame = new JPanel();
    private DefaultListModel<String> defaultListModel;
    private JList<String> someRandomList;
    private JTextField journalTitleField;
    private JTextField journalDateField;
    private JTextArea journalEntryField;
    private JLabel journalTitleLabel;
    private JLabel journalDateLabel;
    private JLabel journalEntryLabel;
    private JLabel loadedPhrase;
    private JLabel savedPhrase;
    private static final String JSON_STORE = "./data/journalapp.json";
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;

    // MODIFIES: journal, jsonWriter, jsonReader
    // EFFECTS: creates GUI and its components
    public JournalApp() {
        this.journal = new Journal("someone's journal");
        this.jsonReader = new JsonReader(JSON_STORE);
        this.jsonWriter = new JsonWriter(JSON_STORE);
        setTitle("JournalApp");
        add(mainFrame);
        setVisible(true);
        mainFrame.setLayout(null);
        setJMenuBar(makeMenu());
        Dimension size = new Dimension(WIDTH, HEIGHT);
        setMinimumSize(size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addVisuals();
    }

    // MODIFIES: private fields
    // EFFECTS: creates components
    private void addVisuals() {
        addAddJournalLabels();
        addAddJournalEntryButton();
        makeImage();
        makeFields();
        addDeleteJournalEntryButton();
        addHeaders();
        createJournalDetails();
        addVisualLabels();
        addJournalEntryLabels();
        createEmptyJournalList();
    }

    // EFFECTS: create menu bar for gui
    public JMenuBar makeMenu() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        menuBar = new JMenuBar();
        menu = new JMenu("Journal");
        menuBar.add(menu);
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(e -> doSaveJournal());
        menu.add(menuItem);
        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(e -> doLoadJournal());
        menu.add(menuItem);
        return menuBar;
    }

    // MODIFIES: Journal journal
    // EFFECTS: creates a journal list, ready for logs/entries to be added,
    //          then rerenders frame
    private void doAdd() {
        JournalLogger journalList = new JournalLogger("default title", "default date",
                "default entry");
        journalList.setJournalTitle(journalTitleField.getText());
        journalList.setJournalDate(journalDateField.getText());
        journalList.setJournalEntry(journalEntryField.getText());
        journal.addLog(journalList);
        journalTitleField.setText(" ");
        journalDateField.setText(" ");
        journalEntryField.setText(" ");
        updateJournalList();
    }

    // MODIFIES: Journal journal
    // EFFECTS: deletes journal
    private void doDelete() {
        String clicked = journal.getJournalName();
        journal.deleteLog(clicked);
        updateJournalList();
        clearJournalDetails();
    }

    //            Also used ListDemo when implementing list related things such as
    //            DefaultListModel and JList
    // MODIFIES: this
    // EFFECTS: updates the list of logs
    private void updateJournalList() {
        DefaultListModel<String> newListModel = new DefaultListModel<>();
        for (JournalLogger log : journal.viewLogs()) {
            newListModel.addElement(log.getJournalTitle());
        }
        defaultListModel = newListModel;
        someRandomList.setModel(defaultListModel);
    }

    //            Used for implementation of listScrollPane
    //            https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html
    //            Used for event handling
    // MODIFIES: this
    // EFFECTS: creates empty journal list
    private void createEmptyJournalList() {
        defaultListModel = new DefaultListModel<>();
        someRandomList = new JList<>(defaultListModel);
        someRandomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        someRandomList.setSelectedIndex(-1);
        someRandomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (someRandomList.getSelectedIndex() > -1) {
                    journal.setJournalName(defaultListModel.getElementAt(someRandomList.getSelectedIndex()));
                    updateJournalDetails(defaultListModel.getElementAt(someRandomList.getSelectedIndex()));
                }
            }
        });
        someRandomList.setVisibleRowCount(3);
        JScrollPane listScrollPane = new JScrollPane(someRandomList);
        listScrollPane.setBounds(385,100,200,100);
        mainFrame.add(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: creates text fields
    private void makeFields() {
        journalTitleField = new JTextField();
        journalTitleField.setBounds(200, SPACING_HEIGHT * 4, 100, SPACING_HEIGHT);
        journalDateField = new JTextField();
        journalDateField.setBounds(200, SPACING_HEIGHT * 5, 100, SPACING_HEIGHT);
        journalEntryField = new JTextArea("");
        journalEntryField.setBounds(200, SPACING_HEIGHT * 6, 100, 200);
        mainFrame.add(journalTitleField);
        mainFrame.add(journalDateField);
        mainFrame.add(journalEntryField);
    }

    // MODIFIES: this
    // EFFECTS: creates journal details text ready to be filled in
    private void createJournalDetails() {
        journalTitleLabel = new JLabel("");
        journalTitleLabel.setBounds(750, SPACING_HEIGHT * 4, 100, SPACING_HEIGHT);
        journalDateLabel = new JLabel("");
        journalDateLabel.setBounds(750, SPACING_HEIGHT * 5, 100, SPACING_HEIGHT);
        journalEntryLabel = new JLabel("");
        journalEntryLabel.setBounds(750, SPACING_HEIGHT * 6, 200, SPACING_HEIGHT);
        mainFrame.add(journalTitleLabel);
        mainFrame.add(journalDateLabel);
        mainFrame.add(journalEntryLabel);
    }

    // MODIFIES: this
    // EFFECTS: updates journal details
    private void updateJournalDetails(String journalTitle) {
        JournalLogger clicked = journal.searchLog(journalTitle);
        journalTitleLabel.setText(clicked.getJournalTitle());
        journalDateLabel.setText(clicked.getJournalDate());
        journalEntryLabel.setText(clicked.getJournalEntry());
    }

    // MODIFIES: this
    // EFFECTS: clears journal details
    private void clearJournalDetails() {
        journalTitleField.setText(" ");
        journalDateField.setText(" ");
        journalEntryField.setText(" ");
        repaint();
    }

    // MODIFIES: this
    // EFFECTS: creates journal labels
    private void addJournalEntryLabels() {
        JLabel journalTitleLabel = new JLabel("Title:");
        journalTitleLabel.setBounds(700, SPACING_HEIGHT * 4, 100, SPACING_HEIGHT);
        mainFrame.add(journalTitleLabel);
        JLabel journalDateLabel = new JLabel("Date:");
        journalDateLabel.setBounds(700, SPACING_HEIGHT * 5, 100, SPACING_HEIGHT);
        mainFrame.add(journalDateLabel);
        JLabel journalEntryLabel = new JLabel("Entry:");
        journalEntryLabel.setBounds(700, SPACING_HEIGHT * 6, 100, SPACING_HEIGHT);
        mainFrame.add(journalEntryLabel);
    }

    // MODIFIES: this
    // EFFECTS: adds labels for creating journal logs
    private void addAddJournalLabels() {
        JLabel journalTitleLabel = new JLabel("Title:");
        journalTitleLabel.setBounds(165,SPACING_HEIGHT * 4,100,SPACING_HEIGHT);
        mainFrame.add(journalTitleLabel);
        JLabel journalDateLabel = new JLabel("Date:");
        journalDateLabel.setBounds(165,SPACING_HEIGHT * 5,100,SPACING_HEIGHT);
        mainFrame.add(journalDateLabel);
        JLabel journalEntryLabel = new JLabel("Entry:");
        journalEntryLabel.setBounds(165,SPACING_HEIGHT * 6,100,SPACING_HEIGHT);
        mainFrame.add(journalEntryLabel);
    }

    // MODIFIES: this
    // EFFECTS: header text for the journal
    private void addHeaders() {
        JLabel journalViewerHeader = new JLabel("Log Details:");
        journalViewerHeader.setBounds(700,SPACING_HEIGHT * 2,250,25);
        journalViewerHeader.setFont(new Font("Arial", Font.PLAIN, 18));
        journalViewerHeader.setBackground(Color.decode("#FFCCCB"));
        mainFrame.add(journalViewerHeader);
        JLabel journalLoggerHeader = new JLabel("Enter Journal Log:");
        journalLoggerHeader.setBounds(175,SPACING_HEIGHT * 2,250,25);
        journalLoggerHeader.setFont(new Font("Arial", Font.PLAIN, 18));
        journalLoggerHeader.setBackground(Color.decode("#FFCCCB"));
        mainFrame.add(journalLoggerHeader);
    }

    // MODIFIES: this
    // EFFECTS: create journal log button
    private void addAddJournalEntryButton() {
        JButton createButton = new JButton("Create new log");
        createButton.setBounds(150, HEIGHT - 300, 300, 80);
        createButton.setFont(new Font("Arial", Font.PLAIN, 18));
        createButton.setBackground(Color.decode("#ADD8E6"));
        createButton.addActionListener(e -> doAdd());
        mainFrame.add(createButton);
    }

    // MODIFIES: this
    // EFFECTS: delete button to delete journal log
    private void addDeleteJournalEntryButton() {
        JButton createButton = new JButton("Delete this log");
        createButton.setBounds(600, HEIGHT - 300, 300, 80);
        createButton.setFont(new Font("Arial", Font.PLAIN, 18));
        createButton.setBackground(Color.decode("#FFCCCB"));
        createButton.addActionListener(e -> doDelete());
        mainFrame.add(createButton);
    }

    // EFFECTS: puts image into journalapp
    protected ImageIcon makeImageIcon() {
        java.net.URL imgURL = JournalApp.class.getResource("images/journal.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + "images/journal.png");
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds image
    private void makeImage() {
        JLabel image = new JLabel();
        image.setIcon(makeImageIcon());
        image.setBounds(385, 230, 300, 300);
        mainFrame.add(image);
    }

    // MODIFIES: this
    // EFFECTS: notice of successful save and load
    private void addVisualLabels() {
        savedPhrase = new JLabel("Saved!");
        savedPhrase.setBounds(480,150,250,250);
        savedPhrase.setVisible(false);
        loadedPhrase = new JLabel("Loaded!");
        loadedPhrase.setBounds(480,150,250,250);
        loadedPhrase.setVisible(false);
        mainFrame.add(savedPhrase);
        mainFrame.add(loadedPhrase);
    }

    // EFFECTS: saves journal to file
    private void doSaveJournal() {
        try {
            jsonWriter.open();
            jsonWriter.write(journal);
            jsonWriter.close();
            savedPhrase.setVisible(true);
            Timer timer = new Timer(1000, y -> savedPhrase.setVisible(false));
            timer.setRepeats(false);
            timer.start();
            repaint();
        } catch (FileNotFoundException e) {
            System.out.println("unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void doLoadJournal() {
        try {
            journal = jsonReader.read();
            loadedPhrase.setVisible(true);
            Timer timer = new Timer(1000, y -> loadedPhrase.setVisible(false));
            timer.setRepeats(false);
            timer.start();
            updateJournalList();
            repaint();
        } catch (IOException e) {
            System.out.println("unable to read from file: " + JSON_STORE);
        }
    }
}