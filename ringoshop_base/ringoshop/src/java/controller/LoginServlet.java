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
 * @author duongtanki
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    
    private final AdminDAO adminDAO = new AdminDAOImpl();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        
        // 🔥 TÍNH NĂNG 1: Admin Login (từ hieu/signupServlet - code sạch hơn)
        Admin admin = adminDAO.getAdminByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminId", admin.getAdminId());
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("adminName", admin.getDisplayName());
            session.setAttribute("adminPhone", admin.getPhone());
            response.sendRedirect(request.getContextPath() + "/Admin.jsp");
            return;
        }
        
        // 🔥 TÍNH NĂNG 2: User Login (từ HEAD - tính năng độc quyền)
        LoginDAO loginDAO = new LoginDAO();
        Login user = loginDAO.checkLogin(username, password);
        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("account", user);
            
            if (user.getRole() == 1) {
                request.getRequestDispatcher("Cart.jsp").forward(request, response);
            } else {
                session.setAttribute("user", username);
                request.getRequestDispatcher("Home.jsp").forward(request, response);
            }
        } else {
            // Cả admin và user đều sai
            request.setAttribute("mess", "Tên đăng nhập hoặc mật khẩu không đúng!");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
}