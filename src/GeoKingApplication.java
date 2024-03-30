import javax.swing.*;
import java.awt.*;
/**
 * This class represents the main application for GeoKing, and is used to start the GUI which then calls
 * other card pannels
 * @author Colin
 * @version 1.0
 */
public class GeoKingApplication {

    /**
     * Main method to start the GeoKing application.
     *
     * @param args Command-line arguments are not used
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GeoKingApplication().createAndShowGUI());
    }

    /**
     * Creates and displays the GUI for GeoKing. This creates a locked 1280,720 display
     * This also initializes all the cards that will be used except the game modes and
     * adds them to the card panel.
     */
    private void createAndShowGUI() {
        JFrame frame = new JFrame("GeoKing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Creating display
        frame.setSize(1280, 720);
        frame.setResizable(false);
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        // intializing cards
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
