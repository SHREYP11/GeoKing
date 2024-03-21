import javax.swing.*;
import java.awt.*;

public class ClassicModeScreen extends JPanel {
    public ClassicModeScreen() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel livesLabel = new JLabel("Lives: 3");
        livesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        livesLabel.setForeground(Color.BLACK);
        topPanel.add(livesLabel, BorderLayout.WEST);

        JButton hintButton = new JButton("Hint");
        hintButton.setPreferredSize(new Dimension(80, 30));
        hintButton.addActionListener(event -> {
            JOptionPane.showMessageDialog(this, "This is a hint.");
        });
        JPanel hintPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        hintPanel.setOpaque(false);
        hintPanel.add(hintButton);
        topPanel.add(hintPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon = new ImageIcon("src/Resources/Flags/Flag_of_Australia-512x256.png");
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
