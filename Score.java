import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Score extends JFrame implements ActionListener {

    JLabel head;
    JLabel su;
    JButton btnLogout;
    String name;

    Score(String name, int score) {
        this.name = name;

        setBounds(600, 150, 750, 550);
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        setTitle("Score");

        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("score.png"));
        JLabel image = new JLabel(il);
        image.setBounds(0, 200, 300, 250);
        add(image);

        head = new JLabel("Thank you " + name + " for playing Simple Minds");
        head.setBounds(40, 30, 700, 30);
        head.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(head);

        su = new JLabel("Your Score is " + score);
        su.setBounds(350, 200, 300, 30);
        su.setFont(new Font("Tahoma", Font.PLAIN, 24));
        add(su);

        btnLogout = new JButton("Logout");
        btnLogout.setBounds(400, 250, 120, 30);
        btnLogout.setFont(new Font("Dialog", Font.PLAIN, 20));
        btnLogout.setBackground(Color.WHITE);
        btnLogout.addActionListener(this);
        add(btnLogout);

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == btnLogout) {
            // Dispose current window
            dispose();

            // Open login window
            new Login();
        }
    }

    public static void main(String[] args) {
        new Score("User", 0);
    }
}
