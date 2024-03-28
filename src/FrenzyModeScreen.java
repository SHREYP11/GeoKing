import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FrenzyModeScreen extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField inputTextField;
    private Timer timer;
    private JLabel timerLabel;
    private int secondsLeft;

    private country Country;

    public FrenzyModeScreen(CardLayout cardLayout, JPanel cardPanel) {
        boolean mode = SettingsScreen.isFlagModeEnabled();
        this.cardLayout = cardLayout;
        this.cardPanel = cardPanel;

        levelDatabase levels = new levelDatabase();
        user currentUser = LoginScreen.getCurrentUser();
        int frenzyLevel = currentUser.getFrenzyLevel();
        Country = levels.selectLevel(frenzyLevel);
        String countyName = Country.getName();

        setLayout(new BorderLayout());
        setBackground(new Color(192, 192, 192));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(192, 192, 192));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            timer.stop();
            CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
            cardLayout1.show(getParent(), "MAIN_MENU");
            revalidate();
            repaint();
        });
        topPanel.add(exitButton, BorderLayout.WEST);

        timerLabel = new JLabel("Time Left: 30");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timerLabel.setForeground(Color.BLACK);
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(timerLabel, BorderLayout.CENTER);

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
        centerPanel.setBackground(new Color(192, 192, 192));

        String path = "";
        if (mode) {
            path = "src/Resources/Countries/" + countyName.toLowerCase() + "-silhouette.png";
        } else {
            path = "src/Resources/Flags/" + countyName + "Flag.png";
        }
        ImageIcon imageIcon = new ImageIcon(path);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        inputTextField = new JTextField();
        inputTextField.setHorizontalAlignment(JTextField.CENTER);
        inputTextField.setPreferredSize(new Dimension(inputTextField.getPreferredSize().width, 40));
        inputTextField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    processGuess();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        centerPanel.add(inputTextField, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JButton enterGuessButton = new JButton("Enter Guess");
        enterGuessButton.addActionListener(event -> processGuess());
        add(enterGuessButton, BorderLayout.SOUTH);

        secondsLeft = 30;
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                if (secondsLeft >= 0) {
                    timerLabel.setText("Time Left: " + secondsLeft);
                } else {
                    timer.stop();
                    gameOver();
                }
            }
        });
        timer.start();
    }

    private void processGuess() {
        String guess = inputTextField.getText().toLowerCase();
        user currentUser = LoginScreen.getCurrentUser();
        String countyName = Country.getName();

        if (Objects.equals(guess.toLowerCase(), countyName.toLowerCase())) {
            // Correct guess
            currentUser.incrementFrenzyLevel();
            userDatabase updateData = new userDatabase();
            updateData.findUser(currentUser.getName()).incrementFrenzyLevel();
            updateData.exportDatabase();
            int option = JOptionPane.showOptionDialog(this,
                    "Congratulations " + guess + " was the correct country.",
                    "Correct Guess",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Next Level", "Main Menu"},
                    "Main Menu");

            if (option == JOptionPane.YES_OPTION) {
                Component[] components = cardPanel.getComponents();
                for (Component component : components) {
                    if (component instanceof FrenzyModeScreen) {
                        cardPanel.remove(component);
                    }
                }
                FrenzyModeScreen frenzyModeScreen = new FrenzyModeScreen(cardLayout, cardPanel);
                cardPanel.add(frenzyModeScreen, "FrenzyModeScreen");
                cardLayout.show(cardPanel, "FrenzyModeScreen");

                revalidate();
                repaint();
            } else {
                // Return to the main menu
                CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
                cardLayout1.show(getParent(), "MAIN_MENU");
                revalidate();
                repaint();
            }
        } else {
            // Incorrect guess
            JOptionPane.showMessageDialog(this, "You guessed: " + guess + ", This was not the correct country.");
            inputTextField.setText("");
        }
    }

    private void gameOver() {
        JOptionPane.showMessageDialog(this, "Time's up! Game over.");
        timerLabel.setText("Time's Up!");
        CardLayout cardLayout1 = (CardLayout) getParent().getLayout();
        cardLayout1.show(getParent(), "MAIN_MENU");
        revalidate();
        repaint();
    }
}
