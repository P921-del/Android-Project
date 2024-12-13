package com.example.food_order_application.Domains;

public class MenuItems {
    public int id;
    private String name;
    private String categoryImage;

    public MenuItems(int id, String name, String categoryImage) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
    }

    public MenuItems(String categoryImage, String name) {
        this.categoryImage = categoryImage;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
