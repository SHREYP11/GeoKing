import javax.swing.*;
import java.awt.*;

public class ClassicModeScreen extends JPanel {
    public ClassicModeScreen() {
        levelDatabase levels = new levelDatabase();
        // this is the level selection code
        user currentUser = LoginScreen.getCurrentUser();
        int classicLevel = currentUser.getClassicLevel();
        country Country = levels.selectLevel(classicLevel);
        String countyName = Country.getName();

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel livesLabel = new JLabel("Lives: 3");
        livesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        livesLabel.setForeground(Color.BLACK);
        topPanel.add(livesLabel, BorderLayout.WEST);

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
            JOptionPane.showMessageDialog(this, "You guessed: " + guess);
            inputTextField.setText("");
        });
        add(enterGuessButton, BorderLayout.SOUTH);
    }
}
