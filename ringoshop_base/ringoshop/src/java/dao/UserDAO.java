package dao;

import model.User;
import utils.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Unified DAO for both Admin and Customer authentication
 * Replaces LoginDAO + AdminDAO + AdminDAOImpl
 * @author duongtanki
 */
public class UserDAO {
    
    // Role constants
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_CUSTOMER = 2;
    public static final int ROLE_MANAGER = 3;
    
    // Status constants
    public static final String STATUS_ACTIVE = "ACTIVE";
    public static final String STATUS_INACTIVE = "INACTIVE";
    public static final String STATUS_SUSPENDED = "SUSPENDED";
    
    /**
     * Unified Authentication - Replaces LoginDAO.checkLogin() + AdminDAO.getAdminByUsername()
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, STATUS_ACTIVE);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = mapResultSetToUser(rs);
                // Update last login time
                updateLastLoginTime(user.getUserId());
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Authentication error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Check if username exists - Replaces LoginDAO.isExit()
     */
    public boolean isUserExist(String username) {
        String sql = "SELECT COUNT(*) as count FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
        } catch (SQLException e) {
            System.err.println("Check user exist error: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Register new customer - Replaces LoginDAO.insertLogin() (NO EMAIL)
     */
    public boolean registerCustomer(String username, String password, String fullName, String phone) {
        String sql = "INSERT INTO users (username, password, full_name, phone, role, status, created_at) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password); // TODO: Hash password
            ps.setString(3, fullName);
            ps.setString(4, phone);
            ps.setInt(5, ROLE_CUSTOMER);
            ps.setString(6, STATUS_ACTIVE);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Register customer error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add new admin - Replaces AdminDAO.addAdmin() (NO EMAIL)
     */
    public boolean addAdmin(String username, String password, String fullName, String phone) {
        String sql = "INSERT INTO users (username, password, full_name, phone, role, status, created_at) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password); // TODO: Hash password
            ps.setString(3, fullName);
            ps.setString(4, phone);
            ps.setInt(5, ROLE_ADMIN);
            ps.setString(6, STATUS_ACTIVE);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add admin error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Add new manager
     */
    public boolean addManager(String username, String password, String fullName, String phone) {
        String sql = "INSERT INTO users (username, password, full_name, phone, role, status, created_at) VALUES (?, ?, ?, ?, ?, ?, GETDATE())";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password); // TODO: Hash password
            ps.setString(3, fullName);
            ps.setString(4, phone);
            ps.setInt(5, ROLE_MANAGER);
            ps.setString(6, STATUS_ACTIVE);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Add manager error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get user by ID - Enhanced from AdminDAO.getAdminById()
     */
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE id = ?";  // Fixed: use 'id' not 'user_id'
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Get user by ID error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get user by username - Enhanced from AdminDAO.getAdminByUsername()
     */
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Get user by username error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * Get all users by role
     */
    public List<User> getUsersByRole(int role) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ? ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Get users by role error: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * Get all admins - Replaces AdminDAO.getAllAdmins()
     */
    public List<User> getAllAdmins() {
        return getUsersByRole(ROLE_ADMIN);
    }
    
    /**
     * Get all customers
     */
    public List<User> getAllCustomers() {
        return getUsersByRole(ROLE_CUSTOMER);
    }
    
    /**
     * Get all managers
     */
    public List<User> getAllManagers() {
        return getUsersByRole(ROLE_MANAGER);
    }
    
    /**
     * Update user profile - Enhanced from AdminDAO.updateAdminProfile() (NO EMAIL)
     */
    public boolean updateUserProfile(User user) {
        String sql = "UPDATE users SET username = ?, full_name = ?, phone = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getFullName());
            ps.setString(3, user.getPhone());
            ps.setInt(4, user.getUserId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update user profile error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update password - Enhanced from AdminDAO.updateAdminPassword()
     */
    public boolean updatePassword(int userId, String newPassword) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, newPassword); // TODO: Hash password
            ps.setInt(2, userId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update password error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Update user status
     */
    public boolean updateUserStatus(int userId, String status) {
        String sql = "UPDATE users SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, status);
            ps.setInt(2, userId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update user status error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Delete user - Enhanced from AdminDAO.deleteAdmin()
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Delete user error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Soft delete user (set status to INACTIVE)
     */
    public boolean deactivateUser(int userId) {
        return updateUserStatus(userId, STATUS_INACTIVE);
    }
    
    /**
     * Suspend user
     */
    public boolean suspendUser(int userId) {
        return updateUserStatus(userId, STATUS_SUSPENDED);
    }
    
    /**
     * Activate user
     */
    public boolean activateUser(int userId) {
        return updateUserStatus(userId, STATUS_ACTIVE);
    }
    
    /**
     * Count active users by role - Enhanced from AdminDAO.countActiveAdmins()
     */
    public int countActiveUsersByRole(int role) {
        String sql = "SELECT COUNT(*) as total FROM users WHERE role = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, role);
            ps.setString(2, STATUS_ACTIVE);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.err.println("Count active users error: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    
    /**
     * Count active admins
     */
    public int countActiveAdmins() {
        return countActiveUsersByRole(ROLE_ADMIN);
    }
    
    /**
     * Count active customers
     */
    public int countActiveCustomers() {
        return countActiveUsersByRole(ROLE_CUSTOMER);
    }
    
    /**
     * Count active managers
     */
    public int countActiveManagers() {
        return countActiveUsersByRole(ROLE_MANAGER);
    }
    
    /**
     * Update last login time - Enhanced from AdminDAO.updateLastLoginTime()
     */
    public boolean updateLastLoginTime(int userId) {
        String sql = "UPDATE users SET last_login_time = GETDATE() WHERE id = ?";
        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Update last login time error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Helper method to map ResultSet to User object (NO EMAIL)
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("id"));  // Fixed: use 'id' not 'user_id'
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("full_name"));
        user.setPhone(rs.getString("phone"));
        user.setRole(rs.getInt("role"));
        user.setStatus(rs.getString("status"));
        user.setLastLoginTime(rs.getTimestamp("last_login_time"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
    
    /**
     * Role checking helper methods
     */
    public boolean isAdmin(User user) {
        return user != null && user.getRole() == ROLE_ADMIN;
    }
    
    public boolean isCustomer(User user) {
        return user != null && user.getRole() == ROLE_CUSTOMER;
    }
    
    public boolean isManager(User user) {
        return user != null && user.getRole() == ROLE_MANAGER;
    }
    
    public boolean isActive(User user) {
        return user != null && STATUS_ACTIVE.equals(user.getStatus());
    }
    
    /**
     * Convenience methods for common operations
     */
    public User login(String username, String password) {
        return authenticate(username, password);
    }
    
    public boolean signup(String username, String password, String fullName, String phone) {
        return registerCustomer(username, password, fullName, phone);
    }
    
    public boolean usernameExists(String username) {
        return isUserExist(username);
    }
}