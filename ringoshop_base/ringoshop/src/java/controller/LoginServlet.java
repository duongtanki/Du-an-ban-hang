/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.*;



/**
 *
 * @author duongtanki
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("user");
        String pass = (String)request.getParameter("pass");
        LoginDAO u = new LoginDAO();
        Login log = u.checkLogin(user, pass);
        if(log != null) {
            HttpSession session = request.getSession();
            session.setAttribute("account", log);  // lưu cả object
            if (log.getRole() == 1) {
                request.getRequestDispatcher("Cart.jsp").forward(request, response);
            } else {
                session.setAttribute("user", user); // hoặc user object nếu bạn có
                response.sendRedirect("home");
            }
        }
         else {
            request.setAttribute("mess", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);  
        }
    }


}
