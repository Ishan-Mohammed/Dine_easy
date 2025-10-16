package dine_easyyy_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map; // <-- Import added for the new method

public class DatabaseManager {

    // --- Database Credentials ---
    // Make sure your database is named "my_app_db"
    private static final String DB_URL = "jdbc:mysql://localhost:3306/my_app_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "nived123@"; // Your password

    /**
     * Registers a new user in the database.
     * Throws SQLException if the user already exists or another DB error occurs.
     */
    public static void registerUser(String username, String email, String password) throws SQLException {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            pstmt.executeUpdate(); // Run the insert command

        } catch (SQLException e) {
            // Just re-throw the exception to let the caller handle it.
            // This gives the user interface more information about what went wrong.
            throw e;
        }
    }

    /**
     * Validates a user's login credentials against the database.
     * @return true if username and password are correct, false otherwise.
     */
    public static boolean validateUser(String username, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if a user was found
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    // Check if the provided password matches the one from the database
                    return password.equals(storedPassword);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error during validation: " + e.getMessage());
        }
        return false; // User not found or password incorrect
    }

    /**
     * Saves all items from an order to the database.
     * @param tableNumber The table number placing the order.
     * @param orderItems A map of item names to their quantities.
     * @param menuPrices A map of item names to their prices to calculate the total.
     * @throws SQLException if a database error occurs.
     */
    public static void saveOrder(int tableNumber, Map<String, Integer> orderItems, Map<String, Integer> menuPrices) throws SQLException {
        String sql = "INSERT INTO orders (table_number, item_name, quantity, total_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through each item in the customer's order
            for (Map.Entry<String, Integer> item : orderItems.entrySet()) {
                String itemName = item.getKey();
                int quantity = item.getValue();
                
                // Calculate the total price for this specific line item
                int pricePerItem = menuPrices.get(itemName);
                double lineItemTotal = pricePerItem * quantity;

                // Set the values for the SQL query
                pstmt.setInt(1, tableNumber);
                pstmt.setString(2, itemName);
                pstmt.setInt(3, quantity);
                pstmt.setDouble(4, lineItemTotal);

                // Add the complete statement to a batch to execute all at once
                pstmt.addBatch();
            }

            // Execute the batch of INSERT statements
            pstmt.executeBatch();
            System.out.println("Order for table " + tableNumber + " successfully saved.");

        } catch (SQLException e) {
            System.out.println("Database error while saving order: " + e.getMessage());
            // Re-throw the exception so the user interface can show an error message
            throw e;
        }
    }
}