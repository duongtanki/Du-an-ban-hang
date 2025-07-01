<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign Up Form</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div class="container mt-5" style="max-width: 400px;">
        <form class="form-signup" action="signup" method="post">
            <h1 class="h3 mb-3 font-weight-normal text-center">Sign up</h1>
            
            <c:if test="${not empty success}">
                <div class="alert alert-success text-center" role="alert">
                    ${success}
                </div>
            </c:if>

            <c:if test="${not empty error}">
                <div class="alert alert-danger text-center" role="alert">
                    ${error}
                </div>
            </c:if>


            <input name="user" type="text" class="form-control mb-2" placeholder="User name" required autofocus>
            <input name="pass" type="password" class="form-control mb-2" placeholder="Password" required>
            <input name="repass" type="password" class="form-control mb-2" placeholder="Repeat Password" required>

            <button class="btn btn-primary btn-block" type="submit">
                <i class="fas fa-user-plus"></i> Sign Up
            </button>

            <a href="Login.jsp" class="btn btn-link mt-2">
                <i class="fas fa-angle-left"></i> Back to Login
            </a>
        </form>
    </div>
</body>
</html>
