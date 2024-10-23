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

    Connection conn;

    public DBManager() {
        establishConnection();
    }

    public static void main(String[] args) {
        DBManager dbManager = new DBManager();
        System.out.println(dbManager.getConnection());
    }

    public Connection getConnection() {
        return this.conn;
    }

    //Establish connection
    public void establishConnection() {
        //Establish a connection to Database
        try {
            conn=DriverManager.getConnection(URL);
            System.out.println(URL+" connected...");
        }
        catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        }
    }

    // Close database connections
    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // Query the database
    public ResultSet queryDB(String sql) {
        Connection connection = this.conn;
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
        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    // Clear all tables from the database
    public void clearDB() {
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, null, new String[] {"TABLE"});
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
        try {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"});
            return rs.next(); // If a result exists, the table is present
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }
}
