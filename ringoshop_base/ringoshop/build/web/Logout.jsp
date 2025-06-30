<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("adminId") == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Đăng xuất - Xác nhận</title>
    <style>
        body {
            font-family: sans-serif;
            text-align: center;
            padding: 50px;
            background-color: #f7f7f7;
        }
        .box {
            background: white;
            padding: 40px;
            border-radius: 8px;
            display: inline-block;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            color: #e74c3c;
        }
        .btn {
            padding: 12px 24px;
            margin: 10px;
            border: none;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            text-decoration: none;
        }
        .btn-danger {
            background-color: #e74c3c;
            color: white;
        }
        .btn-secondary {
            background-color: #3498db;
            color: white;
        }
    </style>
</head>
<body>
    <div class="box">
        <h2>Đăng xuất?</h2>
        <p>Bạn có chắc chắn muốn đăng xuất không?</p>
        <a href="logout" class="btn btn-danger">Đăng xuất</a>
        <a href="Admin.jsp" class="btn btn-secondary">Hủy</a>
    </div>
</body>
</html>
