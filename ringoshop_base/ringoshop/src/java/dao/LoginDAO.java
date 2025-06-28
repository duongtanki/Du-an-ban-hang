package dao;

import jakarta.servlet.ServletException;
import java.sql.SQLException;

/**
 *
 * @author duongtanki
 */
public class LoginDAO extends MyDAO {
    
    public int checkLogin (String xUser, String xPass) {
        //boolean check = false;
        int role = 0;
        xSql = "select * from login WHERE username = ? AND password = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, xUser);
            ps.setString(2, xPass);
            rs = ps.executeQuery();
            //check = rs.next();
            while(rs.next()) {
                role = rs.getInt("role");
            }
            ps.close();
        } catch(Exception e) {
            e.printStackTrace();
         }
        return role;
    }
    
    public int isExit (String xUser) {
        int role = 0;
        xSql = "select * from login WHERE username = ?";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, xUser);
            rs = ps.executeQuery();
            //check = rs.next();
            while(rs.next()) {
                role = rs.getInt("role");
            }
            ps.close();
        } catch(Exception e) {
            e.printStackTrace();
         }
        return role;
    }
    
    public void insertLogin(String xUser, String xPass) {
        String xSql = "INSERT INTO login (username, password, role) VALUES (?, ?, 2)";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, xUser);
            ps.setString(2, xPass);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();  // Hoặc log lỗi tùy hệ thống
        }
    }
    
}
