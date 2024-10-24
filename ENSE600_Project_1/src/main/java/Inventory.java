
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
        dbManager = DBManager.getInstance();
        conn = dbManager.getConnection();
    }
    
    // Add item to inventory
    public void addItem(int itemId, int amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount. Cannot add item.");
            return;
        }
        int currentAmount = getItemAmount(itemId);
        if (currentAmount > 0) {
            // If it exists, update the amount
            updateItemAmount(itemId, currentAmount + amount);
        } else {
            String sql = "INSERT INTO Inventory (item_id, amount) VALUES (?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, itemId);
                pstmt.setInt(2, amount);
                pstmt.executeUpdate();
                //System.out.println("Item added to inventory.");
            } catch (SQLException ex) {
                System.out.println("Error adding item: " + ex.getMessage());
            }
        }
    }
    // Without amount argument, add a single item to inventory
    public void addItem(int itemId) {
        addItem(itemId, 1);
    }

    // Update item amount in inventory
    public void updateItemAmount(int itemId, int newAmount) {
        if (newAmount < 0) {
            System.out.println("Invalid amount. Cannot update item.");
            return;
        }
        String sql = "UPDATE Inventory SET amount = ? WHERE item_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, newAmount);
            pstmt.setInt(2, itemId);
            pstmt.executeUpdate();
            //System.out.println("Item amount updated.");
        } catch (SQLException ex) {
            System.out.println("Error updating item: " + ex.getMessage());
        }
    }

    // Remove item from inventory
    public void removeItem(int itemId) {
        String sql = "DELETE FROM Inventory WHERE item_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            pstmt.executeUpdate();
            //System.out.println("Item removed from inventory.");
        } catch (SQLException ex) {
            System.out.println("Error removing item: " + ex.getMessage());
        }
    }

    // View all items in inventory
    public void viewInventory() {
        String sql = "SELECT i.item_id, i.name, inv.amount "
                   + "FROM Inventory inv "
                   + "JOIN Items i ON inv.item_id = i.item_id";
        try (Statement stmt = conn.createStatement();
              ResultSet rs = stmt.executeQuery(sql))  {
            
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
    
    // Get item amount from inventory
    public int getItemAmount(int itemId) {
        String sql = "SELECT amount FROM Inventory WHERE item_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("amount");
            }
        } catch (SQLException ex) {
            System.out.println("Error retrieving item amount: " + ex.getMessage());
        }

        return 0;
    }

    // Check if an item exists in inventory
    public boolean itemExists(int itemId) {
        return getItemAmount(itemId) > 0;
    }
    
    public void clear() {
        String sql = "DELETE FROM Inventory";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
            //System.out.println("Inventory cleared.");
        } catch (SQLException ex) {
            System.out.println("Error clearing inventory: " + ex.getMessage());
        }
    }
}
