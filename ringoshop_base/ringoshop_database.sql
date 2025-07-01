-- 1. Tạo Database
-- Hãy đảm bảo bạn có quyền tạo database trên SQL Server
-- Nếu database đã tồn tại, bạn có thể bỏ qua bước này hoặc dùng IF NOT EXISTS
-- USE [master]; -- Chạy lệnh này nếu bạn đang ở một database khác
-- GO
--CREATE DATABASE ringoshop_database;
--GO

-- Sau khi tạo, chọn database để làm việc
USE ringoshop_database;
GO -- GO là lệnh phân tách batch trong SQL Server Management Studio

-- 2. Xóa bảng nếu chúng đã tồn tại (để chạy lại script dễ dàng)
-- Hãy cẩn thận khi chạy lệnh DROP TABLE trên môi trường thật
IF OBJECT_ID('shoe_variants', 'U') IS NOT NULL
    DROP TABLE shoe_variants;
GO

IF OBJECT_ID('shoes', 'U') IS NOT NULL
    DROP TABLE shoes;
GO

-- 3. Tạo Bảng `shoes`
CREATE TABLE shoes (
    id INT PRIMARY KEY IDENTITY(1,1), -- Sửa: AUTO_INCREMENT -> IDENTITY(1,1) cho SQL Server
    name NVARCHAR(255) NOT NULL, -- Sửa: VARCHAR -> NVARCHAR để hỗ trợ tiếng Việt tốt hơn
    title NVARCHAR(255) NOT NULL, -- Sửa: VARCHAR -> NVARCHAR
    description NVARCHAR(MAX), -- TEXT -> NVARCHAR(MAX) cho văn bản dài trong SQL Server
    main_image_url VARCHAR(255),
    price DECIMAL(10, 2) NOT NULL,
    brand NVARCHAR(100), -- Sửa: VARCHAR -> NVARCHAR
    category NVARCHAR(100), -- Sửa: VARCHAR -> NVARCHAR
    -- Sử dụng NVARCHAR cho gender và thêm CHECK CONSTRAINT để mô phỏng ENUM
    gender NVARCHAR(50) CHECK (gender IN (N'Nam', N'Nữ', N'Unisex', N'Trẻ em')),
    material NVARCHAR(100), -- Sửa: VARCHAR -> NVARCHAR
    release_date DATE,
    created_at DATETIME DEFAULT GETDATE(), -- Sửa: TIMESTAMP -> DATETIME, CURRENT_TIMESTAMP -> GETDATE()
    updated_at DATETIME DEFAULT GETDATE() -- Sửa: TIMESTAMP -> DATETIME, CURRENT_TIMESTAMP -> GETDATE()
    -- Lưu ý: Để updated_at tự động cập nhật khi có UPDATE, bạn cần tạo TRIGGER
);

CREATE TABLE login (
    id INT IDENTITY(1,1) PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role INT NOT NULL CHECK (role IN (1, 2)) -- 1: admin, 2: user
);


GO

-- TRIGGER để tự động cập nhật updated_at (tùy chọn nhưng được khuyến nghị cho SQL Server)
CREATE TRIGGER TR_shoes_UpdateTime
ON shoes
AFTER UPDATE
AS
BEGIN
    UPDATE shoes
    SET updated_at = GETDATE()
    FROM inserted i
    WHERE shoes.id = i.id;
END;
GO

-- 4. Tạo Bảng `shoe_variants`
CREATE TABLE shoe_variants (
    variant_id INT PRIMARY KEY IDENTITY(1,1), -- Sửa: AUTO_INCREMENT -> IDENTITY(1,1)
    shoe_id INT NOT NULL, -- Khóa ngoại liên kết đến `shoes.id`
    size DECIMAL(3,1) NOT NULL, -- Kích cỡ giày (ví dụ: 38.5, 40.0)
    color_name NVARCHAR(50), -- Sửa: VARCHAR -> NVARCHAR
    color_hex_code VARCHAR(7),
    stock_quantity INT NOT NULL DEFAULT 0,
    sku NVARCHAR(100) UNIQUE, -- Sửa: VARCHAR -> NVARCHAR
    variant_image_url VARCHAR(255),
    FOREIGN KEY (shoe_id) REFERENCES shoes(id) ON DELETE CASCADE
);
GO

