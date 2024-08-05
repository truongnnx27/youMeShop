package com.poly.DTO;


public class CategoryStatsDTO {
    private String name;
    private int productCount;

    // Constructors, getters, and setters
    public CategoryStatsDTO() {}

    public CategoryStatsDTO(String name, int productCount) {
        this.name = name;
        this.productCount = productCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }
}

