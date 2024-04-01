import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

/**
 * Represents the frenzy mode screen of the GeoKing application, this class contains the entire GUI for the classic
 * game mode, it calls Level database to select a random country from the users current level.
 * As well, it handles checking the guess and incrementing the users level if the guess is correct. This is before sending
 * them to the next level
 * Usage Example:
 * <pre>
 * {@code
 *  GUIFrenzyModeScreen GUIFrenzyModeScreen = new GUIFrenzyModeScreen(cardLayout,cardPanel);
 *   // Add the ClassicModeScreen to the cardPanel
 *   cardPanel.add(GUIFrenzyModeScreen, "FrenzyModeScreen");
 *   // Switch to the ClassicModeScreen using CardLayout
 *   cardLayout.show(cardPanel, "FrenzyModeScreen");
 *   revalidate();
 *   repaint();
 *  }
 * </pre>
 *
 *
 * @version 1.0
 * @author Colin
 */
public class GUIFrenzyModeScreen extends JPanel {
    /**This is the layout of the cards */
    private CardLayout cardLayout;
    /** This is what stores all the cards for the gui*/
    private JPanel cardPanel;
    /** This is the users input for the guess*/
    private JTextField inputTextField;
    /** The timer*/
    private Timer timer;
    /** */
    private JLabel timerLabel;
    /** How many seconds till the game ends */
    private int secondsLeft;
    /** THe current country */

    private country Country;

    /**
     * Constructs a GUIFrenzyModeScreen with the specified CardLayout and JPanel.
     * This handles all the panels and buttons for the frenzy mode screen
     * @param cardLayout The CardLayout to use for managing screens.
     * @param cardPanel  The JPanel to which this screen is added, This is used for the new instance
     *                   if the user gets the correct guess.
     */
    public GUIFrenzyModeScreen(CardLayout cardLayout, JPanel cardPanel) {
        boolean mode = GUISettingsScreen.isFlagModeEnabled();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        SoundPlayer clicker = new SoundPlayer();

        levelDatabase levels = new levelDatabase();
        user currentUser = GUILoginScreen.getCurrentUser();
        int frenzyLevel = currentUser.getFrenzyLevel();
        Country = levels.selectLevel(frenzyLevel);
        String countyName = Country.getName();

        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(192, 192, 192));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            timer.stop();
            CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
            cardLayout1.show(getParent(), "MAIN_MENU");
            revalidate();
            repaint();
        });
        topPanel.add(exitButton, BorderLayout.WEST);

        timerLabel = new JLabel("Time Left: 30");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(timerLabel, BorderLayout.CENTER);

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
        centerPanel.setBackground(new Color(192, 192, 192));

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
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 40));
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

        secondsLeft = 30;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                if (secondsLeft >= 0) {
                    timerLabel.setText("Time Left: " + secondsLeft);
                } else {
                    timer.stop();
                    gameOver();
                }
            }
        });
        timer.start();
    }

    /**
     * Processes the user's guess, if the guess is incorrect and the user has time left it tells them and lets them guess again
     * If the user's guess is correct it gives the option for the user to continue to the next level or return to main menu
     */
    private void processGuess() {
        String guess = inputTextField.getText().toLowerCase();
        user currentUser = GUILoginScreen.getCurrentUser();
        String countyName = Country.getName();

        if (Objects.equals(guess.toLowerCase(), countyName.toLowerCase())) {
            timer.stop();
            // Correct guess
            if (currentUser.getFrenzyLevel() < 20) {
                currentUser.incrementFrenzyLevel();
                userDatabase users = new userDatabase();
                users.findUser(currentUser.getName()).incrementFrenzyLevel();
                users.exportDatabase();
            } else {
                // The user has reached level 20
                JOptionPane.showMessageDialog(this, "Congratulations! You have mastered GEOKING!", "Game Over", JOptionPane.INFORMATION_MESSAGE);

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
                Component[] components = cardPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof GUIFrenzyModeScreen) {
                        cardPanel.remove(component);
                    }
                }
                GUIFrenzyModeScreen GUIFrenzyModeScreen = new GUIFrenzyModeScreen(cardLayout, cardPanel);
                cardPanel.add(GUIFrenzyModeScreen, "FrenzyModeScreen");
                cardLayout.show(cardPanel, "FrenzyModeScreen");

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
    /**
     * When the timer hits 0 a message is displayed and the user is sent back to the main menu
     */
    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Time's up! Game over.");
        timerLabel.setText("Time's Up!");
        CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
        cardLayout1.show(getParent(), "MAIN_MENU");
        revalidate();
        repaint();
    }
}
