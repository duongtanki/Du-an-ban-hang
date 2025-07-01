package controller;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import model.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;


public class LoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("user");
        String password = request.getParameter("pass");

        Admin admin = adminDAO.getAdminByUsername(username);

        if (admin != null && admin.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("adminId", admin.getAdminId());
            session.setAttribute("adminUsername", admin.getUsername());
            session.setAttribute("adminName", admin.getDisplayName());
            session.setAttribute("adminPhone", admin.getPhone());

            // ✅ Dùng URL đúng tới JSP (đừng redirect vào servlet hoặc class)
            response.sendRedirect(request.getContextPath() + "/Admin.jsp");
        } else {
            request.setAttribute("mess", "Tên đăng nhập hoặc mật khẩu không đúng");
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("Admin.jsp");
    }
}
