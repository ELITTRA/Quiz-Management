import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class Quiz extends JFrame implements ActionListener {

    private ArrayList<String[]> questionsList = new ArrayList<>();
    private int count = 0;
    private int score = 0;
    private String name;
    private String role;
    private int timer = 15;
    private int ans_given = 0;

    private JLabel qu, que;
    private JRadioButton q1, q2, q3, q4;
    private JButton nextBtn, lifelineBtn, submitBtn;
    private ButtonGroup grp;

    private String userAnswers[];  // store user's answers

    public Quiz(String name, String role) {
        this.name = name;
        this.role = role;

        // Load questions from file
        loadQuestions();

        if (questionsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No questions loaded! Contact Admin.");
            System.exit(0);
        }

        userAnswers = new String[questionsList.size()];

        setBounds(50, 0, 1440, 850);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);

        // Image (optional)
        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("quiz.jpg"));
        JLabel image = new JLabel(il);
        image.setBounds(0, 0, 1440, 392);
        add(image);

        qu = new JLabel();
        qu.setBounds(100, 450, 50, 30);
        qu.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(qu);

        que = new JLabel();
        que.setBounds(150, 450, 900, 30);
        que.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(que);

        q1 = new JRadioButton();
        q1.setBounds(170, 520, 700, 30);
        q1.setBackground(Color.WHITE);
        q1.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(q1);

        q2 = new JRadioButton();
        q2.setBounds(170, 560, 700, 30);
        q2.setBackground(Color.WHITE);
        q2.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(q2);

        q3 = new JRadioButton();
        q3.setBounds(170, 600, 700, 30);
        q3.setBackground(Color.WHITE);
        q3.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(q3);

        q4 = new JRadioButton();
        q4.setBounds(170, 640, 700, 30);
        q4.setBackground(Color.WHITE);
        q4.setFont(new Font("Dialog", Font.PLAIN, 20));
        add(q4);

        grp = new ButtonGroup();
        grp.add(q1);
        grp.add(q2);
        grp.add(q3);
        grp.add(q4);

        nextBtn = new JButton("Next");
        nextBtn.setBounds(1100, 550, 200, 40);
        nextBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
        nextBtn.setBackground(new Color(30, 144, 255));
        nextBtn.setForeground(Color.WHITE);
        nextBtn.addActionListener(this);
        add(nextBtn);

        lifelineBtn = new JButton("50-50 Lifeline");
        lifelineBtn.setBounds(1100, 630, 200, 40);
        lifelineBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lifelineBtn.setBackground(new Color(30, 144, 255));
        lifelineBtn.setForeground(Color.WHITE);
        lifelineBtn.addActionListener(this);
        add(lifelineBtn);

        submitBtn = new JButton("Submit");
        submitBtn.setBounds(1100, 710, 200, 40);
        submitBtn.setFont(new Font("Tahoma", Font.PLAIN, 22));
        submitBtn.setBackground(new Color(30, 144, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.addActionListener(this);
        submitBtn.setEnabled(false);
        add(submitBtn);

        // If role is admin, disable quiz
        if ("admin".equalsIgnoreCase(role)) {
            lifelineBtn.setEnabled(false);
            nextBtn.setEnabled(false);
            submitBtn.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Admin cannot take the quiz.");
            // Optionally redirect to admin dashboard here
        } else {
            startQuestion(count);
        }

        setVisible(true);
    }

    // Load questions from questions.txt file
    private void loadQuestions() {
        try (BufferedReader br = new BufferedReader(new FileReader("questions.txt"))) {
            String line;
            ArrayList<String> questionBlock = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.equals("---")) {
                    if (questionBlock.size() == 6) {
                        // question, opt1, opt2, opt3, opt4, correctAnswer
                        String[] q = questionBlock.toArray(new String[6]);
                        questionsList.add(q);
                    }
                    questionBlock.clear();
                } else {
                    questionBlock.add(line);
                }
            }
            // For last question if no trailing ---
            if (questionBlock.size() == 6) {
                String[] q = questionBlock.toArray(new String[6]);
                questionsList.add(q);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == nextBtn) {
            saveUserAnswer();
            count++;
            if (count == questionsList.size() - 1) {
                nextBtn.setEnabled(false);
                submitBtn.setEnabled(true);
            }
            startQuestion(count);

            // Enable lifeline again
            lifelineBtn.setEnabled(true);
        } else if (ae.getSource() == lifelineBtn) {
            // simple lifeline disables two wrong options randomly
            applyLifeline();
            lifelineBtn.setEnabled(false);
        } else if (ae.getSource() == submitBtn) {
            saveUserAnswer();
            calculateScore();
            setVisible(false);
            new Score(name, score); // Assuming you have a Score class for result display
        }
    }

    private void saveUserAnswer() {
        ButtonModel selected = grp.getSelection();
        if (selected != null) {
            userAnswers[count] = selected.getActionCommand();
        } else {
            userAnswers[count] = "";
        }
    }

    private void startQuestion(int index) {
        qu.setText("" + (index + 1) + ".");
        String[] q = questionsList.get(index);
        que.setText(q[0]);
        q1.setText(q[1]);
        q1.setActionCommand(q[1]);
        q2.setText(q[2]);
        q2.setActionCommand(q[2]);
        q3.setText(q[3]);
        q3.setActionCommand(q[3]);
        q4.setText(q[4]);
        q4.setActionCommand(q[4]);

        grp.clearSelection();

        // Reset timer & flags if you want to implement timer (optional)
    }

    private void applyLifeline() {
        String[] currentQ = questionsList.get(count);
        String correctAnswer = currentQ[5];
        // Disable two wrong answers randomly
        ArrayList<JRadioButton> options = new ArrayList<>(Arrays.asList(q1, q2, q3, q4));
        // Remove correct answer from disabling candidates
        options.removeIf(rb -> rb.getText().equals(correctAnswer));

        // Disable two random wrong options
        Collections.shuffle(options);
        int disabled = 0;
        for (JRadioButton rb : options) {
            rb.setEnabled(false);
            disabled++;
            if (disabled == 2) break;
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questionsList.size(); i++) {
            String userAnswer = userAnswers[i];
            String correctAnswer = questionsList.get(i)[5];
            if (userAnswer != null && userAnswer.equals(correctAnswer)) {
                score += 10; // 10 points per correct
            }
        }
    }

    // Main for testing student quiz only
    public static void main(String[] args) {
        new Quiz("Student", "student");
    }
}
