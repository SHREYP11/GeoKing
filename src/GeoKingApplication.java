import javax.swing.*;
import java.awt.*;

public class GeoKingApplication {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GeoKingApplication().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("GeoKing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        GUILoginScreen GUILoginScreen = new GUILoginScreen();
        GUILoginScreen.setCardLayout(cardLayout, cardPanel);
        cardPanel.add(GUILoginScreen, "LOGIN");

        GUIMainMenuScreen GUIMainMenuScreen = new GUIMainMenuScreen(cardLayout, cardPanel);
        GUIMainMenuScreen.setCardLayout(cardLayout, cardPanel);
        cardPanel.add(GUIMainMenuScreen, "MAIN_MENU");

        GUISettingsScreen GUISettingsScreen = new GUISettingsScreen(cardLayout, cardPanel, GUIMainMenuScreen.getSoundPlayer());
        cardPanel.add(GUISettingsScreen, "SETTINGS");

        cardLayout.show(cardPanel, "LOGIN");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
