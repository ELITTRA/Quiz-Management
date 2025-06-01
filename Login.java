import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

public class Login extends JFrame implements ActionListener {
    JButton ln, re, reg;
    JTextField tn;
    JPasswordField pt;

    Login() {
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("dip.jpg"));
        JLabel image = new JLabel(il);
        image.setBounds(0, 0, 600, 500);
        add(image);

        JLabel head = new JLabel("Quiz Time");
        head.setBounds(830, 90, 300, 45);
        head.setFont(new Font("Dialogs", Font.BOLD, 20));
        head.setForeground(Color.BLUE);
        add(head);

        JLabel name = new JLabel("Enter your Name");
        name.setBounds(810, 150, 300, 20);
        name.setFont(new Font("Viner Hand ITC", Font.BOLD, 20));
        name.setForeground(Color.BLUE);
        add(name);

        tn = new JTextField();
        tn.setBounds(735, 180, 300, 25);
        tn.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(tn);

        JLabel pass = new JLabel("Enter your Password");
        pass.setBounds(810, 220, 300, 20);
        pass.setFont(new Font("Viner Hand ITC", Font.BOLD, 20));
        pass.setForeground(Color.BLUE);
        add(pass);

        pt = new JPasswordField();
        pt.setBounds(735, 250, 300, 25);
        pt.setFont(new Font("Times New Roman", Font.BOLD, 20));
        add(pt);

        ln = new JButton("Login");
        ln.setBounds(735, 290, 120, 25);
        ln.setBackground(Color.BLUE);
        ln.setForeground(Color.WHITE);
        ln.addActionListener(this);
        add(ln);

        re = new JButton("Close");
        re.setBounds(915, 290, 120, 25);
        re.setBackground(Color.BLUE);
        re.setForeground(Color.WHITE);
        re.addActionListener(this);
        add(re);

        reg = new JButton("Register");
        reg.setBounds(825, 350, 120, 25);
        reg.setBackground(Color.GREEN.darker());
        reg.setForeground(Color.WHITE);
        reg.addActionListener(this);
        add(reg);

        setSize(1200, 500);
        setLocation(200, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == ln) {
            String username = tn.getText().trim();
            String password = new String(pt.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
                return;
            }

            Map<String, String> users = UserAuth.loadUsers(); // username -> "password|role"

            if (users.containsKey(username)) {
                String[] passRole = users.get(username).split("\\|");
                if (passRole.length == 2) {
                    String storedPass = passRole[0];
                    String role = passRole[1];

                    if (storedPass.equals(password)) {
                        setVisible(false);
                        if (role.equalsIgnoreCase("admin")) {
                              new AdminDashboard(username);
                      } else if (role.equalsIgnoreCase("student")) {
                              new Rules(username);  // Open Rules frame first for student
                      } else {
                            JOptionPane.showMessageDialog(this, "Unknown user role");
                            setVisible(true);
                      }

                        return;
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "Invalid username or password");
        } else if (ae.getSource() == re) {
            setVisible(false);
        } else if (ae.getSource() == reg) {
            setVisible(false);
            new Register();
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
