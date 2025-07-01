<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign in - Ringo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-6 col-lg-4">
                <div class="card mt-5">
                    <div class="card-body p-4">
                        
                        <!-- LOGO/BRAND - Link back home -->
                        <div class="text-center mb-4">
                            <a href="Home.jsp" class="text-decoration-none">
                                <h3 class="text-primary mb-1">
                                    <strong>Ringo</strong>
                                </h3>
                            </a>
                        </div>
                        
                        <h4 class="text-center mb-4">Sign in</h4>
                       
                        
                        <!-- Login Form -->
                        <form action="login" method="post">
                            <div class="mb-3">
                                <input type="text" class="form-control" name="user" placeholder="Username" required>
                            </div>
                            <div class="mb-3">
                                <input type="password" class="form-control" name="pass" placeholder="Password" required>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="remember">
                                <label class="form-check-label" for="remember">Remember me</label>
                            </div>
                            
                            <button type="submit" class="btn btn-success w-100 mb-3">
                                Sign in
                            </button>
                            
                            <a href="Signup.jsp" class="btn btn-primary w-100">
                                Sign up New Account
                            </a>
                        </form>
                        
                        <!-- Footer Links -->
                        <div class="text-center mt-4">
                            <small>
                                <a href="Home.jsp" class="text-muted me-3">Continue shopping</a>
                                <a href="#" class="text-muted">Forgot Password</a>
                            </small>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>