-- 5. Thêm 20 Đôi Giày Mẫu vào Bảng `shoes`
INSERT INTO shoes (name, title, description, main_image_url, price, brand, category, gender, material, release_date) VALUES
(N'Nike Air Force 1 Low', N'Nike Air Force 1 Low White Classic', N'Mẫu giày kinh điển với thiết kế vượt thời gian, phù hợp mọi phong cách.', 'https://example.com/images/nike_af1_white.jpg', 2790000.00, N'Nike', N'Sneaker', N'Unisex', N'Da tổng hợp', '1982-10-01'),
(N'Adidas Ultraboost 22', N'Adidas Ultraboost 22 Running Shoes', N'Giày chạy bộ êm ái với công nghệ Boost tối ưu, mang lại trải nghiệm tuyệt vời.', 'https://example.com/images/adidas_ub22_black.jpg', 4200000.00, N'Adidas', N'Running', N'Unisex', N'Vải dệt kim', '2022-03-15'),
(N'Converse Chuck 70', N'Converse Chuck 70 High Top Black', N'Phiên bản cao cấp của Chuck Taylor All Star, chất liệu bền bỉ và thoải mái.', 'https://example.com/images/converse_chuck70_black.jpg', 1850000.00, N'Converse', N'Sneaker', N'Unisex', N'Vải Canvas', '2013-09-01'),
(N'Vans Old Skool', N'Vans Old Skool Black White', N'Biểu tượng của văn hóa trượt ván, phong cách cổ điển với sọc Jazz.', 'https://example.com/images/vans_oldskool_bw.jpg', 1600000.00, N'Vans', N'Skate', N'Unisex', N'Vải Canvas & Da lộn', '1977-01-01'),
(N'Puma Suede Classic', N'Puma Suede Classic XXI Black', N'Thiết kế thể thao cổ điển, chất liệu da lộn mềm mại và bền bỉ.', 'https://example.com/images/puma_suede_black.jpg', 1900000.00, N'Puma', N'Casual', N'Unisex', N'Da lộn', '1968-01-01'),
(N'New Balance 574', N'New Balance 574 Core Grey', N'Giày đi bộ thoải mái, phong cách retro, phù hợp cho daily wear.', 'https://example.com/images/nb_574_grey.jpg', 2200000.00, N'New Balance', N'Lifestyle', N'Unisex', N'Da lộn & Lưới', '1988-01-01'),
(N'Bitis Hunter X', N'Bitis Hunter X Festive Collection', N'Giày thể thao năng động, trọng lượng nhẹ, phù hợp cho nhiều hoạt động.', 'https://example.com/images/bitis_hunterx.jpg', 999000.00, N'Bitis Hunter', N'Sneaker', N'Unisex', N'Vải dệt', '2019-11-11'),
(N'Ananas Urbas Corluray', N'Ananas Urbas Corluray High Top Green', N'Giày vải canvas phong cách trẻ trung, màu sắc nổi bật.', 'https://example.com/images/ananas_urbas_green.jpg', 680000.00, N'Ananas', N'Sneaker', N'Unisex', N'Vải Corduroy', '2020-05-01'),
(N'Dr. Martens 1460', N'Dr. Martens 1460 Smooth Leather Black', N'Bốt cổ điển, bền bỉ, phong cách mạnh mẽ và cá tính.', 'https://example.com/images/drmartens_1460_black.jpg', 4800000.00, N'Dr. Martens', N'Boots', N'Unisex', N'Da thật', '1960-04-01'),
(N'Clarks Desert Boot', N'Clarks Desert Boot Beeswax Leather', N'Giày bốt cổ thấp lịch lãm, chất liệu da sáp ong đặc trưng.', 'https://example.com/images/clarks_desertboot_wax.jpg', 3500000.00, N'Clarks', N'Casual', N'Nam', N'Da sáp ong', '1950-07-01'),
(N'Jordan 1 Mid', N'Jordan 1 Mid Light Smoke Grey', N'Phiên bản mid-top của giày Air Jordan 1, phong cách bóng rổ.', 'https://example.com/images/jordan1_mid_grey.jpg', 3800000.00, N'Jordan', N'Basketball', N'Unisex', N'Da tổng hợp', '2020-08-01'),
(N'Reebok Club C 85', N'Reebok Club C 85 Vintage White', N'Giày tennis cổ điển, thiết kế tối giản, dễ phối đồ.', 'https://example.com/images/reebok_clubc85_white.jpg', 2100000.00, N'Reebok', N'Lifestyle', N'Unisex', N'Da thật', '1985-01-01'),
(N'Mizuno Wave Rider 26', N'Mizuno Wave Rider 26 Running Shoes', N'Giày chạy bộ chuyên nghiệp, đệm êm, hỗ trợ tốt cho sải chân.', 'https://example.com/images/mizuno_wr26_blue.jpg', 3000000.00, N'Mizuno', N'Running', N'Unisex', N'Vải lưới', '2022-09-01'),
(N'ASICS Gel-Kayano 29', N'ASICS Gel-Kayano 29 Stabilty Running', N'Giày chạy bộ ổn định, hỗ trợ tối đa cho người có sải chân lệch.', 'https://example.com/images/asics_kayano29_blue.jpg', 3900000.00, N'ASICS', N'Running', N'Unisex', N'Vải lưới', '2022-06-01'),
(N'Crocs Classic Clog', N'Crocs Classic Clog Navy', N'Dép đi chơi thoải mái, chống nước, dễ vệ sinh.', 'https://example.com/images/crocs_classic_navy.jpg', 850000.00, N'Crocs', N'Sandal', N'Unisex', N'Croslite', '2002-01-01'),
(N'Gucci Ace Sneaker', N'Gucci Ace Embroidered Sneaker', N'Giày sneaker cao cấp với họa tiết thêu ong đặc trưng của Gucci.', 'https://example.com/images/gucci_ace_bee.jpg', 18000000.00, N'Gucci', N'Luxury Sneaker', N'Unisex', N'Da thật', '2016-01-01'),
(N'Balenciaga Triple S', N'Balenciaga Triple S Trainer Black', N'Giày sneaker đế cồng kềnh, phong cách chunky đặc trưng.', 'https://example.com/images/balenciaga_triples_black.jpg', 25000000.00, N'Balenciaga', N'Luxury Sneaker', N'Unisex', N'Da & Lưới', '2017-09-01'),
(N'Fila Disruptor II', N'Fila Disruptor II White', N'Giày sneaker đế bánh mì, phong cách retro thập niên 90.', 'https://example.com/images/fila_disruptor2_white.jpg', 1500000.00, N'Fila', N'Sneaker', N'Nữ', N'Da tổng hợp', '1996-01-01'),
(N'Adidas Stan Smith', N'Adidas Stan Smith White Green', N'Giày tennis cổ điển, thiết kế tối giản, biểu tượng của Adidas.', 'https://example.com/images/adidas_stansmith_wg.jpg', 2300000.00, N'Adidas', N'Lifestyle', N'Unisex', N'Da tổng hợp', '1971-01-01'),
(N'Puma Cali Dream', N'Puma Cali Dream Leather White', N'Giày sneaker nữ tính, thiết kế lấy cảm hứng từ bờ biển California.', 'https://example.com/images/puma_calidream_white.jpg', 2000000.00, N'Puma', N'Sneaker', N'Nữ', N'Da tổng hợp', '2021-02-01');
GO

