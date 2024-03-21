import javax.swing.*;
import java.awt.*;

class LoginScreen extends JPanel {
    public LoginScreen() {
        setLayout(new BorderLayout(0, 50));

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("GEOKING"));

        JPanel containerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel loginPanel = new JPanel(new FlowLayout());
        loginPanel.add(new JLabel("USERNAME:"));

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(300, 40));
        loginPanel.add(usernameField);

        containerPanel.add(loginPanel, gbc);

        JPanel buttonPanel = new JPanel();
        JButton enterButton = new JButton("ENTER");
        buttonPanel.add(enterButton);

        add(titlePanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        enterButton.addActionListener(e -> {
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "MAIN_MENU");
        });
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
    }
}

class MainMenuScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public MainMenuScreen(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

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

        Dimension buttonSize = new Dimension(800, 100);
        classicModeButton.setPreferredSize(buttonSize);
        frenzyModeButton.setPreferredSize(buttonSize);
        leaderboardButton.setPreferredSize(buttonSize);
        settingsButton.setPreferredSize(buttonSize);

        classicModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        frenzyModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        add(buttonPanel, BorderLayout.CENTER);

        classicModeButton.addActionListener(e -> {
            removeAll();
            add(new ClassicModeScreen(), BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        settingsButton.addActionListener(e -> this.cardLayout.show(this.cardPanel, "SETTINGS"));
    }

    public void setCardLayout(CardLayout cardLayout, JPanel cardPanel) {
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
