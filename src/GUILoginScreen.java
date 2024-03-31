import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The GUILoginScreen class represents a Swing GUI panel that provides
 * a login interface for the game.
 * It allows users to enter their username and login to the game. If the username
 * does not exist, a new user is created.
 * <p>
 * Example usage:
 * <pre>{@code
 *    GUILoginScreen GUILoginScreen = new GUILoginScreen();
 *    GUILoginScreen.setCardLayout(cardLayout, cardPanel);
 *    cardPanel.add(GUILoginScreen, "LOGIN");
 *    GUIMainMenuScreen GUIMainMenuScreen = new GUIMainMenuScreen(cardLayout, cardPanel);
 *    GUIMainMenuScreen.setCardLayout(cardLayout, cardPanel);
 *    cardPanel.add(GUIMainMenuScreen, "MAIN_MENU");
 *    GUISettingsScreen GUISettingsScreen = new GUISettingsScreen(cardLayout, cardPanel, GUIMainMenuScreen.getSoundPlayer());
 *    cardPanel.add(GUISettingsScreen, "SETTINGS");
 *    cardLayout.show(cardPanel, "LOGIN");
 *    frame.add(cardPanel);
 *    frame.setVisible(true);
 * }</pre>
 * </p>
 *
 * @author Sherry
 * @version 1.0
 */
class GUILoginScreen extends JPanel {

    /** The currently logged-in user. */
    public static user currentUser;

    /**
     * Constructs a new GUILoginScreen panel and manages all button inputs as well as hotkeys.
     */
    public GUILoginScreen() {
        SoundPlayer clicker = new SoundPlayer();
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

        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(192, 192, 192));

        JLabel imageLabel = new JLabel(new ImageIcon("src/Resources/logo.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(imageLabel, BorderLayout.CENTER);

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(new Color(192, 192, 192));
        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernamePanel.add(usernameField, BorderLayout.CENTER);

        loginPanel.add(usernamePanel, BorderLayout.SOUTH);

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

        // ActionListener for the Enter button
        enterButton.addActionListener(e -> {
            clicker.playSound("src/Resources/click.wav", false);
            performLogin(usernameField);
        });

        // KeyListener for the Enter key press event on the username field
        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin(usernameField);
                }
            }
        });
    }

    /**
     * Performs the login action based on the provided username.
     *
     * @param usernameField the text field containing the username
     */
    private void performLogin(JTextField usernameField) {
        CardLayout cardLayout = (CardLayout) getParent().getLayout();
        // Code to retrieve the user from the database
        userDatabase userData = new userDatabase();
        String usernameInputted = usernameField.getText();
        currentUser = userData.findUser(usernameInputted);
        if (currentUser == null) {
            userData.createUser(usernameInputted);
            userData.exportDatabase();
            currentUser = userData.findUser(usernameInputted);
        }
        cardLayout.show(getParent(), "MAIN_MENU");
    }

    /**
     * Sets the current user.
     *
     * @param user the user to set as current user
     */
    public static void setCurrentUser(user user) {
        currentUser = user;
    }

    /**
     * @return the current user
     */
    public static user getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the CardLayout manager and parent panel for switching between panels.
     * @param cardLayout the CardLayout manager
     * @param cardPanel  the parent panel containing all card panels
     */
    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
    }
}
