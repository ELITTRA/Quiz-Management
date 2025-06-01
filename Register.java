import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Register extends JFrame implements ActionListener {
    JTextField tfUsername;
    JPasswordField pfPassword;
    JComboBox<String> roleCombo;
    JButton btnRegister, btnBack;

    Register() {
        setLayout(null);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("User Registration");

         

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 50, 100, 30);
        lblUser.setFont(new Font("Mongolian Baiti",Font.BOLD,16));
        lblUser.setForeground(Color.BLACK);
        add(lblUser);

        tfUsername = new JTextField();
        tfUsername.setBounds(150, 50, 180, 30);
        add(tfUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 100, 100, 30);
        lblPass.setFont(new Font("Mongolian Baiti",Font.BOLD,16));
        lblPass.setForeground(Color.BLACK);
        add(lblPass);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(150, 100, 180, 30);
        add(pfPassword);

        JLabel lblRole = new JLabel("Select Role:");
        lblRole.setBounds(50, 150, 100, 30);
        lblRole.setFont(new Font("Mongolian Baiti",Font.BOLD,16));
        lblRole.setForeground(Color.BLACK);
        add(lblRole);

        roleCombo = new JComboBox<>(new String[]{"student", "admin"});
        roleCombo.setBounds(150, 150, 180, 30);
        add(roleCombo);

        btnRegister = new JButton("Register");
        btnRegister.setBounds(50, 220, 120, 30);
        btnRegister.setBackground(Color.BLUE);
        btnRegister.setForeground(Color.BLACK);
        btnRegister.addActionListener(this);
        add(btnRegister);

        btnBack = new JButton("Back");
        btnBack.setBounds(210, 220, 120, 30);
        btnBack.setBackground(Color.BLUE);
        btnBack.setForeground(Color.BLACK);
        btnBack.addActionListener(this);
        add(btnBack); 

        ImageIcon il = new ImageIcon(ClassLoader.getSystemResource("son.jpg"));
        JLabel image = new JLabel(il);
        image.setBounds(0, 0, 400, 1000);
        add(image);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRegister) {
            String username = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter username and password");
                return;
            }

            // Save to file in format: username,password,role
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
                bw.write(username + "," + password + "," + role);
                bw.newLine();
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                tfUsername.setText("");
                pfPassword.setText("");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving user data");
            }
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new Login();  // Open login window again
        }
          
    }

    public static void main(String[] args) {
        new Register();
    }
}
