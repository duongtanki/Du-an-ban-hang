package dao;

import jakarta.servlet.ServletException;
import java.sql.SQLException;

/**
 *
 * @author duongtanki
 */
public class LoginDAO extends MyDAO {
    
    public boolean checkLogin (String xUser, String xPass) {
        boolean check = false;
        xSql = "select * from login where username = '?', password = '?'";
        try {
            ps = con.prepareStatement(xSql);
            ps.setString(1, xUser);
            ps.setString(2, xPass);
            rs = ps.executeQuery();
            ps.close();
            check = rs.next();
        } catch(Exception e) {
            e.printStackTrace();
         }
        return check;
    }
    
}
