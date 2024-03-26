import javax.swing.*;
import java.awt.*;

public class SettingsScreen extends JPanel {
    // Static variables for flag mode and music state
    private static boolean flagMode = false;
    private static boolean musicOn = true; // Assuming music is on by default

    public SettingsScreen(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        JLabel settingsHeader = new JLabel("SETTINGS", SwingConstants.CENTER);
        settingsHeader.setFont(new Font("Arial", Font.BOLD, 24));
        add(settingsHeader, BorderLayout.NORTH);

        // Settings panel for toggle buttons
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        // Music On/Off toggle button
        JToggleButton musicToggleButton = new JToggleButton("Music On", musicOn);
        musicToggleButton.addActionListener(e -> {
            musicOn = musicToggleButton.isSelected(); // Update the music state based on the button state
            if (musicOn) {
                musicToggleButton.setText("Music Off");
                // Add logic to turn off music here
            } else {
                musicToggleButton.setText("Music On");
                // Add logic to turn on music here
            }
        });
        musicToggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Flag Mode On/Off toggle button
        JToggleButton flagModeToggleButton = new JToggleButton("Flag Mode On", flagMode);
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
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN_MENU"));
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
