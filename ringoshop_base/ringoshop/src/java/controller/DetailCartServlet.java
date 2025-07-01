/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.*;
import model.Shoes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
/**
 *
 * @author duongtanki
 */
@WebServlet(name = "DetailCartServlet", urlPatterns = {"/detailcart"})
public class DetailCartServlet extends HttpServlet {

  @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String productIdRaw = request.getParameter("productId");
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        if (productIdRaw != null) {
            try {
                int productId = Integer.parseInt(productIdRaw);

                // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
                List<Shoes> cart = (List<Shoes>) session.getAttribute("cart");
                if (cart == null) {
                    cart = new ArrayList<>();
                }

                // Lấy thông tin sản phẩm từ DB
                ShoesDAO dao = new ShoesDAO();
                Shoes shoe = dao.getShoes(productId);
                
                
                if (shoe != null) {
                   for(int i = 1; i <= quantity; i++) {
                        cart.add(shoe);
                   }
                }

                // Cập nhật lại session
                session.setAttribute("cart", cart);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // Redirect về lại trang home (đảm bảo qua servlet để có dữ liệu)
        response.sendRedirect("detail?productId=" + productIdRaw);
    }

}
