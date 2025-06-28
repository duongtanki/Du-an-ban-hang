package controller;

import dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author duongtanki
 */
@WebServlet(name = "SignupServlet", urlPatterns = {"/signup"})
public class SignupServlet extends HttpServlet {

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
        String repass = (String)request.getParameter("repass");
        LoginDAO u = new LoginDAO();
        int role = u.isExit(user);
        if (role == 1 || role == 2) {
            request.setAttribute("error", "Tên đăng nhập đã tồn tại!");
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
        } else {
            if (!pass.equals(repass)) {
                request.setAttribute("error", "Mật khẩu không trùng nhau!");
                request.getRequestDispatcher("Signup.jsp").forward(request, response);
            } else {
                u.insertLogin(user, pass); // Trả true nếu thêm thành công
                request.setAttribute("success", "Đăng ký thành công! Vui lòng đăng nhập.");
                request.getRequestDispatcher("Signup.jsp").forward(request, response);
            }
        }
    }

    
}
