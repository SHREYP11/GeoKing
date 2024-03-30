import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GUILeaderboardScreen extends JPanel {
    private JTable classicLeaderboardTable;
    private JTable frenzyLeaderboardTable;
    private SoundPlayer clicker = new SoundPlayer();

    public GUILeaderboardScreen() {
        SoundPlayer clicker = new SoundPlayer();
        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192)); // Set background color for LeaderboardScreen

        // Create top panel for back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(192, 192, 192)); // Set background color for topPanel
        JButton backButton = new JButton("Back");
        topPanel.add(backButton);
        add(topPanel, BorderLayout.NORTH);
        backButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            // Add the main menu card panel to the main menu card panel container
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "MAIN_MENU");
            revalidate();
            repaint();
        });

        // Read user data from userFile.csv
        ArrayList<UserData> userData = readUserData("userFile.csv");

        // Create panel for leaderboard tables
        JPanel leaderboardPanel = new JPanel(new GridLayout(2, 1));
        leaderboardPanel.setBackground(new Color(192, 192, 192)); // Set background color for leaderboardPanel

        // Create classic leaderboard table
        JPanel classicPanel = new JPanel(new BorderLayout());
        classicPanel.setBackground(new Color(192, 192, 192)); // Set background color for classicPanel
        JLabel classicLabel = new JLabel("Classic Leaderboard", SwingConstants.CENTER);
        classicLabel.setFont(new Font(classicLabel.getFont().getName(), Font.PLAIN, 30));
        classicPanel.add(classicLabel, BorderLayout.NORTH);
        classicLeaderboardTable = new JTable();
        updateClassicLeaderboard(userData);
        classicPanel.add(new JScrollPane(classicLeaderboardTable), BorderLayout.CENTER);
        leaderboardPanel.add(classicPanel);

        // Create frenzy leaderboard table
        JPanel frenzyPanel = new JPanel(new BorderLayout());
        frenzyPanel.setBackground(new Color(192, 192, 192)); // Set background color for frenzyPanel
        JLabel frenzyLabel = new JLabel("Frenzy Leaderboard", SwingConstants.CENTER);
        frenzyLabel.setFont(new Font(frenzyLabel.getFont().getName(), Font.PLAIN, 30)); // Set font size
        frenzyPanel.add(frenzyLabel, BorderLayout.NORTH);
        frenzyLeaderboardTable = new JTable();
        updateFrenzyLeaderboard(userData);
        frenzyPanel.add(new JScrollPane(frenzyLeaderboardTable), BorderLayout.CENTER);
        leaderboardPanel.add(frenzyPanel);

        // Add leaderboard panel to the center
        add(leaderboardPanel, BorderLayout.CENTER);

        // Set the preferred size of the tables to be equal
        Dimension tablePreferredSize = classicLeaderboardTable.getPreferredSize();
        frenzyLeaderboardTable.setPreferredScrollableViewportSize(tablePreferredSize);
    }

    // Method to read user data from userFile.csv
    private ArrayList<UserData> readUserData(String filename) {
        ArrayList<UserData> userData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String name = values[0].trim();
                int classicLevel = Integer.parseInt(values[1].trim());
                int frenzyLevel = Integer.parseInt(values[2].trim());
                userData.add(new UserData(name, classicLevel, frenzyLevel));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userData;
    }

    // Method to update classic leaderboard table
    private void updateClassicLeaderboard(ArrayList<UserData> userData) {
        // Sort userData based on classicLevel in descending order
        userData.sort((a, b) -> Integer.compare(b.getClassicLevel(), a.getClassicLevel()));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Classic Level");
        for (UserData data : userData) {
            model.addRow(new Object[]{data.getName(), data.getClassicLevel()});
        }
        classicLeaderboardTable.setModel(model);
    }

    // Method to update frenzy leaderboard table
    private void updateFrenzyLeaderboard(ArrayList<UserData> userData) {
        // Sort userData based on frenzyLevel in descending order
        userData.sort((a, b) -> Integer.compare(b.getFrenzyLevel(), a.getFrenzyLevel()));

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Frenzy Level");
        for (UserData data : userData) {
            model.addRow(new Object[]{data.getName(), data.getFrenzyLevel()});
        }
        frenzyLeaderboardTable.setModel(model);
    }

    // UserData class to hold user data
    private static class UserData {
        private String name;
        private int classicLevel;
        private int frenzyLevel;

        public UserData(String name, int classicLevel, int frenzyLevel) {
            this.name = name;
            this.classicLevel = classicLevel;
            this.frenzyLevel = frenzyLevel;
        }

        public String getName() {
            return name;
        }

        public int getClassicLevel() {
            return classicLevel;
        }

        public int getFrenzyLevel() {
            return frenzyLevel;
        }
    }
}