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
        //list.add(new Shoes(1,"a", "https://www.chuphinhsanpham.vn/wp-content/uploads/2021/06/chup-hinh-giay-dincox-shoes-c-photo-studio-4.jpg", 
        //        100, "check", "desc"));
        return list;
    }

    
}