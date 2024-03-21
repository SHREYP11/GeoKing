import javax.swing.*;
import java.awt.*;

// The main class that starts the application.
public class GeoKingApplication {

    public static void main(String[] args) {
        // Run the GUI in the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> new GeoKingApplication().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("GeoKing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720); // Set frame size to 1280x720
        frame.setResizable(false);

        // Create a CardLayout for managing different screens.
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Add login screen to the card panel
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setCardLayout(cardLayout, cardPanel);
        cardPanel.add(loginScreen, "LOGIN");

        // Add main menu screen to the card panel
        MainMenuScreen mainMenuScreen = new MainMenuScreen();
        mainMenuScreen.setCardLayout(cardLayout, cardPanel);
        cardPanel.add(mainMenuScreen, "MAIN_MENU");

        // Show login screen initially
        cardLayout.show(cardPanel, "LOGIN");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}

// The LoginScreen class that creates the login JPanel.
class LoginScreen extends JPanel {

    public LoginScreen() {
        setLayout(new BorderLayout(0, 50)); // Add some vertical spacing

        // Create the top panel that contains the title.
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("GEOKING"));

        // Create a container panel that will help center the login fields
        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Create the middle panel that contains the username field.
        JPanel loginPanel = new JPanel(new FlowLayout());
        loginPanel.add(new JLabel("USERNAME:"));

        JTextField usernameField = new JTextField();
        // Make the username field a little bigger
        usernameField.setPreferredSize(new Dimension(300, 40)); // Adjusted size
        loginPanel.add(usernameField);

        containerPanel.add(loginPanel, gbc); // Add loginPanel to the centering container

        // Create the bottom panel that contains the "Enter" button.
        JPanel buttonPanel = new JPanel();
        JButton enterButton = new JButton("ENTER");
        buttonPanel.add(enterButton);

        // Add panels to the frame.
        add(titlePanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER); // Add the container to center loginPanel
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listener to the button.
        enterButton.addActionListener(e -> {
            // Switch to main menu screen
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "MAIN_MENU");
        });
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
        // Unused in this example but can be useful for future expansion
    }
}





// The MainMenuScreen class that creates the main menu JPanel.
class MainMenuScreen extends JPanel {
    private Image backgroundImage;

    public MainMenuScreen() {
        setLayout(new BorderLayout());

        // Load your background image
        backgroundImage = Toolkit.getDefaultToolkit().getImage("background.jpg");

        // Add the main menu title.
        JLabel titleLabel = new JLabel("WELCOME TO GEOKING!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Add buttons for the different modes.
        JButton classicModeButton = new JButton("CLASSIC MODE");
        JButton frenzyModeButton = new JButton("FRENZY MODE");
        JButton leaderboardButton = new JButton("LEADERBOARD");
        JButton settingsButton = new JButton("SETTINGS");

        // Set buttons size to be three times larger
        Dimension buttonSize = new Dimension(800, 100); // Set to 300x90
        classicModeButton.setPreferredSize(buttonSize);
        frenzyModeButton.setPreferredSize(buttonSize);
        leaderboardButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);

        // Center-align buttons
        classicModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        frenzyModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make the panel transparent
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(classicModeButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(frenzyModeButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(settingsButton);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);

        classicModeButton.addActionListener(e -> {
            // Remove all components
            removeAll();
            // Instantiate and add ClassicModeScreen
            add(new ClassicModeScreen(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
        // Unused in this example but can be useful for future expansion
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    class ClassicModeScreen extends JPanel {

        public ClassicModeScreen() {
            setLayout(new BorderLayout());

            // Add image and text input in the center
            JPanel centerPanel = new JPanel(new BorderLayout());
            ImageIcon imageIcon = new ImageIcon("Flag_of_Australia-512x256.png");
            JLabel imageLabel = new JLabel(imageIcon);
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(imageLabel, BorderLayout.CENTER);

            JTextField inputTextField = new JTextField();
            inputTextField.setHorizontalAlignment(JTextField.CENTER);
            centerPanel.add(inputTextField, BorderLayout.SOUTH);

            add(centerPanel, BorderLayout.CENTER);

            // Add lives display on top left
            JLabel livesLabel = new JLabel("Lives: 3");
            livesLabel.setFont(new Font("Arial", Font.BOLD, 24));
            livesLabel.setForeground(Color.BLACK);
            add(livesLabel, BorderLayout.NORTH);

            // Add hint button in the top right corner
            JButton hintButton = new JButton("Hint");
            hintButton.setPreferredSize(new Dimension(80, 30)); // Set preferred size
            hintButton.addActionListener(event -> {
                // Display hint logic
                JOptionPane.showMessageDialog(this, "This is a hint.");
            });
            JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            hintPanel.setOpaque(false); // Make the panel transparent
            hintPanel.add(hintButton);
            add(hintPanel, BorderLayout.NORTH);

            // Add Enter Guess button below the input box
            JButton enterGuessButton = new JButton("Enter Guess");
            enterGuessButton.addActionListener(event -> {
                // Enter Guess logic
                String guess = inputTextField.getText();
                // Process the guess
                JOptionPane.showMessageDialog(this, "You guessed: " + guess);
                // Clear the text field after processing the guess
                inputTextField.setText("");
            });
            add(enterGuessButton, BorderLayout.SOUTH);
        }
    }
}
