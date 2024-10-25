/*
 * Copyright 2024 Abdul B
 * https://github.com/AlarusB/
 */

/**
 *
 * @author abdul
 */
/*
 * The programs are designed for PDC paper
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

public final class DBManager {
    private static final String URL = "jdbc:derby:ENSE600_Project_DB;create=true";  //url of the DB host
    private static DBManager dbManagerInstance; 
    Connection connection;

    private DBManager() {
        establishConnection();
    }
    
    // Method to get the single instance of DBManager
    public static synchronized DBManager getInstance() {
        if (dbManagerInstance == null) {
            dbManagerInstance = new DBManager();
        }
        return dbManagerInstance;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Connection getConnection() {
        return this.connection;
    }

    //Establish connection
    public void establishConnection() {
        //Establish a connection to Database
        try {
            connection = DriverManager.getConnection(URL);
            // System.out.println(URL+" connected...");
        }
        catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    // Close database connections
    public void closeConnections() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Query the database
    public ResultSet queryDB(String sql) {
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return resultSet;
    }

    // Update the database
    public void updateDB(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Clear all tables from the database
    public void clearDB() {
        try (ResultSet rs = connection.getMetaData()
                .getTables(null, null, null, new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                updateDB("DROP TABLE " + tableName);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Check if a table exists in the database
    public boolean tableExists(String tableName) {
        try (ResultSet rs = connection.getMetaData()
                .getTables(null, null, tableName.toUpperCase(),
                        new String[]{"TABLE"})) {
            return rs.next();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
}
