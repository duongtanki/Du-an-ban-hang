package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Unified Signup Servlet using UserDAO
 * @author duongtanki
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/signup"})
public class SignupServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    public void init() throws ServletException {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("Signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        // Get parameters
        String username = request.getParameter("user");
        String password = request.getParameter("pass");
        String repassword = request.getParameter("repass");
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        
        // Validation
        String validationError = validateInput(username, password, repassword, fullName, phone);
        if (validationError != null) {
            request.setAttribute("error", validationError);
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
            return;
        }
        
        // Check if username already exists
        if (userDAO.usernameExists(username.trim())) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
            return;
        }
        
        // Register new customer
        boolean success = userDAO.registerCustomer(
            username.trim(), 
            password, 
            fullName != null ? fullName.trim() : null, 
            phone != null ? phone.trim() : null
        );
        
        if (success) {
            request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Đăng ký thất bại! Vui lòng thử lại.");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
        }
    }
    
    /**
     * Validate input parameters
     */
    private String validateInput(String username, String password, String repassword, String fullName, String phone) {
        
        // Check required fields
        if (username == null || username.trim().isEmpty()) {
            return "Vui lòng nhập tên đăng nhập!";
        }
        
        if (password == null || password.trim().isEmpty()) {
            return "Vui lòng nhập mật khẩu!";
        }
        
        if (repassword == null || repassword.trim().isEmpty()) {
            return "Vui lòng nhập lại mật khẩu!";
        }
        
        // Check password match
        if (!password.equals(repassword)) {
            return "Mật khẩu không trùng nhau!";
        }
        
        // Check username length
        if (username.trim().length() < 3) {
            return "Tên đăng nhập phải có ít nhất 3 ký tự!";
        }
        
        if (username.trim().length() > 50) {
            return "Tên đăng nhập không được quá 50 ký tự!";
        }
        
        // Check password strength
        if (password.length() < 6) {
            return "Mật khẩu phải có ít nhất 6 ký tự!";
        }
        
        if (password.length() > 100) {
            return "Mật khẩu không được quá 100 ký tự!";
        }
        
        // Check full name (optional but if provided, validate)
        if (fullName != null && !fullName.trim().isEmpty()) {
            if (fullName.trim().length() > 100) {
                return "Họ tên không được quá 100 ký tự!";
            }
        }
        
        // Check phone (optional but if provided, validate)
        if (phone != null && !phone.trim().isEmpty()) {
            if (!phone.trim().matches("^[0-9+\\-\\s()]{10,15}$")) {
                return "Số điện thoại không hợp lệ!";
            }
        }
        
        return null; // No validation errors
    }
}