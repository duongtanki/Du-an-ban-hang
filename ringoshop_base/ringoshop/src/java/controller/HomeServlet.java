package controller;

import dao.*;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        ShoesDAO dao = new ShoesDAO();
        List<Shoes> list = dao.getAllShoes();
        
        if (!list.isEmpty()) {
            Shoes lastProduct = list.get(list.size() - 1);
            request.setAttribute("lastProduct", lastProduct);
        }

        request.setAttribute("shoesList", list);
        request.getRequestDispatcher("Home.jsp").forward(request, response);
    }
}
