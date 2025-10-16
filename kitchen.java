// import thr main packages 
package dine_easyyy_project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class kitchen {

    // --- Stores the orders ---
    private static ArrayList<Order> pendingOrders = new ArrayList<>();
    private static ArrayList<Order> completedOrders = new ArrayList<>();

    private static JFrame kitchenFrame;
    private static JPanel ordersPanel; // This will hold the individual order cards

    /**
     * A simple class to hold all info about a single order.
     */
    static class Order {
        int tableNumber;
        Map<String, Integer> items;

        Order(int tableNumber, Map<String, Integer> items) {
            this.tableNumber = tableNumber;
            this.items = items;
        }

    }

    public static void showKitchenInterface() {
        kitchenFrame = new JFrame("Kitchen - Live Orders");
        kitchenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        kitchenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        java.net.URL bgURL = kitchen.class.getResource("/kitchen_int.jpg");
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
            bgLabel.setBackground(Color.DARK_GRAY);
            bgLabel.setOpaque(true);
        }
        bgLabel.setLayout(new BorderLayout());
        kitchenFrame.setContentPane(bgLabel);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setOpaque(false);
        JButton backBtn = new JButton("Back to Home");
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        // ===== FIX #1: BACK TO HOME BUTTON LOGIC CORRECTED =====
        backBtn.addActionListener(e -> {
            // Re-use the current frame to show the home content. No need to dispose.
            important.transitionToHomeContent(kitchenFrame);
        });
        topPanel.add(backBtn);
        bgLabel.add(topPanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);

        JLabel headingLabel = new JLabel("Kitchen Interface", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        centerPanel.add(headingLabel, BorderLayout.NORTH);

        ordersPanel = new JPanel();
        ordersPanel.setLayout(new BoxLayout(ordersPanel, BoxLayout.Y_AXIS));
        ordersPanel.setOpaque(false); // Transparent background
        ordersPanel.setBorder(BorderFactory.createEmptyBorder(0, 300, 0, 300)); 
        
        // kitchen flow starts from here 
        // Padding to shrink the area

        JScrollPane scrollPane = new JScrollPane(ordersPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false); // Make scroll pane transparent too
        scrollPane.setBorder(null);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        bgLabel.add(centerPanel, BorderLayout.CENTER);
        
        refreshOrdersDisplay(); // Initial setup of the display

        kitchenFrame.setVisible(true);
    }

    public static void receiveOrder(int tableNumber, Map<String, Integer> orderItems) {
        Order newOrder = new Order(tableNumber, orderItems);
        pendingOrders.add(newOrder);

        // Update the display if the kitchen window is open
        if (kitchenFrame != null && kitchenFrame.isVisible()) {
            SwingUtilities.invokeLater(() -> refreshOrdersDisplay());
        }
    }
    
    /**
     * Clears and redraws all the pending order cards on the screen.
     */
    private static void refreshOrdersDisplay() {
        ordersPanel.removeAll(); // Clear the old view

        if (pendingOrders.isEmpty()) {
            JLabel waitingLabel = new JLabel("--- Waiting for new orders ---", SwingConstants.CENTER);
            waitingLabel.setFont(new Font("Segoe UI", Font.ITALIC, 24));
            waitingLabel.setForeground(Color.WHITE);
            JPanel waitingPanel = new JPanel(new GridBagLayout());
            waitingPanel.setOpaque(false);
            waitingPanel.add(waitingLabel);
            ordersPanel.add(waitingPanel);
        } else {
            // Loop through pending orders and create a card for each
            for (int i = 0; i < pendingOrders.size(); i++) {
                Order order = pendingOrders.get(i);
                ordersPanel.add(createOrderCard(order, i));
                ordersPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between cards
            }
        }

        ordersPanel.revalidate();
        ordersPanel.repaint();
    }

    /**
     * Creates a single white JPanel card for an order.
     */
    private static JPanel createOrderCard(Order order, int orderIndex) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250)); // Set max height

        JTextArea orderDetailsArea = new JTextArea();
        orderDetailsArea.setEditable(false);
        orderDetailsArea.setFont(new Font("Monospaced", Font.BOLD, 16));
        orderDetailsArea.setMargin(new Insets(10, 15, 10, 15));
        
        StringBuilder detailsText = new StringBuilder();
        detailsText.append("TABLE: " + order.tableNumber + "\n");
        detailsText.append("---------------------\n");
        for (Map.Entry<String, Integer> item : order.items.entrySet()) {
            detailsText.append(String.format("- %-15s x %d\n", item.getKey(), item.getValue()));
        }
        orderDetailsArea.setText(detailsText.toString());
        card.add(orderDetailsArea, BorderLayout.CENTER);

        JButton readyButton = new JButton("Order Ready");
        readyButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        readyButton.setBackground(new Color(46, 204, 113)); // Green color
        readyButton.setForeground(Color.WHITE);
        readyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // ===== FIX #2: "ORDER READY" BUTTON LOGIC UPDATED =====
        readyButton.addActionListener(e -> {
            // Get order details *before* removing it to use in the message
            Order completedOrder = pendingOrders.get(orderIndex);
        
            // Move order from pending to completed list
            completedOrders.add(completedOrder);
            pendingOrders.remove(orderIndex);
            
            // Refresh the whole display to make the card disappear
            refreshOrdersDisplay();
            
            // Show the "Despatched!" popup message
            JOptionPane.showMessageDialog(
                kitchenFrame,
                "Order for Table " + completedOrder.tableNumber + " has been despatched!",
                "Order Despatched",
                JOptionPane.INFORMATION_MESSAGE
            );
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(readyButton);
        card.add(buttonPanel, BorderLayout.SOUTH);

        return card;

        // this will return the required output from dining area
    }

}