-- 6. Thêm Dữ liệu vào Bảng `shoe_variants`
INSERT INTO shoe_variants (shoe_id, size, color_name, color_hex_code, stock_quantity, sku, variant_image_url) VALUES
-- Nike Air Force 1 Low (id: 1)
(1, 36.0, N'Trắng', '#FFFFFF', 15, N'AF1-WHI-360', NULL),
(1, 38.0, N'Trắng', '#FFFFFF', 20, N'AF1-WHI-380', NULL),
(1, 40.0, N'Trắng', '#FFFFFF', 25, N'AF1-WHI-400', NULL),
(1, 42.0, N'Trắng', '#FFFFFF', 18, N'AF1-WHI-420', NULL),
(1, 44.0, N'Trắng', '#FFFFFF', 10, N'AF1-WHI-440', NULL),

-- Adidas Ultraboost 22 (id: 2)
(2, 37.0, N'Đen', '#000000', 12, N'UB22-BLK-370', NULL),
(2, 39.0, N'Đen', '#000000', 18, N'UB22-BLK-390', NULL),
(2, 41.0, N'Đen', '#000000', 22, N'UB22-BLK-410', NULL),
(2, 43.0, N'Đen', '#000000', 15, N'UB22-BLK-430', NULL),

-- Converse Chuck 70 (id: 3)
(3, 36.5, N'Đen', '#000000', 10, N'C70-BLK-365', NULL),
(3, 38.5, N'Đen', '#000000', 15, N'C70-BLK-385', NULL),
(3, 40.5, N'Đen', '#000000', 20, N'C70-BLK-405', NULL),
(3, 42.5, N'Đen', '#000000', 13, N'C70-BLK-425', NULL),

