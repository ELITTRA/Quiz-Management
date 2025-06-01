import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class AdminDashboard extends JFrame implements ActionListener {

    private JTextField questionField, option1Field, option2Field, option3Field, option4Field, correctAnswerField, timerField;
    private JButton saveButton, backButton;
    String name;

    public AdminDashboard(String name) {
        this.name=name;
        setTitle("Admin Dashboard - Add Quiz Question");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));

        // Labels and text fields
        add(new JLabel("Question:"));
        questionField = new JTextField();
        add(questionField);

        add(new JLabel("Option 1:"));
        option1Field = new JTextField();
        add(option1Field);

        add(new JLabel("Option 2:"));
        option2Field = new JTextField();
        add(option2Field);

        add(new JLabel("Option 3:"));
        option3Field = new JTextField();
        add(option3Field);

        add(new JLabel("Option 4:"));
        option4Field = new JTextField();
        add(option4Field);

        add(new JLabel("Correct Answer (exactly as one option):"));
        correctAnswerField = new JTextField();
        add(correctAnswerField);

        add(new JLabel("Quiz Timer (in seconds):")); 
        timerField = new JTextField();
        add(timerField);

        saveButton = new JButton("Save Question");
        saveButton.addActionListener(this);
        add(saveButton);

        backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            dispose();
            new Login();  // Replace with your login class
        });
        add(backButton);
       
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String question = questionField.getText().trim();
        String opt1 = option1Field.getText().trim();
        String opt2 = option2Field.getText().trim();
        String opt3 = option3Field.getText().trim();
        String opt4 = option4Field.getText().trim();
        String correct = correctAnswerField.getText().trim();
        String timerInput = timerField.getText().trim();

        if (question.isEmpty() || opt1.isEmpty() || opt2.isEmpty() || opt3.isEmpty() || opt4.isEmpty() || correct.isEmpty() || timerInput.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!correct.equals(opt1) && !correct.equals(opt2) && !correct.equals(opt3) && !correct.equals(opt4)) {
            JOptionPane.showMessageDialog(this, "Correct answer must match one of the options exactly.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int timerSeconds = Integer.parseInt(timerInput);
            if (timerSeconds <= 0) throw new NumberFormatException();

            saveQuestionToFile(question, opt1, opt2, opt3, opt4, correct);
            saveTimerToFile(timerSeconds);

            JOptionPane.showMessageDialog(this, "Question and timer saved successfully!");

            // Clear fields
            questionField.setText("");
            option1Field.setText("");
            option2Field.setText("");
            option3Field.setText("");
            option4Field.setText("");
            correctAnswerField.setText("");
            timerField.setText("");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid positive number for the timer.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveQuestionToFile(String question, String opt1, String opt2, String opt3, String opt4, String correct) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("questions.txt", true))) {
            bw.write(question);
            bw.newLine();
            bw.write(opt1);
            bw.newLine();
            bw.write(opt2);
            bw.newLine();
            bw.write(opt3);
            bw.newLine();
            bw.write(opt4);
            bw.newLine();
            bw.write(correct);
            bw.newLine();
            bw.write("---");
            bw.newLine();
        }
    }

    private void saveTimerToFile(int seconds) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("timer.txt"))) {
            bw.write(String.valueOf(seconds));
        }
    }

    public static void main(String[] args) {
        new AdminDashboard("username");
    }
}
