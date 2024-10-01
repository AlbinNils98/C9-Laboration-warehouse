package org.example.c9laboration2.entities;

import java.time.LocalDate;

public class Product {
    private String id;
    private String name;
    private Category category;
    private int rating;
    private final LocalDate creationDate;
    private LocalDate lastModified;

    public Product(String id, String name, Category category, int rating, LocalDate creationDate, LocalDate lastModified) {
        setId(id);
        setName(name);
        setCategory(category);
        setRating(rating);
        this.creationDate = creationDate;
        setLastModified(lastModified);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }
}

