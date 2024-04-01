import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

/**
 * Represents the classic mode screen of the GeoKing application. This class contains the entire GUI for the classic
 * game mode. It calls Level database to select a random country from the user's current level.
 * As well, it handles checking the guess and incrementing the user's level if the guess is correct.
 * Before sending them to the next level.
 *
 * @version 1.0
 * @author Colin
 */
public class GUIClassicModeScreen extends JPanel {
    /** This is the layout of the cards */
    private final CardLayout cardLayout;
    /** This cardPanel stores all the cards */
    private final JPanel cardPanel;
    /** This takes the user's input */
    private final JTextField inputTextField;
    /** This stores the country */
    private country Country;
    /** The label displaying remaining lives */
    private JLabel livesLabel;
    /** The remaining lives */
    private int lives;

    /**
     * Constructs a GUIClassicModeScreen with the specified CardLayout and JPanel.
     * This manages all the windows as well as buttons for the classic mode screen.
     *
     * @param cardLayout The CardLayout to use for managing screens.
     * @param cardPanel  The JPanel to which this screen is added if the user gets the guess
     *                   correct.
     */
    public GUIClassicModeScreen(CardLayout cardLayout, JPanel cardPanel) {
        boolean mode = GUISettingsScreen.isFlagModeEnabled();
        SoundPlayer clicker = new SoundPlayer();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        String path = "";

        // Level selection
        user currentUser = GUILoginScreen.getCurrentUser();
        levelDatabase levels = new levelDatabase();

        // Admin mode level selection
        if (currentUser.getAdmin()) {
            boolean validInput = false;
            while (!validInput) {
                // Create a panel to hold input components
                JPanel adminInputPanel = new JPanel(new GridLayout(2, 2));

                // Add labels and text fields for integer input
                JTextField levelField = new JTextField(5);
                JTextField levelChoice = new JTextField(5);
                adminInputPanel.add(new JLabel("Enter Level (1-20): "));
                adminInputPanel.add(levelField);
                adminInputPanel.add(new JLabel("Enter Level Choice (1-4): "));
                adminInputPanel.add(levelChoice);

                // Show the dialog
                int option = JOptionPane.showOptionDialog(null, adminInputPanel, "Level 1 - 20, Choice 1 - 4.", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{"OK"}, null);
                if (option == JOptionPane.OK_OPTION) {
                    // Parse and retrieve the entered integers
                    try {
                        int level = Integer.parseInt(levelField.getText());
                        int ranLevel = Integer.parseInt(levelChoice.getText());

                        // Check if any of the entered integers are outside the specified range
                        if (level < 1 || level > 20 || ranLevel < 1 || ranLevel > 4) {
                            // Display error message for invalid input range
                            JOptionPane.showMessageDialog(null, "Please enter valid integers for level (1-20) and choice (1-4).");
                        } else {
                            // Use the entered integers for further processing
                            Country = levels.adminSelect(level, ranLevel);
                            validInput = true; // Set validInput to true to exit the loop
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid input format
                        JOptionPane.showMessageDialog(null, "Please enter valid integers for level and choice.");
                    }
                } else {
                    // If the user cancels, select a default level
                    Country = levels.adminSelect(1, 1);
                    break; // Exit the loop
                }
            }
        } else {
            // User mode level selection
            int classicLevel = currentUser.getClassicLevel();
            Country = levels.selectLevel(classicLevel);
        }

        lives = 5; // Initialize lives
        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192));

        // Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(192, 192, 192));
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
            cardLayout1.show(getParent(), "MAIN_MENU");
        });
        topPanel.add(exitButton, BorderLayout.WEST);
        livesLabel = new JLabel("Lives: " + lives);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        livesLabel.setForeground(Color.BLACK);
        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(livesLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(192, 192, 192));
        if (mode) {
            path = "src/Resources/Countries/" + Country.getName().toLowerCase() + "-silhouette.png";
        } else {
            path = "src/Resources/Flags/" + Country.getName() + "Flag.png";
        }
        ImageIcon imageIcon = new ImageIcon(path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);
        inputTextField = new JTextField();
        inputTextField.setHorizontalAlignment(JTextField.CENTER);
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 40));
        inputTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processGuess();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        centerPanel.add(inputTextField, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Enter Guess button
        JButton enterGuessButton = new JButton("Enter Guess");
        enterGuessButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            processGuess();
        });
        add(enterGuessButton, BorderLayout.SOUTH);
    }

    /**
     * Processes the user's guess, if the guess is incorrect and the user has lives left it does nothing.
     * If the user's guess is correct it gives the option for the user to continue to the next level or return to main menu
     * If the user is out of live it returns the user to the main menu.
     */
    private void processGuess() {
        String guess = inputTextField.getText().toLowerCase();
        user currentUser = GUILoginScreen.getCurrentUser();
        String countyName = Country.getName().toLowerCase();

        if (guess.equals(countyName)) {
            // Correct guess
            if (currentUser.getAdmin()) {
                JOptionPane.showMessageDialog(this, "Congratulations " + guess + " was the correct country.", "Game Over",  JOptionPane.INFORMATION_MESSAGE);
                CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                cardLayout1.show(getParent(), "MAIN_MENU");
            } else {
                if (currentUser.getClassicLevel() < 20) {
                    currentUser.incrementClassicLevel();
                    userDatabase users = new userDatabase();
                    users.findUser(currentUser.getName()).incrementClassicLevel();
                    users.exportDatabase();
                }
                else {
                    JOptionPane.showMessageDialog(this, "Congratulations! You have now mastered GEOKING!", "Game Over",  JOptionPane.INFORMATION_MESSAGE);
                    CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                    cardLayout1.show(getParent(), "MAIN_MENU");
                    return;
                }

                int option = JOptionPane.showOptionDialog(this,
                        "Congratulations " + guess + " was the correct country.",
                        "Correct Guess",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        new String[]{"Next Level", "Main Menu"},
                        "Main Menu");

                if (option == JOptionPane.YES_OPTION) {
                    Component[] components = cardPanel.getComponents();
                    for (Component component : components) {
                        if (component instanceof GUIClassicModeScreen) {
                            cardPanel.remove(component);
                        }
                    }
                    GUIClassicModeScreen GUIClassicModeScreen = new GUIClassicModeScreen(cardLayout, cardPanel);
                    cardPanel.add(GUIClassicModeScreen, "ClassicModeScreen");
                    cardLayout.show(cardPanel, "ClassicModeScreen");
                } else {
                    CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                    cardLayout1.show(getParent(), "MAIN_MENU");
                }
            }
        } else {
            // Incorrect guess
            JOptionPane.showMessageDialog(this, "You guessed: " + guess + ", This was not the correct country.");
            lives--;

            // Update lives label
            livesLabel.setText("Lives: " + lives);

            inputTextField.setText("");

            if (lives <= 0) {
                JOptionPane.showMessageDialog(this, "Game over! You have run out of lives.", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                cardLayout1.show(getParent(), "MAIN_MENU");
            }
        }
    }
}
