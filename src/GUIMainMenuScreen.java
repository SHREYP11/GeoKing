import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * The {@code GUIMainMenuScreen} class represents a Swing GUI panel that displays
 * the main menu screen of the game. It provides functionality to navigate to different game modes, the leaderboard,
 * settings, tutorial, and exit the game.
 * Example usage:
 * <pre>{@code
 *  GUIMainMenuScreen GUIMainMenuScreen = new GUIMainMenuScreen(cardLayout, cardPanel);
 *  GUIMainMenuScreen.setCardLayout(cardLayout, cardPanel);
 *  cardPanel.add(GUIMainMenuScreen, "MAIN_MENU");
 *  GUISettingsScreen GUISettingsScreen = new GUISettingsScreen(cardLayout, cardPanel, GUIMainMenuScreen.getSoundPlayer());
 *  cardPanel.add(GUISettingsScreen, "SETTINGS");
 *  cardLayout.show(cardPanel, "LOGIN");
 * frame.add(cardPanel);
 * frame.setVisible(true);
 * @author Sherry
 * @version 1.0
 */
class GUIMainMenuScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SoundPlayer soundPlayer = new SoundPlayer();
    private SoundPlayer clicker = new SoundPlayer();
    private Boolean sound = GUISettingsScreen.isMusicOn();

    /**
     * Constructs a new GUIMainMenuScreen panel, This handles all the inputs from buttons and hot keys as well.
     * @param cardLayout the ardLayout manager used for switching between panels
     * @param cardPanel  the parent panel containing all card panels
     */
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
        JButton tutorialButton = new JButton("TUTORIAL");

        Dimension buttonSize = new Dimension(800, 100);
        classicModeButton.setPreferredSize(buttonSize);
        frenzyModeButton.setPreferredSize(buttonSize);
        leaderboardButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(new Dimension(200,25));
        tutorialButton.setPreferredSize(buttonSize);

        classicModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        frenzyModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        tutorialButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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
        buttonPanel.add(tutorialButton);
        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);
        // frenzy
        frenzyModeButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
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
        });
        // classic mode
        classicModeButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof GUIClassicModeScreen) {
                    cardPanel.remove(component);
                }
            }
            GUIClassicModeScreen GUIClassicModeScreen = new GUIClassicModeScreen(cardLayout, cardPanel);
            cardPanel.add(GUIClassicModeScreen, "ClassicModeScreen");
            cardLayout.show(cardPanel, "ClassicModeScreen");
            revalidate();
            repaint();
        });
        // leaderboard
        leaderboardButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            Component[] components = cardPanel.getComponents();
            for (Component component : components) {
                if (component instanceof GUILeaderboardScreen) {
                    cardPanel.remove(component);
                }
            }
            GUILeaderboardScreen GUILeaderboardScreen = new GUILeaderboardScreen();
            cardPanel.add(GUILeaderboardScreen, "LeaderboardScreen");
            cardLayout.show(cardPanel, "LeaderboardScreen");
            revalidate();
            repaint();
        });
        // settings
        settingsButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            cardLayout.show(cardPanel, "SETTINGS");
        });
        // tutorial
        tutorialButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            // Create JDialog
            JDialog dialog = new JDialog();
            dialog.setTitle("Tutorial");
            dialog.setSize(900, 500);
            dialog.setLocationRelativeTo(null); // Center the window
            // Create JPanel
            JPanel panel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    ImageIcon imageIcon = new ImageIcon("src/Resources/Tutorial.png");
                    Image image = imageIcon.getImage();
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            };
            // Create OK button
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose(); // Close the dialog
                }
            });
            // Add components to dialog
            dialog.add(panel, BorderLayout.CENTER);
            dialog.add(okButton, BorderLayout.SOUTH);
            dialog.setModal(true); // Make the dialog modal
            dialog.setVisible(true);
        });

        // Exit Button
        exitButton.addActionListener(e -> System.exit(0));

        setOpaque(false);
    }

    /**
     * Sets the CardLayout manager and parent panel for switching between panels.
     * @param cardLayout the CardLayout manager
     * @param cardPanel  the parent panel containing all card panels
     */
    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;
    }

    /**
     * @return the sound player object
     */
    public SoundPlayer getSoundPlayer() {
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
