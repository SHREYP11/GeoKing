import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassicModeScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField inputTextField;

    private country Country;

    public ClassicModeScreen(CardLayout cardLayout, JPanel cardPanel) {
        boolean mode = SettingsScreen.isFlagModeEnabled();
        SoundPlayer clicker = new SoundPlayer();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
        String countyName = "";
        levelDatabase levels = new levelDatabase();
        // this is the level selection code
        user currentUser = LoginScreen.getCurrentUser();
        if (currentUser.getAdmin()) {
            // Create a panel to hold input components
            JPanel adminInputPanel = new JPanel(new GridLayout(2, 2));

            // Add labels and text fields for integer input
            JTextField levelField = new JTextField(5);
            JTextField levelChoice = new JTextField(5);
            adminInputPanel.add(new JLabel("Enter Level: "));
            adminInputPanel.add(levelField);
            adminInputPanel.add(new JLabel("Enter Level Choice: "));
            adminInputPanel.add(levelChoice);

            // Show the dialog
            int option = JOptionPane.showConfirmDialog(null, adminInputPanel, "Level 1 - 20, Choice 1 - 4.", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Parse and retrieve the entered integers
                try {
                    int level = Integer.parseInt(levelField.getText());
                    int ranLevel = Integer.parseInt(levelChoice.getText());
                    // Use the entered integers for further processing
                    Country = levels.adminSelect(level, ranLevel);
                    countyName = Country.getName();
                } catch (NumberFormatException e) {
                    // Handle invalid input
                    JOptionPane.showMessageDialog(null, "Please enter valid integers for level and classic level.");
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

        JButton enterGuessButton = new JButton("Enter Guess");
        enterGuessButton.addActionListener(event -> {
            clicker.playSound("src/Resources/click.wav", false);
            processGuess();
        });
        add(enterGuessButton, BorderLayout.SOUTH);
    }

    private void processGuess() {
        String guess = inputTextField.getText();
        user currentUser = LoginScreen.getCurrentUser();
        String countyName = Country.getName();

        AtomicInteger lives = new AtomicInteger(5);

        if (Objects.equals(guess.toLowerCase(), countyName.toLowerCase())) {
            // Correct guess
            currentUser.incrementClassicLevel();
            userDatabase updateData = new userDatabase();
            updateData.findUser(currentUser.getName()).incrementClassicLevel();
            updateData.exportDatabase();
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
                    if (component instanceof ClassicModeScreen) {
                        cardPanel.remove(component);
                    }
                }
                ClassicModeScreen classicModeScreen = new ClassicModeScreen(cardLayout,cardPanel);

                // Add the ClassicModeScreen to the cardPanel
                cardPanel.add(classicModeScreen, "ClassicModeScreen");

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
            lives.getAndDecrement();
            JOptionPane.showMessageDialog(this, "You guessed: " + guess + ", This was not the correct country.");
            inputTextField.setText("");

            // Update lives label to reflect the new value
            JLabel livesLabel = (JLabel)((JPanel)getComponent(0)).getComponent(1); // Assuming lives label is at index 1 of the top panel
            livesLabel.setText("Lives: " + lives);

            // Check if lives are depleted
            if (lives.get() <= 0) {
                JOptionPane.showMessageDialog(this, "Game Over! You have run out of lives.");
                CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                cardLayout1.show(getParent(), "MAIN_MENU");
                revalidate();
                repaint();
            }
        }
    }
}
