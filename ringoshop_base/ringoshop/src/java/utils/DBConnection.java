// src/main/java/com/yourcompany/stockx/util/DBConnection.java
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=ringoshop_database;encrypt=false;trustServerCertificate=true;";
    private static final String DB_USER = "sa"; // Hoặc user bạn đã tạo
    private static final String DB_PASSWORD = "123"; // Mật khẩu SQL Server của bạn

    // Hàm get connection
    public static Connection getConnection() throws SQLException {
        try {
            // Đăng ký JDBC Driver (không cần thiết với JDBC 4.0+ nhưng là thói quen tốt)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            System.err.println("SQL Server JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("SQL Server JDBC Driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}