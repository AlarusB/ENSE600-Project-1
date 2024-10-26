
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
    
    // Set up the database
    public void setupDatabase() {
        if (refreshMode) {
            System.out.println("Dropping all tables...");
            dropAllTables();
        }
        
        System.out.println("Creating tables...");
        createItemsTable();
        createEffectTypesTable();
        createWeaponsTable();
        createPotionsTable();
        createInventoryTable();
    }
    
    // Go through all tables and drop them
    private void dropAllTables() {
        String[] tables = {"Inventory", "Potions", "Weapons", "Effect_Types", "Items"};
        for (String table : tables) {
            if (dbManager.tableExists(table)) {
                System.out.println("Dropping table " + table);
                String sqlDropTable = "DROP TABLE " + table;
                dbManager.updateDB(sqlDropTable);
            }
        }
    }
    
    // Create items table and fill with data
    public void createItemsTable() {
        if (dbManager.tableExists("Items")) {
            System.out.println("Table Items already exists, skipping creation.");
            return;
        }
        String sqlCreateTable = "CREATE TABLE Items ("
            + "item_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "name VARCHAR(100), "
            + "base_cost INT, "
            + "description VARCHAR(255))";
        dbManager.updateDB(sqlCreateTable);
        System.out.println("Created table Items.");

        String sqlInsertTable = "INSERT INTO Items (name, base_cost, description) VALUES "
                + "('Bannana', 0, 'A banana.'), "
                + "('Wooden Club', 0, 'A tough Wooden Club.'), "
                + "('Steel Sword', 0, 'A sturdy Steel Sword.'), "
                + "('Laser Sword', 0, 'A pristine Laser Sword, can cut anything in half.'), "
                + "('Prime Energy', 0, ''), "
                + "('Mountain Dew', 0, ''), "
                + "('Water', 0, '')";
        dbManager.updateDB(sqlInsertTable);
        System.out.println("Inserted sample data into Items.");
    }
    
    // Create effect types table and fill with data
    public void createEffectTypesTable() {
        if (dbManager.tableExists("Effect_Types")) {
            System.out.println("Table Effect_Types already exists, skipping creation.");
            return;
        }
        String sqlCreateTable = "CREATE TABLE Effect_Types ("
            + "effect_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
            + "name VARCHAR(100), "
            + "cost_factor DECIMAL(5, 2))";
        dbManager.updateDB(sqlCreateTable);
        System.out.println("Created table Effect_Types.");

        String sqlInsertTable = "INSERT INTO Effect_Types (name, cost_factor) VALUES "
                + "('Attack', 1), "
                + "('Weaken', 5), "
                + "('Healing', 0.5)";
        dbManager.updateDB(sqlInsertTable);
        System.out.println("Inserted sample data into Effect_Types.");
    }
    
    // Create weapons table and fill with data
    public void createWeaponsTable() {
        if (dbManager.tableExists("Weapons")) {
            System.out.println("Table Weapons already exists, skipping creation.");
            return;
        }
        String sqlCreateTable = "CREATE TABLE Weapons ("
                + "item_id INT NOT NULL PRIMARY KEY, "
                + "damage INT, "
                + "cost_factor DECIMAL(5, 2), "
                + "FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE)";
        dbManager.updateDB(sqlCreateTable);
        System.out.println("Created table Weapons.");
        
        String sqlInsertTable = "INSERT INTO Weapons VALUES "
                + "(1, 10, 10), "
                + "(2, 30, 10), "
                + "(3, 60, 10), "
                + "(4, 120, 10)";
        dbManager.updateDB(sqlInsertTable);
        System.out.println("Inserted sample data into Weapons.");
    }
    
    // Create potions table and fill with data
    public void createPotionsTable() {
        if (dbManager.tableExists("Potions")) {
            System.out.println("Table Potions already exists, skipping creation.");
            return;
        }
        String sqlCreateTable = "CREATE TABLE Potions ("
                + "item_id INT NOT NULL PRIMARY KEY, "
                + "effect_value INT, "
                + "effect_id INT, "
                + "FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE, "
                + "FOREIGN KEY (effect_id) REFERENCES Effect_Types(effect_id) ON DELETE CASCADE)";
        dbManager.updateDB(sqlCreateTable);
        System.out.println("Created table Potions.");
        
        String sqlInsertTable = "INSERT INTO Potions VALUES "
                + "(5, 10, 2), "
                + "(6, 50, 1), "
                + "(7, 100, 3)";
        dbManager.updateDB(sqlInsertTable);
        System.out.println("Inserted sample data into Potions.");
    }
    
    // Create inventory table and fill with data
    public void createInventoryTable() {
        if (dbManager.tableExists("Inventory")) {
            System.out.println("Table Inventory already exists, skipping creation.");
            return;
        }
        String sqlCreateTable = "CREATE TABLE Inventory ("
                + "id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                + "item_id INT, "
                + "amount INT, "
                + "FOREIGN KEY (item_id) REFERENCES Items(item_id) ON DELETE CASCADE)";
        dbManager.updateDB(sqlCreateTable);
        System.out.println("Created table Inventory.");
    }
}

