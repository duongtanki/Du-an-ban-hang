package controller;

import dao.*;
import model.Shoes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "SearchCartServlet", urlPatterns = {"/searchcart"})
public class SearchCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String productIdRaw = request.getParameter("productId");

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
                   cart.add(shoe);
                }

                // Cập nhật lại session
                session.setAttribute("cart", cart);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String searchName = request.getParameter("name");
        response.sendRedirect("search?name=" + java.net.URLEncoder.encode(searchName, "UTF-8"));
    }
}
