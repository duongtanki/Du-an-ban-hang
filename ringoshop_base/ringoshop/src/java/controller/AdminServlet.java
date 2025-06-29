package controller;

import dao.AdminDAO;
import dao.AdminDAOImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Admin;

import java.io.IOException;

@WebServlet(urlPatterns = {"/updateAdminProfile", "/changeAdminPassword"})
public class AdminServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("admin/Admin.jsp"); // Đảm bảo file này tồn tại
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getServletPath();
        HttpSession session = request.getSession();

        if (path.equals("/updateAdminProfile")) {
            String adminIdStr = request.getParameter("adminId");
            String username = request.getParameter("username");
            String phone = request.getParameter("phone");

            int adminId = Integer.parseInt(adminIdStr);

            Admin admin = new Admin();
            admin.setAdminId(adminId);
            admin.setUsername(username);
            admin.setPhone(phone);

            boolean updated = adminDAO.updateAdminProfile(admin);

            if (updated) {
                session.setAttribute("adminUsername", username);
                session.setAttribute("adminPhone", phone);
                response.sendRedirect("admin/Admin.jsp?update=success");
            } else {
                response.sendRedirect("admin/Admin.jsp?update=fail");
            }

        } else if (path.equals("/changeAdminPassword")) {
            Object adminIdObj = session.getAttribute("adminId");
            if (adminIdObj == null) {
                response.sendRedirect("login.jsp");
                return;
            }
            int adminId = (int) adminIdObj;

            String currentPassword = request.getParameter("currentPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            Admin admin = adminDAO.getAdminById(adminId);

            if (admin != null && admin.getPassword().equals(currentPassword)) {
                if (newPassword.equals(confirmPassword)) {
                    admin.setPassword(newPassword);
                    boolean changed = adminDAO.updateAdminPassword(admin);
                    if (changed) {
                        response.sendRedirect("admin/Admin.jsp?password=changed");
                    } else {
                        response.sendRedirect("admin/Admin.jsp?password=fail");
                    }
                } else {
                    response.sendRedirect("admin/Admin.jsp?password=notmatch");
                }
            } else {
                response.sendRedirect("admin/Admin.jsp?password=wrongcurrent");
            }
        }
    }
}
