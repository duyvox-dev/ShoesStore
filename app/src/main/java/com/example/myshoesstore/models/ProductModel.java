package com.example.myshoesstore.models;

import java.io.Serializable;

public class ProductModel implements Serializable {
    String name;
    String description;
    String rating;
    String img_url;
    String typeId;
    String brandId;
    int price;
    int stock;

    public ProductModel() {
    }

    public ProductModel(String name, String description, String rating, String img_url, String typeId, String brandId, int price, int stock) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.img_url = img_url;
        this.typeId = typeId;
        this.brandId = brandId;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
