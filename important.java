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

    static void transitionToHomeContent(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setTitle("Dine Easy - Home");
        
        java.net.URL bgURL = important.class.getResource("/outlook.jpg");
        JLabel bgLabel = new JLabel();
        
        if (bgURL != null) {
            ImageIcon bgIcon = new ImageIcon(bgURL);
            Image scaledBg = bgIcon.getImage().getScaledInstance(
                    Toolkit.getDefaultToolkit().getScreenSize().width,
                    Toolkit.getDefaultToolkit().getScreenSize().height,
                    Image.SCALE_SMOOTH
            );
            bgLabel.setIcon(new ImageIcon(scaledBg));
        } else {
            bgLabel.setBackground(new Color(230, 230, 250));
            bgLabel.setOpaque(true);
        }
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boxWidth = (int) (screenSize.width * 0.70);
        int boxHeight = (int) (screenSize.height * 0.65);
        
        JPanel boxPanel = createBoxPanel(boxWidth, boxHeight);
        boxPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbcBox = new GridBagConstraints();
        gbcBox.insets = new Insets(20, 20, 20, 20);
        gbcBox.anchor = GridBagConstraints.CENTER;
        
        JLabel heading = new JLabel("Welcome to Dine Easy", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        heading.setForeground(Color.BLACK);
        gbcBox.gridx = 0;
        gbcBox.gridy = 0;
        gbcBox.gridwidth = 2;
        boxPanel.add(heading, gbcBox);
        
        JButton diningBtn = createStyledButton("Dining Area", new Color(100, 149, 237));
        gbcBox.gridx = 0;
        gbcBox.gridy = 1;
        gbcBox.gridwidth = 1;
        boxPanel.add(diningBtn, gbcBox);
        
        JButton kitchenBtn = createStyledButton("Kitchen", new Color(60, 179, 113));
        gbcBox.gridx = 1;
        gbcBox.gridy = 1;
        boxPanel.add(kitchenBtn, gbcBox);
        
        mainPanel.add(boxPanel);
        
        bgLabel.setLayout(new BorderLayout());
        bgLabel.add(mainPanel, BorderLayout.CENTER);
        
        frame.setContentPane(bgLabel);
        frame.revalidate();
        frame.repaint();
        
        diningBtn.addActionListener(e -> {
            frame.dispose();
            showDiningArea();
        });
        
        kitchenBtn.addActionListener(e -> {
            frame.dispose();
            kitchen.showKitchenInterface();
        });
    }
    
    // ================= DINING AREA =================
    static void showDiningArea() {
        JFrame frame = new JFrame("Dine Easy - Dining Area");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 245));
        
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        backBtn.addActionListener(e -> {
            frame.getContentPane().removeAll();
            transitionToHomeContent(frame);
        });
        topPanel.add(backBtn);
        
        JButton billBtn = new JButton("Bill");
        billBtn.setFont(new Font("Arial", Font.BOLD, 16));
        billBtn.setBackground(new Color(128, 0, 255));
        billBtn.setForeground(Color.WHITE);
        billBtn.setFocusPainted(false);
        billBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        billBtn.addActionListener(e -> showBillInterface(frame));
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(245, 245, 245));
        rightPanel.add(billBtn);
        
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(245, 245, 245));
        headerPanel.add(topPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        JLabel heading = new JLabel("Table List", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.add(heading);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JPanel tablePanel = new JPanel(new GridLayout(3, 3, 20, 20));
        tablePanel.setOpaque(false);
        
        for (int i = 1; i <= 9; i++) {
            JButton tableBtn = new JButton("Table " + i);
            styleTableButton(tableBtn);
            int tableNo = i;
            tableBtn.addActionListener(e -> showMenu(tableNo, frame));
            tablePanel.add(tableBtn);
        }
        
        centerPanel.add(tablePanel);
        
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    // ================= BILL INTERFACE =================
    static void showBillInterface(JFrame parentFrame) {
        String[] tableOptions = {"Table 1", "Table 2", "Table 3", "Table 4", "Table 5", 
                                 "Table 6", "Table 7", "Table 8", "Table 9"};
        
        String selectedTable = (String) JOptionPane.showInputDialog(
                parentFrame,
                "Select a table to view bill:",
                "Bill - Select Table",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tableOptions,
                tableOptions[0]);
        
        if (selectedTable != null) {
            int tableNo = Integer.parseInt(selectedTable.split(" ")[1]);
            showTableBill(tableNo, parentFrame);
        }
    }
    
    static void showTableBill(int tableNo, JFrame parentFrame) {
        if (!tableOrders.containsKey(tableNo) || tableOrders.get(tableNo).isEmpty()) {
            JOptionPane.showMessageDialog(parentFrame,
                    "Table " + tableNo + " is not under booking.\nNo orders have been placed.",
                    "Table Not Booked",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        Map<String, Integer> orders = tableOrders.get(tableNo);
        StringBuilder bill = new StringBuilder();
        bill.append("========== BILL ==========\n");
        bill.append("Table Number: ").append(tableNo).append("\n");
        bill.append("==========================\n\n");
        
        int totalAmount = 0;
        for (String item : orders.keySet()) {
            int qty = orders.get(item);
            int price = menu.get(item);
            int itemTotal = price * qty;
            totalAmount += itemTotal;
            
            bill.append(item).append("\n");
            bill.append("  Qty: ").append(qty).append(" x ₹").append(price);
            bill.append(" = ₹").append(itemTotal).append("\n\n");
        }
        
        bill.append("==========================\n");
        bill.append("TOTAL AMOUNT: ₹").append(totalAmount).append("\n");
        bill.append("==========================");
        
        JTextArea billArea = new JTextArea(bill.toString());
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billArea.setEditable(false);
        billArea.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(billArea);
        scrollPane.setPreferredSize(new Dimension(400, 500));
        
        JOptionPane.showMessageDialog(parentFrame, scrollPane,
                "Bill for Table " + tableNo,
                JOptionPane.PLAIN_MESSAGE);
    }


