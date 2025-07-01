<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <style>
        .form-container { max-width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input { width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 3px; }
        .btn { background-color: #007bff; color: white; padding: 10px 20px; border: none; border-radius: 3px; cursor: pointer; }
        .btn:hover { background-color: #0056b3; }
        .error { color: red; margin-bottom: 15px; }
        .success { color: green; margin-bottom: 15px; }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>Đăng Ký Tài Khoản</h2>
        
        <% if (request.getAttribute("error") != null) { %>
            <div class="error"><%= request.getAttribute("error") %></div>
        <% } %>
        
        <% if (request.getAttribute("success") != null) { %>
            <div class="success"><%= request.getAttribute("success") %></div>
        <% } %>
        
        <form action="signup" method="post">
            <div class="form-group">
                <label for="user">Tên đăng nhập *:</label>
                <input type="text" id="user" name="user" required>
            </div>
            
            <div class="form-group">
                <label for="fullName">Họ và tên:</label>
                <input type="text" id="fullName" name="fullName">
            </div>
            
            <div class="form-group">
                <label for="phone">Số điện thoại:</label>
                <input type="tel" id="phone" name="phone">
            </div>
            
            <div class="form-group">
                <label for="pass">Mật khẩu *:</label>
                <input type="password" id="pass" name="pass" required>
            </div>
            
            <div class="form-group">
                <label for="repass">Nhập lại mật khẩu *:</label>
                <input type="password" id="repass" name="repass" required>
            </div>
            
            <button type="submit" class="btn">Đăng Ký</button>
        </form>
        
        <p><a href="Login.jsp">Đã có tài khoản? Đăng nhập</a></p>
    </div>
</body>
</html>