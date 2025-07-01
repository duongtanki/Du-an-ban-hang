package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.*;
import dao.*;

@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/updatecart"})
public class UpdateCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Integer, Item> cart = (Map<Integer, Item>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();

        int id = Integer.parseInt(request.getParameter("id"));
        String action = request.getParameter("action");
        Item item = cart.get(id);
        

        if (item != null) {
            if ("increase".equals(action)) {
                item.setQuantity(item.getQuantity() + 1);
            } else if ("decrease".equals(action)) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                }
            }
        }

        session.setAttribute("cart", cart);
        response.sendRedirect("Cart.jsp");
    }
}
