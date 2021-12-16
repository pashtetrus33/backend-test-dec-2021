package com.geekbrains.backend.retrofit.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Product {

    private Long id;
    private Integer price;
    private String title;
    private String categoryTitle;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", categoryTitle='" + categoryTitle + '\'' +
                '}';
    }
}