import javax.swing.*;
import java.awt.*;

public class SettingsScreen extends JPanel {
    public SettingsScreen(CardLayout cardLayout, JPanel cardPanel) {
        setLayout(new BorderLayout());

        JLabel settingsHeader = new JLabel("SETTINGS", SwingConstants.CENTER);
        settingsHeader.setFont(new Font("Arial", Font.BOLD, 24));
        add(settingsHeader, BorderLayout.NORTH);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> cardLayout.show(cardPanel, "MAIN_MENU"));
        backButtonPanel.add(backButton);

        add(backButtonPanel, BorderLayout.SOUTH);
    }
}
