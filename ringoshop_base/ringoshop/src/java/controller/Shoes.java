// src/main/java/com/ringoshop/model/Shoe.java
package controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Shoes {
    private int id;
    private String name;
    private String title;
    private String description;
    private String mainImageUrl;
    private BigDecimal price;
    private String brand;
    private String category;
    private String gender;
    private String material;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor rỗng
    public Shoes() {}

    // Constructor đầy đủ (có thể tạo nhiều constructor tùy mục đích)
    public Shoes(int id, String name, String title, String description, String mainImageUrl, BigDecimal price,
                String brand, String category, String gender, String material, LocalDate releaseDate,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.gender = gender;
        this.material = material;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters (tôi chỉ hiển thị một vài để tránh lặp lại dài)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    // ... các getters và setters khác cho các trường còn lại ...

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getMainImageUrl() { return mainImageUrl; }
    public void setMainImageUrl(String mainImageUrl) { this.mainImageUrl = mainImageUrl; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }
    public LocalDate getReleaseDate() { return releaseDate; }
    public void setReleaseDate(LocalDate releaseDate) { this.releaseDate = releaseDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }


    @Override
    public String toString() {
        return "Shoes{" +
               "id=" + id +
               ", name='" + name + '\'' +
               ", title='" + title + '\'' +
               ", price=" + price +
               ", brand='" + brand + '\'' +
               '}';
    }
}