-- Vans Old Skool (id: 4)
(4, 37.0, N'Đen trắng', '#000000', 18, N'VOS-BW-370', NULL),
(4, 39.0, N'Đen trắng', '#000000', 25, N'VOS-BW-390', NULL),
(4, 41.0, N'Đen trắng', '#000000', 20, N'VOS-BW-410', NULL),

-- Puma Suede Classic (id: 5)
(5, 36.0, N'Đen', '#000000', 8, N'PSC-BLK-360', NULL),
(5, 38.0, N'Đen', '#000000', 12, N'PSC-BLK-380', NULL),
(5, 40.0, N'Đen', '#000000', 15, N'PSC-BLK-400', NULL),

-- New Balance 574 (id: 6)
(6, 37.0, N'Xám', '#808080', 14, N'NB574-GRY-370', NULL),
(6, 39.0, N'Xám', '#808080', 19, N'NB574-GRY-390', NULL),
(6, 41.0, N'Xám', '#808080', 16, N'NB574-GRY-410', NULL),

-- Bitis Hunter X (id: 7)
(7, 36.0, N'Trắng', '#FFFFFF', 30, N'BHX-WHI-360', NULL),
(7, 38.0, N'Trắng', '#FFFFFF', 40, N'BHX-WHI-380', NULL),
(7, 40.0, N'Trắng', '#FFFFFF', 35, N'BHX-WHI-400', NULL),
(7, 42.0, N'Trắng', '#FFFFFF', 25, N'BHX-WHI-420', NULL),

-- Ananas Urbas Corluray (id: 8)
(8, 37.0, N'Xanh lá', '#008000', 15, N'AN-UC-GRN-370', NULL),
(8, 39.0, N'Xanh lá', '#008000', 20, N'AN-UC-GRN-390', NULL),
(8, 41.0, N'Xanh lá', '#008000', 12, N'AN-UC-GRN-410', NULL),

-- Dr. Martens 1460 (id: 9)
(9, 38.0, N'Đen', '#000000', 8, N'DM1460-BLK-380', NULL),
(9, 40.0, N'Đen', '#000000', 10, N'DM1460-BLK-400', NULL),
(9, 42.0, N'Đen', '#000000', 7, N'DM1460-BLK-420', NULL),

-- Clarks Desert Boot (id: 10)
(10, 39.0, N'Nâu sáp', '#8B4513', 5, N'CLDB-BW-390', NULL),
(10, 41.0, N'Nâu sáp', '#8B4513', 8, N'CLDB-BW-410', NULL),
(10, 43.0, N'Nâu sáp', '#8B4513', 6, N'CLDB-BW-430', NULL),

