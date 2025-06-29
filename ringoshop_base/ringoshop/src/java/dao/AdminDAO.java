package dao;

import model.Admin;
import java.util.List;

public interface AdminDAO {

    // Thêm mới admin
    boolean addAdmin(Admin admin);

    // Lấy tất cả admin
    List<Admin> getAllAdmins();

    // Lấy admin theo ID
    Admin getAdminById(int adminId);

    // Lấy admin theo tên đăng nhập (dùng cho login)
    Admin getAdminByUsername(String username);

    // Cập nhật thông tin profile (username, phone)
    boolean updateAdminProfile(Admin admin);

    // Cập nhật mật khẩu
    boolean updateAdminPassword(Admin admin);

    // Xóa admin
    boolean deleteAdmin(int adminId);

    // Đếm số lượng admin đang hoạt động
    int countActiveAdmins();

    // Cập nhật thời gian đăng nhập cuối
    boolean updateLastLoginTime(int adminId);
}
