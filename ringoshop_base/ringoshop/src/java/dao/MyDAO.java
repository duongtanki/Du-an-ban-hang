package dao;

import utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyDAO {
    public Connection con = null;
    public PreparedStatement ps = null;
    public ResultSet rs = null;
    public String xSql = null;

    public MyDAO() {
        try {
            con = DBConnection.getConnection(); // ← QUAN TRỌNG: Fix này
            System.out.println("MyDAO connection: " + (con != null ? "SUCCESS" : "FAILED"));
        } catch (Exception e) {
            System.out.println("MyDAO connection error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}