import java.awt.*;
import javax.swing.*;
class GUIMainMenuScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SoundPlayer soundPlayer = new SoundPlayer();
    private SoundPlayer clicker = new SoundPlayer();
    private Boolean sound = GUISettingsScreen.isMusicOn();

    public GUIMainMenuScreen(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        if (sound) {
            soundPlayer.playSound("src/Resources/music.wav", true);
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
                if (component instanceof GUIFrenzyModeScreen) {
                    cardPanel.remove(component);
                }
            }
            GUIFrenzyModeScreen GUIFrenzyModeScreen = new GUIFrenzyModeScreen(cardLayout,cardPanel);

            // Add the ClassicModeScreen to the cardPanel
            cardPanel.add(GUIFrenzyModeScreen, "FrenzyModeScreen");

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
                if (component instanceof GUIClassicModeScreen) {
                    cardPanel.remove(component);
                }
            }
            GUIClassicModeScreen GUIClassicModeScreen = new GUIClassicModeScreen(cardLayout,cardPanel);

            // Add the ClassicModeScreen to the cardPanel
            this.cardPanel.add(GUIClassicModeScreen, "ClassicModeScreen");

            // Switch to the ClassicModeScreen using CardLayout
            this.cardLayout.show(cardPanel, "ClassicModeScreen");

            revalidate();
            repaint();
        });
        leaderboardButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            GUILeaderboardScreen GUILeaderboardScreen = new GUILeaderboardScreen(); // Instantiate LeaderboardScreen
            this.cardPanel.add(GUILeaderboardScreen, "LeaderboardScreen"); // A
            cardLayout.show(cardPanel, "LeaderboardScreen");
            revalidate();
            repaint();
            // Switch to the LeaderboardScreen using CardLayout
        });

        settingsButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            this.cardLayout.show(this.cardPanel, "SETTINGS");});

        exitButton.addActionListener(e -> System.exit(0)); // Exit the program
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
    }

    public SoundPlayer getSoundPlayer(){
        return soundPlayer;
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
