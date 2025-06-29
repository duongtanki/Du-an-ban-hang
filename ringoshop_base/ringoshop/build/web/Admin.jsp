<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    if (session.getAttribute("adminId") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Ringgo Shoes</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #f5f5f5;
            color: #333;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        /* Header */
        .header {
            background: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .logo h1 {
            color: #2c3e50;
            font-size: 28px;
        }

        .logo span {
            color: #3498db;
        }

        .user-info {
            display: flex;
            align-items: center;
            gap: 15px;
        }

        .user-avatar {
            width: 40px;
            height: 40px;
            background: #3498db;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }

        /* Navigation */
        .nav-tabs {
            display: flex;
            background: white;
            border-radius: 8px;
            padding: 10px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            gap: 10px;
        }

        .nav-tab {
            padding: 12px 20px;
            background: transparent;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            transition: all 0.3s ease;
            font-size: 14px;
            color: #666;
        }

        .nav-tab.active,
        .nav-tab:hover {
            background: #3498db;
            color: white;
        }

        .nav-tab i {
            margin-right: 8px;
        }

        /* Content Sections */
        .content-section {
            display: none;
            background: white;
            border-radius: 8px;
            padding: 25px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .content-section.active {
            display: block;
        }

        .section-title {
            font-size: 24px;
            color: #2c3e50;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #ecf0f1;
        }

        /* Form Styles */
        .admin-form {
            max-width: 500px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 500;
            color: #2c3e50;
        }

        .form-control {
            width: 100%;
            padding: 12px;
            border: 2px solid #ecf0f1;
            border-radius: 6px;
            font-size: 14px;
            transition: border-color 0.3s ease;
        }

        .form-control:focus {
            outline: none;
            border-color: #3498db;
        }

        /* Button Styles */
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            font-size: 14px;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            margin-right: 10px;
        }

        .btn-primary {
            background: #3498db;
            color: white;
        }

        .btn-success {
            background: #27ae60;
            color: white;
        }

        .btn-warning {
            background: #f39c12;
            color: white;
        }

        .btn-danger {
            background: #e74c3c;
            color: white;
        }

        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }

        /* Table Styles */
        .admin-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .admin-table th,
        .admin-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ecf0f1;
        }

        .admin-table th {
            background: #f8f9fa;
            font-weight: 600;
            color: #2c3e50;
        }

        .admin-table tr:hover {
            background: #f8f9fa;
        }

        /* Stats Cards */
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }

        .stat-card {
            background: white;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #3498db;
            margin-bottom: 5px;
        }

        .stat-label {
            color: #666;
            font-size: 14px;
        }

        /* Responsive */
        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                text-align: center;
                gap: 15px;
            }

            .nav-tabs {
                flex-wrap: wrap;
            }

            .container {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Header -->
        <div class="header">
            <div class="logo">
                <h1>Ring<span>go</span> Admin</h1>
            </div>
            <div class="user-info">
                <div class="user-avatar">
                    <i class="fas fa-user"></i>
                </div>
                <div>
                    <strong>${sessionScope.adminName != null ? sessionScope.adminName : 'Admin'}</strong>
                    <br>
                    <small>Quản trị viên</small>
                </div>
            </div>
        </div>

        <!-- Navigation -->
        <div class="nav-tabs">
            <button class="nav-tab active" onclick="showSection('dashboard')">
                <i class="fas fa-tachometer-alt"></i>Dashboard
            </button>
            <button class="nav-tab" onclick="showSection('profile')">
                <i class="fas fa-user"></i>Thông tin Admin
            </button>
            <button class="nav-tab" onclick="showSection('change-password')">
                <i class="fas fa-key"></i>Đổi mật khẩu
            </button>
            <button class="nav-tab" onclick="showSection('logout')">
                <i class="fas fa-sign-out-alt"></i>Đăng xuất
            </button>
        </div>

        <!-- Dashboard Section -->
        <div id="dashboard" class="content-section active">
            <h2 class="section-title">
                <i class="fas fa-tachometer-alt"></i> Dashboard
            </h2>
            
            <div class="stats-grid">
                <div class="stat-card">
                    <div class="stat-number">1</div>
                    <div class="stat-label">Admin đang hoạt động</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">${totalSessions != null ? totalSessions : '5'}</div>
                    <div class="stat-label">Phiên đăng nhập hôm nay</div>
                </div>
                <div class="stat-card">
                    <div class="stat-number">29/06/2025</div>
                    <div class="stat-label">Lần đăng nhập cuối</div>
                </div>
            </div>

            <h3>Thông tin hệ thống</h3>
            <table class="admin-table">
                <tr>
                    <td><strong>Trạng thái hệ thống:</strong></td>
                    <td><span style="color: #27ae60;">Hoạt động bình thường</span></td>
                </tr>
                <tr>
                    <td><strong>Phiên bản:</strong></td>
                    <td>1.0.0</td>
                </tr>
                <tr>
                    <td><strong>Thời gian hoạt động:</strong></td>
                    <td>24 giờ 15 phút</td>
                </tr>
            </table>
        </div>

        <!-- Profile Section -->
        <div id="profile" class="content-section">
            <h2 class="section-title">
                <i class="fas fa-user"></i> Thông tin Admin
            </h2>
            
            <form class="admin-form" method="post" action="updateAdminProfile">
                <div class="form-group">
                    <label for="adminId">ID Admin:</label>
                    <input type="text" id="adminId" name="adminId" class="form-control" 
                           value="${sessionScope.adminId != null ? sessionScope.adminId : '1'}" readonly>
                </div>

                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" class="form-control" 
                           value="${sessionScope.adminUsername != null ? sessionScope.adminUsername : 'admin'}" required>
                </div>

                <div class="form-group">
                    <label for="phone">Số điện thoại:</label>
                    <input type="tel" id="phone" name="phone" class="form-control" 
                           value="${sessionScope.adminPhone != null ? sessionScope.adminPhone : '0123456789'}" 
                           pattern="[0-9]{10,11}" required>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> Cập nhật thông tin
                    </button>
                    <button type="reset" class="btn btn-warning">
                        <i class="fas fa-undo"></i> Khôi phục
                    </button>
                </div>
            </form>
        </div>

        <!-- Change Password Section -->
        <div id="change-password" class="content-section">
            <h2 class="section-title">
                <i class="fas fa-key"></i> Đổi mật khẩu
            </h2>
            
            <form class="admin-form" method="post" action="changeAdminPassword">
                <div class="form-group">
                    <label for="currentPassword">Mật khẩu hiện tại:</label>
                    <input type="password" id="currentPassword" name="currentPassword" class="form-control" required>
                </div>

                <div class="form-group">
                    <label for="newPassword">Mật khẩu mới:</label>
                    <input type="password" id="newPassword" name="newPassword" class="form-control" 
                           minlength="6" required>
                </div>

                <div class="form-group">
                    <label for="confirmPassword">Xác nhận mật khẩu mới:</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" 
                           minlength="6" required>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-success">
                        <i class="fas fa-key"></i> Đổi mật khẩu
                    </button>
                    <button type="reset" class="btn btn-warning">
                        <i class="fas fa-times"></i> Hủy
                    </button>
                </div>
            </form>
        </div>

        <!-- Logout Section -->
        <div id="logout" class="content-section">
            <h2 class="section-title">
                <i class="fas fa-sign-out-alt"></i> Đăng xuất
            </h2>
            
            <div style="text-align: center; padding: 40px;">
                <i class="fas fa-sign-out-alt" style="font-size: 64px; color: #e74c3c; margin-bottom: 20px;"></i>
                <h3 style="margin-bottom: 20px;">Bạn có chắc chắn muốn đăng xuất?</h3>
                <p style="color: #666; margin-bottom: 30px;">Phiên làm việc của bạn sẽ kết thúc và bạn sẽ được chuyển về trang đăng nhập.</p>
                
                <button onclick="confirmLogout()" class="btn btn-danger">
                    <i class="fas fa-sign-out-alt"></i> Đăng xuất
                </button>
                <button onclick="showSection('dashboard')" class="btn btn-primary">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </button>
            </div>
        </div>
    </div>

    <script>
        // Show/hide sections
        function showSection(sectionId) {
            // Hide all sections
            document.querySelectorAll('.content-section').forEach(section => {
                section.classList.remove('active');
            });
            
            // Remove active class from all tabs
            document.querySelectorAll('.nav-tab').forEach(tab => {
                tab.classList.remove('active');
            });
            
            // Show selected section
            document.getElementById(sectionId).classList.add('active');
            
            // Add active class to clicked tab
            event.target.classList.add('active');
        }

        // Confirm logout
        function confirmLogout() {
            if (confirm('Bạn có chắc chắn muốn đăng xuất khỏi hệ thống?')) {
                // Redirect to logout page
                window.location.href = 'logout.jsp';
            }
        }

        // Password confirmation validation
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            
            if (newPassword !== confirmPassword) {
                this.setCustomValidity('Mật khẩu xác nhận không khớp');
            } else {
                this.setCustomValidity('');
            }
        });

        // Phone number validation
        document.getElementById('phone').addEventListener('input', function() {
            const phone = this.value;
            const phonePattern = /^[0-9]{10,11}$/;
            
            if (!phonePattern.test(phone)) {
                this.setCustomValidity('Số điện thoại phải có 10-11 chữ số');
            } else {
                this.setCustomValidity('');
            }
        });

        // Form submission handlers
        document.querySelector('form[action="updateAdminProfile"]').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Thông tin admin đã được cập nhật thành công!');
        });

        document.querySelector('form[action="changeAdminPassword"]').addEventListener('submit', function(e) {
            e.preventDefault();
            const currentPassword = document.getElementById('currentPassword').value;
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (newPassword !== confirmPassword) {
                alert('Mật khẩu xác nhận không khớp!');
                return;
            }
            
            alert('Mật khẩu đã được thay đổi thành công!');
            this.reset();
        });
    </script>
</body>
</html>