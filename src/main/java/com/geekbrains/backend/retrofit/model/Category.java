package com.geekbrains.backend.retrofit.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class Category {
    private Long id;
    private List<Product> products;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", products=" + products +
                ", title='" + title + '\'' +
                '}';
    }
}