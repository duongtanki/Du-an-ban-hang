CREATE TABLE admins (
    admin_id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100),
    email VARCHAR(100),
    phone VARCHAR(20),
    status VARCHAR(10) DEFAULT 'ACTIVE',
    last_login_time DATETIME NULL,
    created_at DATETIME DEFAULT GETDATE()
);