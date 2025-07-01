// src/main/java/com/ringoshop/dao/ShoeDAO.java
package dao;

import model.Shoes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ShoesDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    Shoes getShoesById(int id) throws SQLException;

    List<Shoes> getAllShoes() throws SQLException;

    List<Shoes> searchShoes(String keyword, String brand, String category, String gender) throws SQLException;
}