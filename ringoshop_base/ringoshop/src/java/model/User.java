package model;

import java.sql.Timestamp;

public class User {

    private int userId;
    private String username;
    private String password;
    private String fullName;
    private String phone;
    private Timestamp lastLoginTime;
    private Timestamp createdAt;
    private int role;           // 1=Admin, 2=Customer, 3=Manager
    private String status;      // ACTIVE, INACTIVE, SUSPENDED

    // Constructors
    public User() {
    }

    public User(String username, String password, int role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = "ACTIVE";  // Default status
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    // Helper methods
    public boolean isAdmin() {
        return role == 1;
    }

    public boolean isCustomer() {
        return role == 2;
    }

    public boolean isManager() {
        return role == 3;
    }

    public String getRoleName() {
        switch (role) {
            case 1:
                return "Admin";
            case 2:
                return "Customer";
            case 3:
                return "Manager";
            default:
                return "Unknown";
        }
    }

    public boolean isActive() {
        return "ACTIVE".equals(this.status);
    }

    public boolean isInactive() {
        return "INACTIVE".equals(this.status);
    }

    public boolean isSuspended() {
        return "SUSPENDED".equals(this.status);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", roleName='" + getRoleName() + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", lastLoginTime=" + lastLoginTime +
                '}';
    }

    // Additional utility methods
    public String getDisplayName() {
        return fullName != null && !fullName.trim().isEmpty() ? fullName : username;
    }

    public boolean canLogin() {
        return isActive();
    }

    public String getFormattedRole() {
        return getRoleName() + " (" + role + ")";
    }
}