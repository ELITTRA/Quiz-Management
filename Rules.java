import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rules extends JFrame implements ActionListener {
    String username;
    JButton startBtn, backBtn;

    Rules(String username) {
        this.username = username;

        setTitle("Quiz Rules - Welcome " + username);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Quiz Rules");
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setBounds(220, 20, 200, 30);
        add(title);

        JTextArea rulesText = new JTextArea();
        rulesText.setText(
            "1. You will have limited time to answer each question.\n" +
            "2. Each question carries equal marks.\n" +
            "3. Do not close the quiz window before finishing.\n" +
            "4. Click 'Start Quiz' when ready.\n" +
            "5. Your result will be displayed at the end."
        );
        rulesText.setEditable(false);
        rulesText.setFont(new Font("Arial", Font.PLAIN, 16));
        rulesText.setBackground(getBackground());
        rulesText.setBounds(50, 70, 500, 180);
        add(rulesText);

        startBtn = new JButton("Start Quiz");
        startBtn.setBounds(150, 300, 120, 30);
        startBtn.addActionListener(this);
        add(startBtn);

        backBtn = new JButton("Back");
        backBtn.setBounds(320, 300, 120, 30);
        backBtn.addActionListener(this);
        add(backBtn);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) {
            this.setVisible(false);
            new Quiz(username, "student"); // Make sure Quiz class accepts these parameters
        } else if (e.getSource() == backBtn) {
            this.setVisible(false);
            new Login();
        }
    }

    public static void main(String[] args) {
        new Rules("TestStudent"); // For testing
    }
}
