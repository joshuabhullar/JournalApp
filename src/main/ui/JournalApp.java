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
    private Dimension size = new Dimension(WIDTH, HEIGHT);
    private JPanel mainFrame = new JPanel();
    private DefaultListModel defaultListModel;
    private JList someRandomList;
    private JTextField journaltitleField;
    private JTextField journaldateField;
    private JTextArea journalentryField;
    private JLabel journaltitleLabel;
    private JLabel journaldateLabel;
    private JLabel journalentryLabel;
    private JLabel loadedPhrase;
    private JLabel savedPhrase;
    private static final String JSON_STORE = "./data/journalapp.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // References: https://docs.oracle.com/javase/tutorial/uiswing/components/panel.html
    //             used throughout project for implementation of mainFrame
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
        setMinimumSize(size);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addVisuals();
    }

    // Reference: Used simpledrawingplayer to compress
    //            this method into a bunch of different methods
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

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/menu.html
    //            and MenuDemo
    // EFFECTS: create menu bar for gui
    public JMenuBar makeMenu() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;
        menuBar = new JMenuBar();
        menu = new JMenu("Journal");
        menuBar.add(menu);
        menuItem = new JMenuItem("Save");
        menuItem.addActionListener(e -> {
            doSaveJournal();
        });
        menu.add(menuItem);
        menuItem = new JMenuItem("Load");
        menuItem.addActionListener(e -> {
            doLoadJournal();
        });
        menu.add(menuItem);
        return menuBar;
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
    // MODIFIES: Journal journal
    // EFFECTS: creates a journal list, ready for logs/entries to be added,
    //          then rerenders frame
    private void doAdd() {
        JournalLogger journalList = new JournalLogger("default title", "default date",
                "default entry");
        journalList.setJournalTitle(journaltitleField.getText());
        journalList.setJournalDate(journaldateField.getText());
        journalList.setJournalEntry(journalentryField.getText());
        journal.addLog(journalList);
        journaltitleField.setText(" ");
        journaldateField.setText(" ");
        journalentryField.setText(" ");
        updateJournalList();
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
    // MODIFIES: Journal journal
    // EFFECTS: deletes journal
    private void doDelete() {
        String clicked = journal.getJournalName();
        journal.deleteLog(clicked);
        updateJournalList();
        clearJournalDetails();
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/list.html
    //            Also used ListDemo when implementing list related things such as
    //            DefaultListModel and JList
    // MODIFIES: this
    // EFFECTS: updates the list of logs
    private void updateJournalList() {
        DefaultListModel newListModel = new DefaultListModel();
        for (JournalLogger log : journal.viewLogs()) {
            newListModel.addElement(log.getJournalTitle());
        }
        defaultListModel = newListModel;
        someRandomList.setModel(defaultListModel);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
    //            Used for implementation of listScrollPane
    //            https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html
    //            Used for event handling
    // MODIFIES: this
    // EFFECTS: creates empty journal list
    private void createEmptyJournalList() {
        defaultListModel = new DefaultListModel();
        someRandomList = new JList(defaultListModel);
        someRandomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        someRandomList.setSelectedIndex(-1);
        someRandomList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                if (someRandomList.getSelectedIndex() > -1) {
                    journal.setJournalName(defaultListModel.getElementAt(someRandomList.getSelectedIndex()).toString());
                    updateJournalDetails(defaultListModel.getElementAt(someRandomList.getSelectedIndex()).toString());
                }
            }
        });
        someRandomList.setVisibleRowCount(3);
        JScrollPane listScrollPane = new JScrollPane(someRandomList);
        listScrollPane.setBounds(385,100,200,100);
        mainFrame.add(listScrollPane);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/textfield.html
    //            and TextDemo
    // MODIFIES: this
    // EFFECTS: creates text fields
    private void makeFields() {
        journaltitleField = new JTextField();
        journaltitleField.setBounds(200, SPACING_HEIGHT * 4, 100, SPACING_HEIGHT);
        journaldateField = new JTextField();
        journaldateField.setBounds(200, SPACING_HEIGHT * 5, 100, SPACING_HEIGHT);
        journalentryField = new JTextArea("");
        journalentryField.setBounds(200, SPACING_HEIGHT * 6, 100, 200);
        mainFrame.add(journaltitleField);
        mainFrame.add(journaldateField);
        mainFrame.add(journalentryField);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/label.html
    //            and LabelDemo
    // MODIFIES: this
    // EFFECTS: creates journal details text ready to be filled in
    private void createJournalDetails() {
        journaltitleLabel = new JLabel("");
        journaltitleLabel.setBounds(750, SPACING_HEIGHT * 4, 100, SPACING_HEIGHT);
        journaldateLabel = new JLabel("");
        journaldateLabel.setBounds(750, SPACING_HEIGHT * 5, 100, SPACING_HEIGHT);
        journalentryLabel = new JLabel("");
        journalentryLabel.setBounds(750, SPACING_HEIGHT * 6, 200, SPACING_HEIGHT);
        mainFrame.add(journaltitleLabel);
        mainFrame.add(journaldateLabel);
        mainFrame.add(journalentryLabel);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/generaltext.html
    //            and TextComponentDemo
    // MODIFIES: this
    // EFFECTS: updates journal details
    private void updateJournalDetails(String journalTitle) {
        JournalLogger clicked = journal.searchLog(journalTitle);
        journaltitleLabel.setText(clicked.getJournalTitle());
        journaldateLabel.setText(clicked.getJournalDate());
        journalentryLabel.setText(clicked.getJournalEntry());
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/generaltext.html
    //            and TextComponentDemo
    // MODIFIES: this
    // EFFECTS: clears journal details
    private void clearJournalDetails() {
        journaltitleField.setText(" ");
        journaldateField.setText(" ");
        journalentryField.setText(" ");
        repaint();
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/label.html
    //            and LabelDemo
    // MODIFIES: this
    // EFFECTS: creates journal labels
    private void addJournalEntryLabels() {
        JLabel journaltitleLabel = new JLabel("Title:");
        journaltitleLabel.setBounds(700, SPACING_HEIGHT * 4, 100, SPACING_HEIGHT);
        mainFrame.add(journaltitleLabel);
        JLabel journaldateLabel = new JLabel("Date:");
        journaldateLabel.setBounds(700, SPACING_HEIGHT * 5, 100, SPACING_HEIGHT);
        mainFrame.add(journaldateLabel);
        JLabel journalentryLabel = new JLabel("Entry:");
        journalentryLabel.setBounds(700, SPACING_HEIGHT * 6, 100, SPACING_HEIGHT);
        mainFrame.add(journalentryLabel);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/label.html
    //            and LabelDemo
    // MODIFIES: this
    // EFFECTS: adds labels for creating journal logs
    private void addAddJournalLabels() {
        JLabel journaltitleLabel = new JLabel("Title:");
        journaltitleLabel.setBounds(165,SPACING_HEIGHT * 4,100,SPACING_HEIGHT);
        mainFrame.add(journaltitleLabel);
        JLabel journaldateLabel = new JLabel("Date:");
        journaldateLabel.setBounds(165,SPACING_HEIGHT * 5,100,SPACING_HEIGHT);
        mainFrame.add(journaldateLabel);
        JLabel journalentryLabel = new JLabel("Entry:");
        journalentryLabel.setBounds(165,SPACING_HEIGHT * 6,100,SPACING_HEIGHT);
        mainFrame.add(journalentryLabel);
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

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
    //            and ButtonDemo
    // MODIFIES: this
    // EFFECTS: create journal log button
    private void addAddJournalEntryButton() {
        JButton createButton = new JButton("Create new log");
        createButton.setBounds(150, HEIGHT - 300, 300, 80);
        createButton.setFont(new Font("Arial", Font.PLAIN, 18));
        createButton.setBackground(Color.decode("#ADD8E6"));
        createButton.addActionListener(e -> {
            doAdd();
        });
        mainFrame.add(createButton);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
    //            and ButtonDemo
    // MODIFIES: this
    // EFFECTS: delete button to delete journal log
    private void addDeleteJournalEntryButton() {
        JButton createButton = new JButton("Delete this log");
        createButton.setBounds(600, HEIGHT - 300, 300, 80);
        createButton.setFont(new Font("Arial", Font.PLAIN, 18));
        createButton.setBackground(Color.decode("#FFCCCB"));
        createButton.addActionListener(e -> {
            doDelete();
        });
        mainFrame.add(createButton);
    }

    // Reference: https://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
    //            and IconDemo
    // EFFECTS: puts image into journalapp
    protected ImageIcon makeImageIcon(String path) {
        java.net.URL imgURL = JournalApp.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // MODIFIES: this
    // EFFECTS: adds image
    private void makeImage() {
        JLabel image = new JLabel();
        image.setIcon(makeImageIcon("images/journal.png"));
        image.setBounds(385, 230, 300, 300);
        mainFrame.add(image);
    }

    // MODIFIES: this
    // EFFECTS: notice of successful save and load
    private void addVisualLabels() {
        savedPhrase = new JLabel("Saved!");
        savedPhrase.setBounds(500,230,250,250);
        savedPhrase.setVisible(false);
        loadedPhrase = new JLabel("Loaded!");
        loadedPhrase.setBounds(500,230,250,250);
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
            Timer timer = new Timer(1000, y -> {
                savedPhrase.setVisible(false);
            });
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
            Timer timer = new Timer(1000, y -> {
                loadedPhrase.setVisible(false);
            });
            timer.setRepeats(false);
            timer.start();
            updateJournalList();
            repaint();
        } catch (IOException e) {
            System.out.println("unable to read from file: " + JSON_STORE);
        }
    }
}