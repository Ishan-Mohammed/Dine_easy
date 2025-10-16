package dine_easyyy_project;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class important {
    static Map<String, Integer> menu = new HashMap<>();
    static Map<Integer, Map<String, Integer>> tableOrders = new HashMap<>();
    
    public static void main(String[] args) {
        menu.put("Pizza", 200);
        menu.put("Burger", 120);
        menu.put("Pasta", 150);
        menu.put("Coffee", 80);
        menu.put("Ice Cream", 100);
        
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> showSplash());
    }
    
    // ================= SPLASH SCREEN =================
    static void showSplash() {
        JFrame splashFrame = new JFrame("Dine Easy - Welcome");
        splashFrame.setUndecorated(true);
        splashFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        splashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        splashFrame.getContentPane().setBackground(Color.BLACK);
        
        java.net.URL gifURL = important.class.getResource("/intro_dine.gif");
        JLabel gifLabel;
        
        if (gifURL != null) {
            ImageIcon gifIcon = new ImageIcon(gifURL);
            Image scaledGif = gifIcon.getImage().getScaledInstance(
                    Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height,
                    Image.SCALE_DEFAULT
            );
            gifLabel = new JLabel(new ImageIcon(scaledGif));
        } else {
            gifLabel = new JLabel("GIF not found: intro_dine.gif", SwingConstants.CENTER);
            gifLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
            gifLabel.setForeground(Color.RED);
        }
        
        gifLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gifLabel.setVerticalAlignment(SwingConstants.CENTER);
        splashFrame.add(gifLabel);
        splashFrame.setVisible(true);
        
        // Smooth transition after GIF completes
        int gifDuration = 5000; // 5 seconds
        Timer timer = new Timer(gifDuration, e -> {
            // Directly transition to login in the same frame
            transitionToLoginContent(splashFrame);
        });
        timer.setRepeats(false);
        timer.start();
    }
    
 static void transitionToLoginContent(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setTitle("Dine Easy - Login");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.dispose();
        frame.setUndecorated(false);
        frame.setVisible(true);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boxWidth = (int) (screenSize.width * 0.75);
        int boxHeight = (int) (screenSize.height * 0.75);
        
        JPanel mainPanel = createBackgroundPanel();
        JPanel boxPanel = createBoxPanel(boxWidth, boxHeight);
        
        GridBagConstraints gbcBox = new GridBagConstraints();
        gbcBox.insets = new Insets(15, 15, 15, 15);
        gbcBox.fill = GridBagConstraints.BOTH;
        gbcBox.weightx = 1.0;
        gbcBox.weighty = 1.0;
        
        JLabel imageLabel = createImagePlaceholder(boxWidth, boxHeight);
        gbcBox.gridx = 0;
        gbcBox.gridy = 0;
        gbcBox.gridheight = 6;
        boxPanel.add(imageLabel, gbcBox);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(10, 10, 10, 10);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        gbcForm.gridx = 0;
        
        JLabel signInLabel = new JLabel("Sign In");
        signInLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        gbcForm.gridy = 0;
        formPanel.add(signInLabel, gbcForm);
        
        JTextField usernameField = new JTextField(20);
        usernameField.setBorder(BorderFactory.createTitledBorder("Restaurant Name"));
        gbcForm.gridy = 1;
        formPanel.add(usernameField, gbcForm);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        gbcForm.gridy = 2;
        formPanel.add(passwordField, gbcForm);
        
        JCheckBox rememberBox = new JCheckBox("Remember me");
        gbcForm.gridy = 3;
        formPanel.add(rememberBox, gbcForm);
        
        JButton loginBtn = createPrimaryButton("Sign In");
        gbcForm.gridy = 4;
        formPanel.add(loginBtn, gbcForm);
        
        JLabel createAccount = createLinkLabel("New here? Create an Account");
        gbcForm.gridy = 5;
        formPanel.add(createAccount, gbcForm);
        
        gbcBox.gridx = 1;
        gbcBox.gridy = 0;
        gbcBox.gridheight = 6;
        boxPanel.add(formPanel, gbcBox);
        
        mainPanel.add(boxPanel);
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
        
        loginBtn.addActionListener(e -> transitionToHomeContent(frame));
        
        createAccount.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                transitionToSignupContent(frame);
            }
        });
    }
    
    static void transitionToSignupContent(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setTitle("Dine Easy - Sign Up");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boxWidth = (int) (screenSize.width * 0.75);
        int boxHeight = (int) (screenSize.height * 0.75);
        
        JPanel mainPanel = createBackgroundPanel();
        JPanel boxPanel = createBoxPanel(boxWidth, boxHeight);
        
        GridBagConstraints gbcBox = new GridBagConstraints();
        gbcBox.insets = new Insets(15, 15, 15, 15);
        gbcBox.fill = GridBagConstraints.BOTH;
        gbcBox.weightx = 1.0;
        gbcBox.weighty = 1.0;
        
        JLabel imageLabel = createImagePlaceholder(boxWidth, boxHeight);
        gbcBox.gridx = 0;
        gbcBox.gridy = 0;
        gbcBox.gridheight = 7;
        boxPanel.add(imageLabel, gbcBox);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.insets = new Insets(10, 10, 10, 10);
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        gbcForm.gridx = 0;
        
        JLabel signupLabel = new JLabel("Create Account");
        signupLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        gbcForm.gridy = 0;
        formPanel.add(signupLabel, gbcForm);
        
        JTextField nameField = new JTextField(20);
        nameField.setBorder(BorderFactory.createTitledBorder("Full Name"));
        gbcForm.gridy = 1;
        formPanel.add(nameField, gbcForm);
        
        JTextField emailField = new JTextField(20);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        gbcForm.gridy = 2;
        formPanel.add(emailField, gbcForm);
        
        JPasswordField passField = new JPasswordField(20);
        passField.setBorder(BorderFactory.createTitledBorder("Password"));
        gbcForm.gridy = 3;
        formPanel.add(passField, gbcForm);
        
        JButton signupBtn = createPrimaryButton("Sign Up");
        gbcForm.gridy = 4;
        formPanel.add(signupBtn, gbcForm);
        
        JLabel already = createLinkLabel("Already have an account? Login");
        gbcForm.gridy = 5;
        formPanel.add(already, gbcForm);
        
        gbcBox.gridx = 1;
        gbcBox.gridy = 0;
        gbcBox.gridheight = 7;
        boxPanel.add(formPanel, gbcBox);
        
        mainPanel.add(boxPanel);
        frame.getContentPane().add(mainPanel);
        frame.revalidate();
        frame.repaint();
        
        signupBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            transitionToLoginContent(frame);
        });
        
        already.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                transitionToLoginContent(frame);
            }
        });
    }
