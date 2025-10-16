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
    
