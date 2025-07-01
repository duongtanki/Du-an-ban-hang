
package controller;

import dao.*;
import model.Shoes;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;


@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String name = request.getParameter("name");

        ShoesDAO dao = new ShoesDAO();
        List<Shoes> searchResult = dao.getShoesByName(name);

        request.setAttribute("shoesList", searchResult); // giống với home.jsp
        request.getRequestDispatcher("SearchResult.jsp").forward(request, response);
    }

 @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        String name = request.getParameter("name");

        ShoesDAO dao = new ShoesDAO();
        List<Shoes> searchResult = dao.getShoesByName(name);

        request.setAttribute("shoesList", searchResult); // giống với home.jsp
        request.getRequestDispatcher("SearchResult.jsp").forward(request, response);
    }
}
