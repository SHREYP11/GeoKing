import javax.swing.*;
import java.awt.*;

class LoginScreen extends JPanel {

    public static user currentUser;
    public LoginScreen() {
        setLayout(new BorderLayout(0, 50));
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("GEOKING"));

        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel loginPanel = new JPanel(new FlowLayout());
        loginPanel.add(new JLabel("USERNAME:"));

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        loginPanel.add(usernameField);

        containerPanel.add(loginPanel, gbc);

        JPanel buttonPanel = new JPanel();
        JButton enterButton = new JButton("ENTER");
        buttonPanel.add(enterButton);

        add(titlePanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        enterButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            // this is the code to grab the user from database
            userDatabase userData = new userDatabase();
            String usernameInputed = usernameField.getText();
            currentUser = userData.findUser(usernameInputed);
            if (currentUser == null) {
                userData.createUser(usernameInputed);
                userData.exportDatabase();
                currentUser = userData.findUser(usernameInputed);
            }
            cardLayout.show(getParent(), "MAIN_MENU");
        });


    }

    // Static method to set the current user
    public static void setCurrentUser(user user) {
        currentUser = user;
    }

    // Static method to get the current user
    public static user getCurrentUser() {
        return currentUser;
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
    }
}

class MainMenuScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private SoundPlayer soundPlayer = new SoundPlayer();
    private SoundPlayer clicker = new SoundPlayer();
    private Boolean sound = SettingsScreen.isMusicOn();

    public MainMenuScreen(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        if (sound) {
            soundPlayer.playSound("src/Resources/music.wav", true);
        } else {
            soundPlayer.stopSound();
        }

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("WELCOME TO GEOKING!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JButton classicModeButton = new JButton("CLASSIC MODE");
        JButton frenzyModeButton = new JButton("FRENZY MODE");
        JButton leaderboardButton = new JButton("LEADERBOARD");
        JButton settingsButton = new JButton("SETTINGS");
        JButton exitButton = new JButton("EXIT");

        Dimension buttonSize = new Dimension(800, 100);
        classicModeButton.setPreferredSize(buttonSize);
        frenzyModeButton.setPreferredSize(buttonSize);
        leaderboardButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(new Dimension(200,25));

        classicModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        frenzyModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(classicModeButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(frenzyModeButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(settingsButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);

        classicModeButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            // Create a new instance of ClassicModeScreen
            ClassicModeScreen classicModeScreen = new ClassicModeScreen(cardLayout, cardPanel);

            // Create a main menu card panel instance
            MainMenuScreen mainMenuScreen = new MainMenuScreen(cardLayout, cardPanel);

            // Add the main menu card panel to the main menu card panel container
            this.cardPanel.add(mainMenuScreen, "MainMenu");

            // Add the ClassicModeScreen to the cardPanel
            this.cardPanel.add(classicModeScreen, "ClassicModeScreen");

            // Switch to the ClassicModeScreen using CardLayout
            this.cardLayout.show(cardPanel, "ClassicModeScreen");

            revalidate();
            repaint();
        });

        // Inside the MainMenuScreen class
        LeaderboardScreen leaderboardScreen;

        // Inside the constructor of MainMenuScreen
        leaderboardScreen = new LeaderboardScreen(); // Instantiate LeaderboardScreen
        this.cardPanel.add(leaderboardScreen, "LeaderboardScreen"); // Add it to cardPanel with identifier "LeaderboardScreen"

        // Action listener for the leaderboard button
        leaderboardButton.addActionListener(e -> {
            cardLayout.show(cardPanel, "LeaderboardScreen"); // Switch to the LeaderboardScreen using CardLayout
        });

        settingsButton.addActionListener(e -> this.cardLayout.show(this.cardPanel, "SETTINGS"));

        exitButton.addActionListener(e -> System.exit(0)); // Exit the program
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("src/Resources/background.jpg");
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
