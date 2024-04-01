import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents the classic mode screen of the GeoKing application, this class contains the entire GUI for the classic
 * game mode, it calls Level database to select a random country from the users current level.
 * As well, it handles checking the guess and incrementing the users level if the guess is correct. This is before sending
 * them to the next level
 *
 * Usage Example:
 * <pre>
 * {@code
 *  GUIClassicModeScreen GUIClassicModeScreen = new GUIClassicModeScreen(cardLayout,cardPanel);
 *  // Add the ClassicModeScreen to the cardPanel
 *  this.cardPanel.add(GUIClassicModeScreen, "ClassicModeScreen");
 *  // Switch to the ClassicModeScreen using CardLayout
 *  this.cardLayout.show(cardPanel, "ClassicModeScreen");
 *  revalidate();
 *   repaint();
 *  }
 * </pre>
 *
 *
 * @version 1.0
 * @author Colin
 */
public class GUIClassicModeScreen extends JPanel {
    /** This is the layout of the cards*/
    private CardLayout cardLayout;
    /**This cardPanel stores all the cards */
    private JPanel cardPanel;
    /** This takes the users input*/
    private JTextField inputTextField;
    /** This Stores the country*/
    private country Country;

    /**
     * Constructs a GUIClassicModeScreen with the specified CardLayout and JPanel.
     * This manages all the windows as well as buttons for the classic mode screen.
     * @param cardLayout The CardLayout to use for managing screens.
     * @param cardPanel  The JPanel to which this screen is added if the user gets the guess
     *                   correct.
     */
    public GUIClassicModeScreen(CardLayout cardLayout, JPanel cardPanel) {
        boolean mode = GUISettingsScreen.isFlagModeEnabled();
        SoundPlayer clicker = new SoundPlayer();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        String countyName = "";
        levelDatabase levels = new levelDatabase();
        // this is the level selection code
        user currentUser = GUILoginScreen.getCurrentUser();
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
                            countyName = Country.getName();
                            validInput = true; // Set validInput to true to exit the loop
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid input format
                        JOptionPane.showMessageDialog(null, "Please enter valid integers for level and choice.");
                    }
                }
                else {
                    Country = levels.adminSelect(1,1);
                    countyName = Country.getName();
                    break;
                }
            }
        }
        else {
            int classicLevel = currentUser.getClassicLevel();
            Country = levels.selectLevel(classicLevel);
            countyName = Country.getName();
        }
        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192)); // Set background color for ClassicModeScreen

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(192, 192, 192)); // Set background color for topPanel

        // Add Exit Button to top left
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
            cardLayout1.show(getParent(), "MAIN_MENU");
            revalidate();
            repaint();
        }); // Exit to main
        topPanel.add(exitButton, BorderLayout.WEST);

        // Lives Counter in the top middle
        AtomicInteger lives = new AtomicInteger(5);
        JLabel livesLabel = new JLabel("Lives: " + lives);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        livesLabel.setForeground(Color.BLACK);
        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(livesLabel, BorderLayout.CENTER);

        JButton hintButton = new JButton("Hint");
        hintButton.setPreferredSize(new Dimension(80, 30));
        hintButton.addActionListener(event -> {
            clicker.playSound("src/Resources/click.wav", false);
            JOptionPane.showMessageDialog(this, "This country is in " + Country.getContinent() + ".");
        });
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hintPanel.setOpaque(false);
        hintPanel.add(hintButton);
        topPanel.add(hintPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(192, 192, 192)); // Set background color for centerPanel

        String path = "";
        if (mode) {
            path = "src/Resources/Countries/" + countyName.toLowerCase() + "-silhouette.png";
        } else {
            path = "src/Resources/Flags/" + countyName + "Flag.png";
        }
        ImageIcon imageIcon = new ImageIcon(path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        inputTextField = new JTextField();
        inputTextField.setHorizontalAlignment(JTextField.CENTER);
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 40)); // Set text field height
        // This is the input for the enter key to make it do the same as the enter button
        inputTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processGuess();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        centerPanel.add(inputTextField, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
        JButton enterGuessButton = new JButton("Enter Guess");
        enterGuessButton.addActionListener(event -> {
            clicker.playSound("src/Resources/click.wav", false);
            processGuess();
        });
        add(enterGuessButton, BorderLayout.SOUTH);
    }

    /**
     * Processes the user's guess, if the guess is incorrect and the user has lives left it does nothing.
     * If the user's guess is correct it gives the option for the user to continue to the next level or return to main menu
     * IF the user is out of live it returns the user to the main menu.
     */
    private void processGuess() {
        String guess = inputTextField.getText();
        user currentUser = GUILoginScreen.getCurrentUser();
        String countyName = Country.getName();

        if (Objects.equals(guess.toLowerCase(), countyName.toLowerCase())) {
            // Correct guess
            if (currentUser.getClassicLevel() < 20) {
                currentUser.incrementClassicLevel();
            } else {
                // The user has reached level 20
                JOptionPane.showMessageDialog(this,
                        "Congratulations! You have now mastered GEOKING!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);

                // Return to the main menu
                CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                cardLayout1.show(getParent(), "MAIN_MENU");
                revalidate();
                repaint();

                return; // Exit the method to prevent further execution
            }

            // Rest of your code for proceeding to the next level
            int option = JOptionPane.showOptionDialog(this,
                    "Congratulations " + guess + " was the correct country.",
                    "Correct Guess",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Next Level", "Main Menu"},
                    "Main Menu");

            if (option == JOptionPane.YES_OPTION) {
                // Proceed to the next level
                Component[] components = cardPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof GUIClassicModeScreen) {
                        cardPanel.remove(component);
                    }
                }
                GUIClassicModeScreen GUIClassicModeScreen = new GUIClassicModeScreen(cardLayout, cardPanel);

                // Add the ClassicModeScreen to the cardPanel
                cardPanel.add(GUIClassicModeScreen, "ClassicModeScreen");

                // Switch to the ClassicModeScreen using CardLayout
                cardLayout.show(cardPanel, "ClassicModeScreen");

                revalidate();
                repaint();
            } else {
                // Return to the main menu
                CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                cardLayout1.show(getParent(), "MAIN_MENU");
                revalidate();
                repaint();
            }
        } else {
            // Incorrect guess
            JOptionPane.showMessageDialog(this, "You guessed: " + guess + ", This was not the correct country.");
            inputTextField.setText("");
        }
    }
}
