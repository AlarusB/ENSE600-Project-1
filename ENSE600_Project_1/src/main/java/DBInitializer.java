
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
    private final Connection conn;
    private Statement statement;
    
    private boolean REFRESH_MODE = false;
    
    public DBInitializer() {
        dbManager = DBManager.getInstance();
        conn = dbManager.getConnection();
    }
    
    public void setupDatabase() {
        createItemsTable();
        createEffectTypesTable();
        createWeaponsTable();
        createPotionsTable();
        createInventoryTable();
    }
    
    public void createItemsTable() {
        if (dbManager.tableExists("Items")) {
            if (!REFRESH_MODE) {
                return;
            }
            String sqlDropTable = "DROP TABLE Items";
            dbManager.updateDB(sqlDropTable);
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
            if (!REFRESH_MODE) {
                return;
            }
            String sqlDropTable = "DROP TABLE Effect_Types";
            dbManager.updateDB(sqlDropTable);
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
            if (!REFRESH_MODE) {
                return;
            }
            String sqlDropTable = "DROP TABLE Weapons";
            dbManager.updateDB(sqlDropTable);
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
            if (!REFRESH_MODE) {
                return;
            }
            String sqlDropTable = "DROP TABLE Potions";
            dbManager.updateDB(sqlDropTable);
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
