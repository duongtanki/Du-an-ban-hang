/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.*;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author duongtanki
 */
@WebServlet(name = "LeftServlet", urlPatterns = {"/left"})
public class LeftServlet extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        ShoesDAO dao = new ShoesDAO();
        List<Shoes> list = dao.getAllShoes();

        request.setAttribute("shoesList", list);
        request.getRequestDispatcher("Home.jsp").forward(request, response);
    }

}
