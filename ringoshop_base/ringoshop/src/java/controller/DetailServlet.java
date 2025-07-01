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


/**
 *
 * @author duongtanki
 */
@WebServlet(name = "DetailServlet", urlPatterns = {"/detail"})
public class DetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id_raw = request.getParameter("productId");
        try {
            int id = Integer.parseInt(id_raw);
            ShoesDAO dao = new ShoesDAO();
            Shoes s = dao.getShoes(id); // Lấy thông tin sản phẩm từ DB
            request.setAttribute("detail", s);
            request.getRequestDispatcher("Detail.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("Home.jsp"); // fallback khi lỗi
        }
    }
}
