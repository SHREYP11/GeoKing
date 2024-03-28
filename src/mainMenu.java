import javax.swing.*;
import java.awt.*;

class LoginScreen extends JPanel {

    public static user currentUser;
    public LoginScreen() {
        setBackground(new Color(192, 192, 192));
        setLayout(new BorderLayout(0, 50));

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("GEOKING"));
        titlePanel.setBackground(new Color(192, 192, 192));

        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        containerPanel.setBackground(new Color(192, 192, 192));

        JPanel loginPanel = new JPanel(new BorderLayout()); // Use BorderLayout for loginPanel
        loginPanel.setBackground(new Color(192, 192, 192));

        // Create a label for the image
        JLabel imageLabel = new JLabel(new ImageIcon("src/Resources/logo.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the image
        loginPanel.add(imageLabel, BorderLayout.CENTER); // Add image label to the center of loginPanel

        JPanel usernamePanel = new JPanel(new FlowLayout()); // Create a panel for username components
        usernamePanel.setBackground(new Color(192, 192, 192));
        usernamePanel.add(new JLabel("USERNAME:"));

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernamePanel.add(usernameField);

        loginPanel.add(usernamePanel, BorderLayout.SOUTH); // Add usernamePanel to the bottom of loginPanel

        containerPanel.add(loginPanel, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(192, 192, 192));

        JButton enterButton = new JButton("ENTER");
        enterButton.setPreferredSize(new Dimension(200, 80));
        enterButton.setFont(enterButton.getFont().deriveFont(Font.BOLD, 18));
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
        frenzyModeButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof FrenzyModeScreen) {
                    cardPanel.remove(component);
                }
            }
            FrenzyModeScreen frenzyModeScreen = new FrenzyModeScreen(cardLayout,cardPanel);

            // Add the ClassicModeScreen to the cardPanel
            cardPanel.add(frenzyModeScreen, "FrenzyModeScreen");

            // Switch to the ClassicModeScreen using CardLayout
            cardLayout.show(cardPanel, "FrenzyModeScreen");

            revalidate();
            repaint();

        });
        classicModeButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            // Create a new instance of ClassicModeScreen
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof ClassicModeScreen) {
                    cardPanel.remove(component);
                }
            }
            ClassicModeScreen classicModeScreen = new ClassicModeScreen(cardLayout,cardPanel);

            // Add the ClassicModeScreen to the cardPanel
            this.cardPanel.add(classicModeScreen, "ClassicModeScreen");

            // Switch to the ClassicModeScreen using CardLayout
            this.cardLayout.show(cardPanel, "ClassicModeScreen");

            revalidate();
            repaint();
        });
        leaderboardButton.addActionListener(e -> {

            LeaderboardScreen leaderboardScreen = new LeaderboardScreen(); // Instantiate LeaderboardScreen
            this.cardPanel.add(leaderboardScreen, "LeaderboardScreen"); // A
            cardLayout.show(cardPanel, "LeaderboardScreen");
            revalidate();
            repaint();
            // Switch to the LeaderboardScreen using CardLayout
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
