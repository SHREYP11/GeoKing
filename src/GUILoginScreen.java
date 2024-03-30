import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
class GUILoginScreen extends JPanel {

    public static user currentUser;
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

        JPanel loginPanel = new JPanel(new BorderLayout()); // Use BorderLayout for loginPanel
        loginPanel.setBackground(new Color(192, 192, 192));

        // Create a label for the image
        JLabel imageLabel = new JLabel(new ImageIcon("src/Resources/logo.png"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER); // Center the image
        loginPanel.add(imageLabel, BorderLayout.CENTER); // Add image label to the center of loginPanel

        JPanel usernamePanel = new JPanel(new BorderLayout()); // Use BorderLayout for usernamePanel
        usernamePanel.setBackground(new Color(192, 192, 192));
        JLabel usernameLabel = new JLabel("USERNAME");
        usernameLabel.setHorizontalAlignment(JLabel.CENTER); // Center the username label
        usernamePanel.add(usernameLabel, BorderLayout.NORTH); // Add usernameLabel to the top of usernamePanel

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        usernamePanel.add(usernameField, BorderLayout.CENTER); // Add usernameField to the center of usernamePanel

        loginPanel.add(usernamePanel, BorderLayout.SOUTH); // Add usernamePanel to the bottom of loginPanel

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


    // Method to perform login action
    private void performLogin(JTextField usernameField) {
        CardLayout cardLayout = (CardLayout) getParent().getLayout();
        // this is the code to grab the user from database
        userDatabase userData = new userDatabase();
        String usernameInputed = usernameField.getText();
        currentUser = userData.findUser(usernameInputed);
        if (currentUser == null) {
            userData.createUser(usernameInputed);
            userData.exportDatabase();
            currentUser = userData.findUser(usernameInputed);
        }
        cardLayout.show(getParent(), "MAIN_MENU");
    }
    // Static method to set the current user
    public static void setCurrentUser(user user) {
        currentUser = user;
    }

    // Static method to get the current user
    public static user getCurrentUser() {
        return currentUser;
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
    }
}
