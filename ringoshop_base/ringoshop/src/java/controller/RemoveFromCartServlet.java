package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.Shoes;

@WebServlet(name = "RemoveFromCartServlet", urlPatterns = {"/removefromcart"})
public class RemoveFromCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        List<Shoes> cart = (List<Shoes>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        try {
            int index = Integer.parseInt(request.getParameter("index"));
            if (index >= 0 && index < cart.size()) {
                cart.remove(index);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // hoặc log lỗi
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("Cart.jsp");
    }
}
