// src/main/java/com/ringoshop/dao/ShoeDAO.java
package dao;


import java.util.ArrayList;
import java.util.List;
import model.Shoes;

public class ShoesDAO extends MyDAO{

    public List<Shoes> getAllShoes() {
        List<Shoes> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM shoes";
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Shoes s = new Shoes(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("main_image_url"),
                    rs.getDouble("price"),
                    rs.getString("title"),
                    rs.getString("description")
                );
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Shoes getShoes(int xId) {
        Shoes s = new Shoes();
        try {
            String query = "SELECT * FROM shoes where id = ?";
            ps = con.prepareStatement(query);
            ps.setInt(1, xId);
            rs = ps.executeQuery();
            while (rs.next()) {
                s = new Shoes(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("main_image_url"),
                    rs.getDouble("price"),
                    rs.getString("title"),
                    rs.getString("description")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
    
    public List<Shoes> getShoesByName(String xName) {
        List<Shoes> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM shoes where name like ?";
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + xName + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Shoes s = new Shoes(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("main_image_url"),
                    rs.getDouble("price"),
                    rs.getString("title"),
                    rs.getString("description")
                );
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
}