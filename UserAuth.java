import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class UserAuth extends JFrame implements ActionListener {
    JTextField tfUsername;
    JPasswordField pfPassword;
    JButton btnLogin;

    private static final String FILE_NAME = "users.txt";

    public static Map<String, String> loadUsers() {
        Map<String, String> users = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    users.put(username, password + "|" + role);
                }
            }
        } catch (IOException e) {
            System.out.println("User file not found or error reading file.");
        }
        return users;
    }

    // ✅ Static method used by other classes for authentication
    public static String authenticateUserRole(String username, String password) {
        Map<String, String> users = loadUsers();
        if (users.containsKey(username)) {
            String[] passRole = users.get(username).split("\\|");
            if (passRole.length == 2) {
                String storedPass = passRole[0];
                String role = passRole[1];
                if (storedPass.equals(password)) {
                    return role;
                }
            }
        }
        return null;
    }

    public UserAuth() {
        setLayout(null);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("User Login");

        JLabel lblUser = new JLabel("Username:");
        lblUser.setBounds(50, 50, 100, 30);
        add(lblUser);

        tfUsername = new JTextField();
        tfUsername.setBounds(150, 50, 180, 30);
        add(tfUsername);

        JLabel lblPass = new JLabel("Password:");
        lblPass.setBounds(50, 100, 100, 30);
        add(lblPass);

        pfPassword = new JPasswordField();
        pfPassword.setBounds(150, 100, 180, 30);
        add(pfPassword);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(150, 150, 100, 30);
        btnLogin.addActionListener(this);
        add(btnLogin);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnLogin) {
            String username = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword()).trim();

            String role = authenticateUserRole(username, password);

            if (role != null) {
                JOptionPane.showMessageDialog(this, "Login successful! Welcome " + username + " (" + role + ")");
                this.dispose();

                if ("admin".equalsIgnoreCase(role)) {
                    new AdminDashboard(username);
                } else if ("student".equalsIgnoreCase(role)) {
                    new Rules(username); // ✅ Change this if you want to launch Rules.java first
                } else {
                    JOptionPane.showMessageDialog(this, "Unknown user role: " + role);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password");
            }
        }
    }

    public static void main(String[] args) {
        new UserAuth();
    }
}

// ✅ Example AdminDashboard placeholder
class AdminDashboard extends JFrame {
    AdminDashboard(String username) {
        setTitle("Admin Dashboard - Welcome " + username);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel lbl = new JLabel("This is Admin Dashboard");
        lbl.setFont(new Font("Arial", Font.BOLD, 20));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl);

        setVisible(true);
    }
}








