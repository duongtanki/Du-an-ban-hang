package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); // Lấy session hiện tại (nếu có)
        if (session != null) {
            session.invalidate(); // Xoá toàn bộ thông tin đăng nhập
        }

        // Quay về trang đăng nhập sau khi logout
        response.sendRedirect("Login.jsp");
    }
}
