import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ClassicModeScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public ClassicModeScreen(CardLayout cardLayout, JPanel cardPanel) {
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        levelDatabase levels = new levelDatabase();
        // this is the level selection code
        user currentUser = LoginScreen.getCurrentUser();
        int classicLevel = currentUser.getClassicLevel();
        country Country = levels.selectLevel(classicLevel);
        String countyName = Country.getName();

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        // Add Exit Button to top left
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> cardLayout.show(cardPanel, "MainMenu")); // Exit to main
        topPanel.add(exitButton, BorderLayout.WEST);

        // Lives Counter in the top middle
        AtomicInteger lives = new AtomicInteger(5);
        JLabel livesLabel = new JLabel("Lives: " + lives);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        livesLabel.setForeground(Color.BLACK);
        livesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(livesLabel, BorderLayout.CENTER);

        JButton hintButton = new JButton("Hint");
        hintButton.setPreferredSize(new Dimension(80, 30));
        hintButton.addActionListener(event -> {
            JOptionPane.showMessageDialog(this, "This country is in " + Country.getContinent() + ".");
        });
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hintPanel.setOpaque(false);
        hintPanel.add(hintButton);
        topPanel.add(hintPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon("src/Resources/Countries/" + countyName.toLowerCase() + "-silhouette.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        JTextField inputTextField = new JTextField();
        inputTextField.setHorizontalAlignment(JTextField.CENTER);
        centerPanel.add(inputTextField, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JButton enterGuessButton = new JButton("Enter Guess");
        enterGuessButton.addActionListener(event -> {
            String guess = inputTextField.getText();
            if (Objects.equals(guess, countyName)){
                JOptionPane.showMessageDialog(this, "Congratulations " + guess + " was the correct country.");
                currentUser.incrementClassicLevel();
                userDatabase updateData = new userDatabase();
                updateData.findUser(currentUser.getName()).incrementClassicLevel();
                updateData.exportDatabase();
                cardLayout.show(cardPanel, "MainMenu");
            }
            else {
                lives.getAndDecrement();
                JOptionPane.showMessageDialog(this, "You guessed: " + guess + ", This was not the correct country.");
                inputTextField.setText("");

                // Update lives label to reflect the new value
                livesLabel.setText("Lives: " + lives);

                // Check if lives are depleted
                if (lives.get() <= 0) {
                    JOptionPane.showMessageDialog(this, "Game Over! You have run out of lives.");
                    cardLayout.show(cardPanel, "MainMenu");
                }
            }
        });
        add(enterGuessButton, BorderLayout.SOUTH);
    }
}
