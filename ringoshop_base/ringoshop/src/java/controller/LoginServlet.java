package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    
    private final UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        
        // Validation
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            request.setAttribute("mess", "Vui lòng nhập tên đăng nhập và mật khẩu");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            return;
        }
        
        // Authenticate user (works for all roles)
        User user = userDAO.authenticate(username.trim(), password);
        
        if (user != null) {
            // Login successful
            HttpSession session = request.getSession();
            
            // Set common session attributes
            session.setAttribute("userId", user.getUserId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userRole", user.getRole());
            session.setAttribute("userStatus", user.getStatus());
            session.setAttribute("user", user); // Store entire user object
            
            // Set role-specific attributes for backward compatibility
            if (user.isAdmin()) {
                session.setAttribute("adminId", user.getUserId());
                session.setAttribute("adminUsername", user.getUsername());
                session.setAttribute("adminName", user.getFullName());
                session.setAttribute("adminPhone", user.getPhone());
            }
            
            // Set display name
            String displayName = user.getFullName() != null && !user.getFullName().trim().isEmpty() 
                                ? user.getFullName() 
                                : user.getUsername();
            session.setAttribute("displayName", displayName);
            
            // Role-based redirect
            String redirectURL = getRedirectURL(user, request);
            response.sendRedirect(redirectURL);
            
        } else {
            // Login failed
            request.setAttribute("mess", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String redirectURL = getRedirectURL(user, request);
            response.sendRedirect(redirectURL);
        } else {
            // Not logged in, show login page
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }
    
    /**
     * Determine redirect URL based on user role
     */
    private String getRedirectURL(User user, HttpServletRequest request) {
        String contextPath = request.getContextPath();
        
        switch (user.getRole()) {
            case UserDAO.ROLE_ADMIN:
                return contextPath + "/Admin.jsp";
                
            case UserDAO.ROLE_MANAGER:
                return contextPath + "/Manager.jsp"; // Create this if needed
                
            case UserDAO.ROLE_CUSTOMER:
            default:
                return contextPath + "/Home.jsp";
        }
    }
}