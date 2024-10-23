
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class ItemFactory {
    private final static DBManager dbManager = new DBManager();
    
    
    public static Weapon createWeapon(int id) {
        Item item = null;
        try {
            item = createItem(id);
        } catch (SQLException ex) {
            System.out.print("Error creating weapon: " + ex.getMessage());
        }
        

        if (item instanceof Weapon) {
            return (Weapon) item; // Safe cast
        }

        return null;
    }
    
    public static Potion createPotion(int id) {
        Item item = null;
        try {
            item = createItem(id);
        } catch (SQLException ex) {
            System.out.print("Error creating potion: " + ex.getMessage());
        }

        if (item instanceof Potion) {
            return (Potion) item; // Safe cast
        }

        return null;
    }
    
    // Create an item from the database using its ID
    public static Item createItem(int id) throws SQLException {
        // Query to retrieve item data based on the ID
   String query = "SELECT Items.item_id, Items.name, Items.base_cost, Items.description, " +
                   "Weapons.damage, Weapons.cost_factor AS weapon_cost_factor, " +
                   "Potions.effect_value, Potions.effect_id, " +
                   "Effect_Types.cost_factor AS potion_cost_factor " +
                   "FROM Items " +
                   "LEFT JOIN Weapons ON Items.item_id = Weapons.item_id " +
                   "LEFT JOIN Potions ON Items.item_id = Potions.item_id " +
                   "LEFT JOIN Effect_Types ON Potions.effect_id = Effect_Types.effect_id " + // Joining Effect_Types
                   "WHERE Items.item_id = " + id;

        ResultSet rs = dbManager.queryDB(query);
        if (rs.next()) { // Move to the first row of the result set
            return createItem(rs);
        }
        
        // Item not found
        throw new SQLException("Item with ID " + id + " not found.");
    }
    
    public static Item createItem(ResultSet rs) throws SQLException {
        int itemId = rs.getInt("item_id");
        String name = rs.getString("name");
        int baseCost = rs.getInt("base_cost");
        String description = rs.getString("description");
        double weaponCostFactor = rs.getDouble("weapon_cost_factor");
         
        // Check if it's a Weapon
        int damage = rs.getInt("damage");

        if (!rs.wasNull()) {  // If damage is not null, it’s a Weapon
            return new Weapon(itemId, name, baseCost, description, damage, (int) weaponCostFactor);
        }

        // Check if it's a specific Potion
        int effectValue = rs.getInt("effect_value");
        double potionCostFactor = rs.getDouble("potion_cost_factor"); // Cost factor from Effect_Types table
        if (!rs.wasNull()) {  // If effect_value is not null, it’s a Potion
            int effectId = rs.getInt("effect_id");
            switch (effectId) {
                case 1:
                    return new AttackPotion(itemId, name, baseCost, description, effectValue, potionCostFactor);
                case 2:
                    return new WeakenPotion(itemId, name, baseCost, description, effectValue, potionCostFactor);
                case 3:
                    return new HealingPotion(itemId, name, baseCost, description, effectValue, potionCostFactor);
                default:
                    throw new SQLException("Unknown effect ID: " + effectId);
            }
        }

        // Invalid item
        throw new SQLException("Item with ID " + itemId + " is neither a Weapon nor a Potion.");
    }
}