-- Jordan 1 Mid (id: 11)
(11, 38.0, N'Xám khói', '#A9A9A9', 10, N'J1M-SMK-380', NULL),
(11, 40.0, N'Xám khói', '#A9A9A9', 15, N'J1M-SMK-400', NULL),
(11, 42.0, N'Xám khói', '#A9A9A9', 12, N'J1M-SMK-420', NULL),

-- Reebok Club C 85 (id: 12)
(12, 36.0, N'Trắng kem', '#F5F5DC', 18, N'RC85-VIN-360', NULL),
(12, 38.0, N'Trắng kem', '#F5F5DC', 25, N'RC85-VIN-380', NULL),
(12, 40.0, N'Trắng kem', '#F5F5DC', 20, N'RC85-VIN-400', NULL),

-- Mizuno Wave Rider 26 (id: 13)
(13, 37.0, N'Xanh dương', '#0000FF', 10, N'MWR26-BLU-370', NULL),
(13, 39.0, N'Xanh dương', '#0000FF', 14, N'MWR26-BLU-390', NULL),
(13, 41.0, N'Xanh dương', '#0000FF', 11, N'MWR26-BLU-410', NULL),

-- ASICS Gel-Kayano 29 (id: 14)
(14, 38.0, N'Xanh dương', '#0000FF', 9, N'AGK29-BLU-380', NULL),
(14, 40.0, N'Xanh dương', '#0000FF', 13, N'AGK29-BLU-400', NULL),
(14, 42.0, N'Xanh dương', '#0000FF', 10, N'AGK29-BLU-420', NULL),

-- Crocs Classic Clog (id: 15)
(15, 36.0, N'Xanh navy', '#000080', 25, N'CCC-NVY-360', NULL),
(15, 38.0, N'Xanh navy', '#000080', 30, N'CCC-NVY-380', NULL),
(15, 40.0, N'Xanh navy', '#000080', 28, N'CCC-NVY-400', NULL),

-- Gucci Ace Sneaker (id: 16)
(16, 37.0, N'Trắng', '#FFFFFF', 3, N'GUCCI-ACE-370', NULL),
(16, 39.0, N'Trắng', '#FFFFFF', 5, N'GUCCI-ACE-390', NULL),
(16, 41.0, N'Trắng', '#FFFFFF', 2, N'GUCCI-ACE-410', NULL),

-- Balenciaga Triple S (id: 17)
(17, 38.0, N'Đen', '#000000', 4, N'BAL-TS-BLK-380', NULL),
(17, 40.0, N'Đen', '#000000', 6, N'BAL-TS-BLK-400', NULL),
(17, 42.0, N'Đen', '#000000', 3, N'BAL-TS-BLK-420', NULL),

-- Fila Disruptor II (id: 18)
(18, 36.0, N'Trắng', '#FFFFFF', 20, N'FILA-D2-WHI-360', NULL),
(18, 38.0, N'Trắng', '#FFFFFF', 25, N'FILA-D2-WHI-380', NULL),
(18, 40.0, N'Trắng', '#FFFFFF', 18, N'FILA-D2-WHI-400', NULL),

-- Adidas Stan Smith (id: 19)
(19, 37.0, N'Trắng xanh lá', '#FFFFFF', 15, N'ASS-WG-370', NULL),
(19, 39.0, N'Trắng xanh lá', '#FFFFFF', 20, N'ASS-WG-390', NULL),
(19, 41.0, N'Trắng xanh lá', '#FFFFFF', 17, N'ASS-WG-410', NULL),

-- Puma Cali Dream (id: 20)
(20, 36.0, N'Trắng', '#FFFFFF', 12, N'PCD-WHI-360', NULL),
(20, 38.0, N'Trắng', '#FFFFFF', 16, N'PCD-WHI-380', NULL),
(20, 40.0, N'Trắng', '#FFFFFF', 10, N'PCD-WHI-400', NULL);
GO