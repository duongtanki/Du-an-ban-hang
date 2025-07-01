package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.User;
import java.io.IOException;

@WebServlet(urlPatterns = {"/admin", "/updateAdminProfile", "/changeAdminPassword"})
public class AdminServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Check if user is logged in and is admin
        if (!isAdminLoggedIn(request)) {
            response.sendRedirect("Login.jsp");
            return;
        }
        
        response.sendRedirect("Admin.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String path = request.getServletPath();
        HttpSession session = request.getSession();
        
        // Check if user is logged in and is admin
        if (!isAdminLoggedIn(request)) {
            response.sendRedirect("Login.jsp");
            return;
        }

        if (path.equals("/updateAdminProfile")) {
            handleUpdateProfile(request, response, session);
        } else if (path.equals("/changeAdminPassword")) {
            handleChangePassword(request, response, session);
        }
    }
    
    /**
     * Handle profile update
     */
    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        
        try {
            String userIdStr = request.getParameter("adminId"); // Keep parameter name for compatibility
            String username = request.getParameter("username");
            String fullName = request.getParameter("fullName"); // Add full name support
            String phone = request.getParameter("phone");
            
            if (userIdStr == null || username == null) {
                response.sendRedirect("Admin.jsp?update=fail&error=missing_data");
                return;
            }
            
            int userId = Integer.parseInt(userIdStr);
            
            // Get current user and verify it's the same user
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null || currentUser.getUserId() != userId) {
                response.sendRedirect("Admin.jsp?update=fail&error=unauthorized");
                return;
            }
            
            // Check if username already exists (if changed)
            if (!username.equals(currentUser.getUsername()) && userDAO.usernameExists(username)) {
                response.sendRedirect("Admin.jsp?update=fail&error=username_exists");
                return;
            }
            
            // Update user object
            currentUser.setUsername(username);
            currentUser.setFullName(fullName);
            currentUser.setPhone(phone);
            
            boolean updated = userDAO.updateUserProfile(currentUser);
            
            if (updated) {
                // Update session attributes
                session.setAttribute("user", currentUser);
                session.setAttribute("username", username);
                session.setAttribute("adminUsername", username); // Backward compatibility
                session.setAttribute("adminName", fullName);     // Backward compatibility
                session.setAttribute("adminPhone", phone);       // Backward compatibility
                session.setAttribute("displayName", currentUser.getDisplayName());
                
                response.sendRedirect("Admin.jsp?update=success");
            } else {
                response.sendRedirect("Admin.jsp?update=fail&error=database_error");
            }
            
        } catch (NumberFormatException e) {
            response.sendRedirect("Admin.jsp?update=fail&error=invalid_id");
        } catch (Exception e) {
            System.err.println("Update profile error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("Admin.jsp?update=fail&error=system_error");
        }
    }
    
    /**
     * Handle password change
     */
    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response, HttpSession session)
            throws ServletException, IOException {
        
        try {
            User currentUser = (User) session.getAttribute("user");
            if (currentUser == null) {
                response.sendRedirect("Login.jsp");
                return;
            }
            
            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");
            
            // Validation
            if (currentPassword == null || newPassword == null || confirmPassword == null ||
                currentPassword.trim().isEmpty() || newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                response.sendRedirect("Admin.jsp?password=fail&error=missing_data");
                return;
            }
            
            // Get fresh user data from database
            User user = userDAO.getUserById(currentUser.getUserId());
            if (user == null) {
                response.sendRedirect("Login.jsp");
                return;
            }
            
            // Verify current password
            if (!user.getPassword().equals(currentPassword)) {
                response.sendRedirect("Admin.jsp?password=wrongcurrent");
                return;
            }
            
            // Check if new passwords match
            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect("Admin.jsp?password=notmatch");
                return;
            }
            
            // Check password strength (optional)
            if (newPassword.length() < 6) {
                response.sendRedirect("Admin.jsp?password=fail&error=weak_password");
                return;
            }
            
            // Update password
            boolean changed = userDAO.updatePassword(user.getUserId(), newPassword);
            
            if (changed) {
                // Update session user object
                currentUser.setPassword(newPassword);
                session.setAttribute("user", currentUser);
                response.sendRedirect("Admin.jsp?password=changed");
            } else {
                response.sendRedirect("Admin.jsp?password=fail&error=database_error");
            }
            
        } catch (Exception e) {
            System.err.println("Change password error: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("Admin.jsp?password=fail&error=system_error");
        }
    }
    
    /**
     * Check if current user is logged in and is admin
     */
    private boolean isAdminLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        
        User user = (User) session.getAttribute("user");
        return user != null && user.isAdmin() && user.isActive();
    }
}