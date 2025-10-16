// NOTE: This class now calls methods from the kitchen.java class.

package dine_easyyy_project;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
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
        
        int gifDuration = 5000; // 5 seconds
        Timer timer = new Timer(gifDuration, e -> {
            transitionToLoginContent(splashFrame);
        });
        timer.setRepeats(false);
        timer.start();
    }

    // the efficient methoed 
    
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
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
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
        
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                        "Please enter both username and password!", 
                        "Login Error", 
                        JOptionPane.ERROR_MESSAGE);
            } else {
                if (DatabaseManager.validateUser(username, password)) {
                    transitionToHomeContent(frame);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                            "Invalid username or password!", 
                            "Login Failed", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
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
        nameField.setBorder(BorderFactory.createTitledBorder("Username"));
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
            String username = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passField.getPassword());
            
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                        "Please fill in all fields!", 
                        "Signup Error", 
                        JOptionPane.ERROR_MESSAGE);
                return; 
            }
            
            try {
                DatabaseManager.registerUser(username, email, password);
                
                JOptionPane.showMessageDialog(frame, 
                        "Account created successfully!\nYou can now login.", 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);
                transitionToLoginContent(frame); 
                
            } catch (SQLException ex) {
                if (ex.getErrorCode() == 1062) { 
                    JOptionPane.showMessageDialog(frame, 
                            "Username or email already exists! Please choose another.", 
                            "Signup Failed", 
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, 
                            "A database error occurred: " + ex.getMessage(), 
                            "Database Error", 
                            JOptionPane.ERROR_MESSAGE);
                }
            }
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

        // flow happening
        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 245));
        
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        
        backBtn.addActionListener(e -> {
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
        
        // This displays the bill and waits for the user to click OK
        JOptionPane.showMessageDialog(parentFrame, scrollPane,
                "Bill for Table " + tableNo,
                JOptionPane.PLAIN_MESSAGE);
                
        // ===== NEW: SEND ORDER TO KITCHEN AFTER BILL IS VIEWED =====
        kitchen.receiveOrder(tableNo, orders);
    }
    
    // ================= MENU =================
    static void showMenu(int tableNo, JFrame parentFrame) {
        JFrame menuFrame = new JFrame("Table " + tableNo + " - Menu");
        menuFrame.setSize(500, 600);
        menuFrame.setLocationRelativeTo(parentFrame);
        menuFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        menuFrame.setLayout(new BorderLayout());
        
        JLabel heading = new JLabel("Select Food - Table " + tableNo, SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        menuFrame.add(heading, BorderLayout.NORTH);
        
        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        Map<String, JSpinner> quantitySpinners = new HashMap<>();
        
        for (String item : menu.keySet()) {
            int price = menu.get(item);
            JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
            itemPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            JLabel itemLabel = new JLabel(item + " - ₹" + price);
            itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            
            JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
            qtySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            quantitySpinners.put(item, qtySpinner);
            
            itemPanel.add(itemLabel, BorderLayout.WEST);
            itemPanel.add(qtySpinner, BorderLayout.EAST);
            menuPanel.add(itemPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        menuFrame.add(scrollPane, BorderLayout.CENTER);
        
        JButton confirmBtn = createPrimaryButton("Confirm Order");
        confirmBtn.setPreferredSize(new Dimension(200, 50));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(confirmBtn);
        
        confirmBtn.addActionListener(e -> {
            StringBuilder order = new StringBuilder("Order for Table " + tableNo + ":\n\n");
            boolean selected = false;
            int totalAmount = 0;
            
            Map<String, Integer> tableOrder = new HashMap<>();
            
            for (String item : quantitySpinners.keySet()) {
                int qty = (Integer) quantitySpinners.get(item).getValue();
                if (qty > 0) {
                    int itemTotal = menu.get(item) * qty;
                    totalAmount += itemTotal;
                    order.append("- ").append(item)
                            .append(" x ").append(qty)
                            .append(" (₹").append(itemTotal).append(")\n");
                    selected = true;
                    
                    tableOrder.put(item, qty);
                }
            }
            
            if (selected) {
                order.append("\nTotal: ₹").append(totalAmount);
                
                tableOrders.put(tableNo, tableOrder);
                
                try {
                    DatabaseManager.saveOrder(tableNo, tableOrder, menu);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(menuFrame,
                            "Error: Could not save order to database.\n" + ex.getMessage(),
                            "Database Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                
                // ===== MOVED: Order is now sent after viewing the bill =====
                // kitchen.receiveOrder(tableNo, tableOrder);
                
                JOptionPane.showMessageDialog(menuFrame, order.toString(), 
                        "Order Confirmed", JOptionPane.INFORMATION_MESSAGE);
                menuFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(menuFrame, 
                        "Please select at least one item!", 
                        "No Items Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        menuFrame.add(buttonPanel, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
    }
    
    // ================= COMMON HELPERS =================
    static JPanel createBackgroundPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(230, 230, 250));
        return mainPanel;
    }
    
    static JPanel createBoxPanel(int w, int h) {
        JPanel box = new JPanel(new GridBagLayout());
        box.setBackground(Color.WHITE);
        box.setPreferredSize(new Dimension(w, h));
        box.setBorder(BorderFactory.createLineBorder(new Color(128, 0, 255), 3, true));
        return box;
    }
    
    static JLabel createImagePlaceholder(int boxWidth, int boxHeight) {
        java.net.URL imgURL = important.class.getResource("/logo.png");
        JLabel imgLabel;
        
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image scaledImage = icon.getImage().getScaledInstance(
                    boxWidth / 2 - 40,
                    boxHeight - 100,
                    Image.SCALE_SMOOTH
            );
            imgLabel = new JLabel(new ImageIcon(scaledImage));
        } else {
            imgLabel = new JLabel("Image not found: logo.png", SwingConstants.CENTER);
            imgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            imgLabel.setForeground(Color.RED);
        }
        
        imgLabel.setPreferredSize(new Dimension(boxWidth / 2 - 40, boxHeight - 100));
        imgLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.CENTER);
        return imgLabel;
    }
    
    static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(128, 0, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    static JLabel createLinkLabel(String text) {
        JLabel lbl = new JLabel("<HTML><FONT color='#8000ff'><U>" + text + "</U></FONT></HTML>");
        lbl.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return lbl;
    }
    
    static JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setPreferredSize(new Dimension(250, 80));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    static void styleTableButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(255, 228, 181));
        button.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
    }
}


