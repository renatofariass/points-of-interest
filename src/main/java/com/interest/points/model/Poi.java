package com.interest.points.model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "points_of_interest")
public class Poi implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int x;
    private int y;
    @ManyToOne(targetEntity = Category.class)
    private Category category;

    public Poi() {
    }

    public Poi(Long id, String name, int x, int y, Category category) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
