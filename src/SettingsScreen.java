import javax.swing.*;
import java.awt.*;

public class SettingsScreen extends JPanel {
    // Declare the flagMode variable as static to make it globally accessible
    private static boolean flagMode = false;

    public SettingsScreen(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        JLabel settingsHeader = new JLabel("SETTINGS", SwingConstants.CENTER);
        settingsHeader.setFont(new Font("Arial", Font.BOLD, 24));
        add(settingsHeader, BorderLayout.NORTH);

        // Settings panel for toggle buttons
        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        // Music On/Off toggle button
        JToggleButton musicToggleButton = new JToggleButton("Music On");
        musicToggleButton.addActionListener(e -> {
            if (musicToggleButton.isSelected()) {
                musicToggleButton.setText("Music Off");
                // Logic to turn off music here
            } else {
                musicToggleButton.setText("Music On");
                // Logic to turn on music here
            }
        });
        musicToggleButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Flag Mode On/Off toggle button
        JToggleButton flagModeToggleButton = new JToggleButton("Flag Mode On");
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
 //
    // Public static method to access the flag mode status from other classes
    public static boolean isFlagModeEnabled() {
        return flagMode;
    }
}

