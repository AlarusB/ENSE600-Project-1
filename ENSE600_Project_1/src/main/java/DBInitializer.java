
import java.sql.Connection;
import java.sql.Statement;

/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
public class DBInitializer {
    
    private final DBManager dbManager;    
    private boolean refreshMode = false;
    
    public DBInitializer(boolean refreshMode) {
        dbManager = DBManager.getInstance();
        this.refreshMode = refreshMode;
    }
    
    public void setupDatabase() {
        if (refreshMode) {
            dropAllTables();
        }
        
        createItemsTable();
        createEffectTypesTable();
        createWeaponsTable();
        createPotionsTable();
        createInventoryTable();
    }
    
    private void dropAllTables() {
        String[] tables = {"Inventory", "Potions", "Weapons", "Effect_Types", "Items"};
        for (String table : tables) {
            if (dbManager.tableExists(table)) {
                String sqlDropTable = "DROP TABLE " + table;
                dbManager.updateDB(sqlDropTable);
            }
        }
    }
    
    public void createItemsTable() {
        if (dbManager.tableExists("Items")) {
            return;
        }
        String sqlCreateTable = "CREATE TABLE Items ("
            + "item_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "name VARCHAR(100), "
            + "base_cost INT, "
            + "description VARCHAR(255))";
        dbManager.updateDB(sqlCreateTable);

        String sqlInsertTable = "INSERT INTO Items (name, base_cost, description) VALUES "
                + "('Bannana', 0, 'A banana.'),\n"
                + "('Wooden Club', 0, 'A tough Wooden Club.'),\n"
                + "('Steel Sword', 0, 'A sturdy Steel Sword.'),\n"
                + "('Laser Sword', 0, 'A pristine Laser Sword, can cut anything in half.'),\n"
                + "('Prime Energy', 0, ''),\n"
                + "('Mountain Dew', 0, ''),\n"
                + "('Water', 0, '')";
        dbManager.updateDB(sqlInsertTable);
    }
    
    public void createEffectTypesTable() {
        if (dbManager.tableExists("Effect_Types")) {
            return;
        }
        String sqlCreateTable = "CREATE TABLE Effect_Types ("
            + "effect_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "name VARCHAR(100), "
            + "cost_factor DECIMAL(5, 2))";
        dbManager.updateDB(sqlCreateTable);

        String sqlInsertTable = "INSERT INTO Effect_Types (name, cost_factor) VALUES"
                + "('Attack', 1),\n"
                + "('Weaken', 5),\n"
                + "('Healing', 0.5)";
        dbManager.updateDB(sqlInsertTable);
    }
    
    public void createWeaponsTable() {
        if (dbManager.tableExists("Weapons")) {
            return;
        }
        String sqlCreateTable = "CREATE TABLE Weapons ("
                + "item_id INT NOT NULL PRIMARY KEY, "
                + "damage INT, "
                + "cost_factor DECIMAL(5, 2), "
                + "FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE)";
        dbManager.updateDB(sqlCreateTable);
        
        String sqlInsertTable = "INSERT INTO Weapons VALUES"
                + "(1, 10, 10),\n"
                + "(2, 30, 10),\n"
                + "(3, 60, 10),\n"
                + "(4, 120, 10)";
        dbManager.updateDB(sqlInsertTable);
    }
    
    public void createPotionsTable() {
        if (dbManager.tableExists("Potions")) {
            return;
        }
        String sqlCreateTable = "CREATE TABLE Potions ("
                + "item_id INT NOT NULL PRIMARY KEY, "
                + "effect_value INT, "
                + "effect_id INT, "
                + "FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE, "
                + "FOREIGN KEY (effect_id) REFERENCES Effect_Types(effect_id) ON DELETE CASCADE)";
        dbManager.updateDB(sqlCreateTable);
        
        String sqlInsertTable = "INSERT INTO Potions VALUES"
                + "(5, 10, 2),\n"
                + "(6, 50, 1),\n"
                + "(7, 100, 3)";
        dbManager.updateDB(sqlInsertTable);
    }
    
    public void createInventoryTable() {
        if (dbManager.tableExists("Inventory")) {
            return;
        }
        String sqlCreateTable = "CREATE TABLE Inventory ("
                + "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "item_id INT, "
                + "amount INT, "
                + "FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE)";
        dbManager.updateDB(sqlCreateTable);
    }
}
