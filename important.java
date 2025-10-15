package dine_easyyy_project;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class important {
    static Map<String, Integer> menu = new HashMap<>();

    public static void main(String[] args) {
        menu.put("Pizza", 200);
        menu.put("Burger", 120);
        menu.put("Pasta", 150);
        menu.put("Coffee", 80);
        menu.put("Ice Cream", 100);
        showLogin();
    }

    // ========== LOGIN PAGE ==========
    static void showLogin() {
        JFrame loginFrame = new JFrame("Dine Easy - Login");
        loginFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boxWidth = (int) (screenSize.width * 0.75);
        int boxHeight = (int) (screenSize.height * 0.75);

        JPanel mainPanel = createBackgroundPanel();

        JPanel boxPanel = createBoxPanel(boxWidth, boxHeight);
        GridBagConstraints gbcBox = new GridBagConstraints();
        gbcBox.insets = new Insets(15, 15, 15, 15);
        gbcBox.fill = GridBagConstraints.BOTH;

        // IMAGE
        JLabel imageLabel = createImagePlaceholder(boxWidth, boxHeight);
        gbcBox.gridx = 0; gbcBox.gridy = 0; gbcBox.gridheight = 6;
        boxPanel.add(imageLabel, gbcBox);

        // FORM
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

        gbcBox.gridx = 1; gbcBox.gridy = 0; gbcBox.gridheight = 6;
        boxPanel.add(formPanel, gbcBox);

        mainPanel.add(boxPanel);
        loginFrame.add(mainPanel);
        loginFrame.setVisible(true);

        // Actions
        loginBtn.addActionListener(e -> {
            loginFrame.dispose();
            showHome();
        });

        createAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                loginFrame.dispose();
                showSignup();
            }
        });
    }

    // ========== SIGNUP PAGE ==========
    static void showSignup() {
        JFrame signupFrame = new JFrame("Dine Easy - Sign Up");
        signupFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        signupFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boxWidth = (int) (screenSize.width * 0.75);
        int boxHeight = (int) (screenSize.height * 0.75);

        JPanel mainPanel = createBackgroundPanel();

        JPanel boxPanel = createBoxPanel(boxWidth, boxHeight);
        GridBagConstraints gbcBox = new GridBagConstraints();
        gbcBox.insets = new Insets(15, 15, 15, 15);
        gbcBox.fill = GridBagConstraints.BOTH;

        // IMAGE
        JLabel imageLabel = createImagePlaceholder(boxWidth, boxHeight);
        gbcBox.gridx = 0; gbcBox.gridy = 0; gbcBox.gridheight = 7;
        boxPanel.add(imageLabel, gbcBox);

        // FORM
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

        gbcBox.gridx = 1; gbcBox.gridy = 0; gbcBox.gridheight = 7;
        boxPanel.add(formPanel, gbcBox);

        mainPanel.add(boxPanel);
        signupFrame.add(mainPanel);
        signupFrame.setVisible(true);

        // Actions
        signupBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(signupFrame, "Account created successfully!");
            signupFrame.dispose();
            showLogin();
        });

        already.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signupFrame.dispose();
                showLogin();
            }
        });
    }

    // ========== HOME PAGE ==========
    static void showHome() {
        JFrame homeFrame = new JFrame("Dine Easy - Home");
        homeFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int boxWidth = (int) (screenSize.width * 0.75);
        int boxHeight = (int) (screenSize.height * 0.75);

        JPanel mainPanel = createBackgroundPanel();

        JPanel boxPanel = createBoxPanel(boxWidth, boxHeight);
        GridBagConstraints gbcBox = new GridBagConstraints();
        gbcBox.insets = new Insets(20, 20, 20, 20);

        JLabel heading = new JLabel("Welcome to Dine Easy", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 32));
        gbcBox.gridx = 0; gbcBox.gridy = 0; gbcBox.gridwidth = 2;
        boxPanel.add(heading, gbcBox);

        JButton diningBtn = createStyledButton("Dining Area", new Color(100, 149, 237));
        gbcBox.gridx = 0; gbcBox.gridy = 1; gbcBox.gridwidth = 1;
        boxPanel.add(diningBtn, gbcBox);

        JButton kitchenBtn = createStyledButton("Kitchen", new Color(60, 179, 113));
        gbcBox.gridx = 1; gbcBox.gridy = 1;
        boxPanel.add(kitchenBtn, gbcBox);

        mainPanel.add(boxPanel);
        homeFrame.add(mainPanel);
        homeFrame.setVisible(true);

        diningBtn.addActionListener(e -> {
            homeFrame.dispose();
            showDiningArea();
        });

        kitchenBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(homeFrame, "Kitchen Interface (to be developed)");
        });
    }

    // ========== DINING AREA ==========
    static void showDiningArea() {
        JFrame frame = new JFrame("Dine Easy - Dining Area");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(245, 245, 245));
        JButton backBtn = new JButton("Back");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        backBtn.addActionListener(e -> {
            frame.dispose();
            showHome();
        });
        topPanel.add(backBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JLabel heading = new JLabel("Table List", SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 30));
        centerPanel.add(heading);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel tablePanel = new JPanel(new GridLayout(3, 3, 20, 20));
        for (int i = 1; i <= 9; i++) {
            JButton tableBtn = new JButton("Table " + i);
            styleTableButton(tableBtn);
            int tableNo = i;
            tableBtn.addActionListener(e -> showMenu(tableNo, frame));
            tablePanel.add(tableBtn);
        }
        centerPanel.add(tablePanel);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // ========== MENU ==========
    static void showMenu(int tableNo, JFrame parentFrame) {
        JFrame menuFrame = new JFrame("Table " + tableNo + " - Menu");
        menuFrame.setSize(500, 600);
        menuFrame.setLocationRelativeTo(parentFrame);
        menuFrame.setLayout(new BorderLayout());

        JLabel heading = new JLabel("Select Food - Table " + tableNo, SwingConstants.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        menuFrame.add(heading, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        Map<String, JSpinner> quantitySpinners = new HashMap<>();

        for (String item : menu.keySet()) {
            int price = menu.get(item);

            JPanel itemPanel = new JPanel(new BorderLayout());
            JLabel itemLabel = new JLabel(item + " - ₹" + price);
            itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

            JSpinner qtySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 20, 1));
            qtySpinner.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            quantitySpinners.put(item, qtySpinner);

            itemPanel.add(itemLabel, BorderLayout.WEST);
            itemPanel.add(qtySpinner, BorderLayout.EAST);

            menuPanel.add(itemPanel);
        }

        menuFrame.add(new JScrollPane(menuPanel), BorderLayout.CENTER);

        JButton confirmBtn = createPrimaryButton("Confirm Order");
        confirmBtn.addActionListener(e -> {
            StringBuilder order = new StringBuilder("Order for Table " + tableNo + ":\n");
            boolean selected = false;
            for (String item : quantitySpinners.keySet()) {
                int qty = (Integer) quantitySpinners.get(item).getValue();
                if (qty > 0) {
                    order.append("- ").append(item).append(" x ").append(qty)
                         .append(" (₹").append(menu.get(item) * qty).append(")\n");
                    selected = true;
                }
            }
            if (selected) {
                JOptionPane.showMessageDialog(menuFrame, order.toString());
                menuFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(menuFrame, "Please select at least one item!");
            }
        });
        menuFrame.add(confirmBtn, BorderLayout.SOUTH);
        menuFrame.setVisible(true);
    }

    // ========== COMMON HELPERS ==========
    static JPanel createBackgroundPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(230, 230, 250)); // lavender
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
        JLabel img = new JLabel("IMAGE HERE", SwingConstants.CENTER);
        img.setPreferredSize(new Dimension(boxWidth / 2 - 40, boxHeight - 100));
        img.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        img.setFont(new Font("Segoe UI", Font.ITALIC, 18));
        return img;
    }

    static JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(128, 0, 255));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 18));
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
        return button;
    }

    static void styleTableButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(new Color(255, 228, 181));
        button.setBorder(BorderFactory.createLineBorder(new Color(210, 180, 140), 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}

