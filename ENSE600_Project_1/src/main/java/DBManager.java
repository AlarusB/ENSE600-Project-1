
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public final class DBManager {

    private static final String URL = "jdbc:derby:ENSE600_Project_DB;create=true";
    private static DBManager dbManagerInstance;
    private Connection connection;

    private DBManager() {
        establishConnection();
    }

    public static synchronized DBManager getInstance() {
        if (dbManagerInstance == null) {
            dbManagerInstance = new DBManager();
        }
        return dbManagerInstance;
    }

    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void establishConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL);
                System.out.println("Connected to database at " + URL);
            }
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
        }
    }

    public void closeConnections() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Database connection closed.");
            } catch (SQLException ex) {
                System.out.println("Error closing connection: " + ex.getMessage());
            }
        }
    }

    public ResultSet queryDB(String sql, Object... params) {
        ResultSet resultSet = null;
        establishConnection();  // Ensure the connection is open
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            resultSet = pstmt.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Error executing query: " + ex.getMessage());
        }
        return resultSet;
    }

    public void updateDB(String sql, Object... params) {
        establishConnection();  // Ensure the connection is open
        try ( PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error executing update: " + ex.getMessage());
        }
    }

    public void clearDB() {
        establishConnection();  // Ensure the connection is open
        try ( ResultSet rs = connection.getMetaData().getTables(null, null, null, new String[]{"TABLE"})) {
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                updateDB("DROP TABLE " + tableName);
            }
            System.out.println("All tables dropped.");
        } catch (SQLException ex) {
            System.out.println("Error clearing database: " + ex.getMessage());
        }
    }

    public boolean tableExists(String tableName) {
        establishConnection();  // Ensure the connection is open
        try ( ResultSet rs = connection.getMetaData()
                .getTables(null, null, tableName.toUpperCase(), new String[]{"TABLE"})) {
            return rs.next();
        } catch (SQLException ex) {
            System.err.println("Error checking table existence: " + ex.getMessage());
        }
        return false;
    }
}
