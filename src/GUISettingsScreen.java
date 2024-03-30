import javax.swing.*;
import java.awt.*;

public class GUISettingsScreen extends JPanel {
    // Static variables for flag mode and music state
    private static boolean flagMode = true;
    private static boolean musicOn = true; // Assuming music is on by default

    public GUISettingsScreen(CardLayout cardLayout, JPanel cardPanel, SoundPlayer music) {
        SoundPlayer clicker = new SoundPlayer();
        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192)); // Set background color for SettingsScreen

        JLabel settingsHeader = new JLabel("SETTINGS", SwingConstants.CENTER);
        settingsHeader.setFont(new Font("Arial", Font.BOLD, 24));
        settingsHeader.setForeground(Color.BLACK); // Set text color
        add(settingsHeader, BorderLayout.NORTH);

        // Settings panel for toggle buttons
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
        settingsPanel.setBackground(new Color(192, 192, 192)); // Set background color for settingsPanel

        // Music On/Off toggle button
        JToggleButton musicToggleButton = new JToggleButton("Music On", musicOn);
        musicToggleButton.addActionListener(e -> {
            musicOn = musicToggleButton.isSelected(); // Update the music state based on the button state
            if (musicOn) {
                musicToggleButton.setText("Music On");
                music.playSound("src/Resources/music.wav", true);
            } else {
                musicToggleButton.setText("Music Off");
                music.stopSound();
            }
        });
        musicToggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Flag Mode On/Off toggle button
        JToggleButton flagModeToggleButton = new JToggleButton("Flag Mode Off", flagMode);
        flagModeToggleButton.addActionListener(e -> {
            flagMode = flagModeToggleButton.isSelected(); // Update the flag mode status based on the button state
            if (flagMode) {
                flagModeToggleButton.setText("Flag Mode Off");
                // Logic to enable flag mode here
            } else {
                flagModeToggleButton.setText("Flag Mode On");
                // Logic to disable flag mode here
            }
        });
        flagModeToggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Adding toggle buttons to the settings panel
        settingsPanel.add(Box.createVerticalGlue());
        settingsPanel.add(musicToggleButton);
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer
        settingsPanel.add(flagModeToggleButton);
        settingsPanel.add(Box.createVerticalGlue());

        add(settingsPanel, BorderLayout.CENTER);

        // Back button panel
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setBackground(new Color(192, 192, 192)); // Set background color for backButtonPanel
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            cardLayout.show(cardPanel, "MAIN_MENU");

        });
        backButtonPanel.add(backButton);

        add(backButtonPanel, BorderLayout.SOUTH);
    }

    // Public static methods to access the status of flag mode and music state
    public static boolean isFlagModeEnabled() {
        return flagMode;
    }

    public static boolean isMusicOn() {
        return musicOn;
    }
}
