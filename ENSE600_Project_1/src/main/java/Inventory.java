
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class Inventory {
    private final DBManager dbManager;
    private final Connection conn;
    
    public Inventory() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
    }
    
    // Add item to inventory
    public void addItem(int itemId, int amount) {
        String sql = "INSERT INTO Inventory (item_id, amount) VALUES (?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            pstmt.setInt(2, amount);
            pstmt.executeUpdate();
            System.out.println("Item added to inventory.");
        } catch (SQLException ex) {
            System.out.println("Error adding item: " + ex.getMessage());
        }
    }

    // Update item amount in inventory
    public void updateItemAmount(int itemId, int newAmount) {
        String sql = "UPDATE Inventory SET amount = ? WHERE item_id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newAmount);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();
            System.out.println("Item amount updated.");
        } catch (SQLException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
        }
    }

    // Remove item from inventory
    public void removeItem(int itemId) {
        String sql = "DELETE FROM Inventory WHERE item_id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
            System.out.println("Item removed from inventory.");
        } catch (SQLException ex) {
            System.out.println("Error removing item: " + ex.getMessage());
        }
    }

    // View all items in inventory
    public void viewInventory() {
        String sql = "SELECT i.item_id, i.name, inv.amount "
                   + "FROM Inventory inv "
                   + "JOIN Items i ON inv.item_id = i.item_id";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("Inventory:");
            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String itemName = rs.getString("name");
                int amount = rs.getInt("amount");
                System.out.printf("ID: %d, Name: %s, Amount: %d%n", itemId, itemName, amount);
            }
        } catch (SQLException ex) {
            System.out.println("Error viewing inventory: " + ex.getMessage());
        }
    }

    // Check if an item exists in inventory
    public boolean itemExists(int itemId) {
        String sql = "SELECT 1 FROM Inventory WHERE item_id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Error checking item existence: " + ex.getMessage());
        }
        return false;
    }
}
