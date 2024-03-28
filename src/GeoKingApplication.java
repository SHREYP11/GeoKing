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

        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setCardLayout(cardLayout, cardPanel);
        cardPanel.add(loginScreen, "LOGIN");

        MainMenuScreen mainMenuScreen = new MainMenuScreen(cardLayout, cardPanel);
        mainMenuScreen.setCardLayout(cardLayout, cardPanel);
        cardPanel.add(mainMenuScreen, "MAIN_MENU");

        SettingsScreen settingsScreen = new SettingsScreen(cardLayout, cardPanel, mainMenuScreen.getSoundPlayer());
        cardPanel.add(settingsScreen, "SETTINGS");

        cardLayout.show(cardPanel, "LOGIN");

        frame.add(cardPanel);
        frame.setVisible(true);
    }
}
