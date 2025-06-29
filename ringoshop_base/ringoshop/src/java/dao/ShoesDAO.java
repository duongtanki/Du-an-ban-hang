// src/main/java/com/ringoshop/dao/ShoeDAO.java
package dao;

import controller.ShoesServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ShoesDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    ShoesServlet getShoesById(int id) throws SQLException;

    List<ShoesServlet> getAllShoes() throws SQLException;

    List<ShoesServlet> searchShoes(String keyword, String brand, String category, String gender) throws SQLException;
}