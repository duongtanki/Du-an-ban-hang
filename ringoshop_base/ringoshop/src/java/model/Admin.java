package model;

import java.sql.Timestamp;

/**
 * Model class cho Admin - Quản trị viên hệ thống Ringgo (Version đơn giản)
 */
public class Admin {
    
    // Thuộc tính cơ bản
    private int adminId;
    private String username;
    private String password;
    private String fullName;

    private String phone;
    
    // Trạng thái đơn giản
    private String status; // ACTIVE, INACTIVE
    
    // Thông tin thời gian
    private Timestamp lastLoginTime;
    private Timestamp createdAt;
    
    // Constructors
    public Admin() {}
    
    public Admin(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.status = "ACTIVE"; // Mặc định
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
    
    // Constructor đầy đủ cho việc lấy từ database
    public Admin(int adminId, String username, String fullName, 
                String phone, String status, Timestamp lastLoginTime) {
        this.adminId = adminId;
        this.username = username;
        this.fullName = fullName;
        this.phone = phone;
        this.status = status;
        this.lastLoginTime = lastLoginTime;
    }
    
    // Getters và Setters
    public int getAdminId() {
        return adminId;
    }
    
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    

    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Timestamp getLastLoginTime() {
        return lastLoginTime;
    }
    
    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // Utility methods đơn giản
    
    /**
     * Kiểm tra admin có đang hoạt động không
     */
    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }
    
    /**
     * Cập nhật thời gian đăng nhập cuối
     */
    public void updateLastLogin() {
        this.lastLoginTime = new Timestamp(System.currentTimeMillis());
    }
    
    /**
     * Lấy tên hiển thị (ưu tiên fullName, fallback username)
     */
    public String getDisplayName() {
        return (fullName != null && !fullName.trim().isEmpty()) ? fullName : username;
    }
    
    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}