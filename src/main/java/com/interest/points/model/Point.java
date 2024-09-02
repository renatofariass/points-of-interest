package com.interest.points.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

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
    private String openingHours;
    private String closingHours;
    @ManyToMany
    @JoinTable(
            name = "poi_category",
            joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();
    ;

    public Poi() {
    }

    public Poi(Long id, String name, int x, int y, String openingHours, String closingHours, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.categories = categories;
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

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        this.closingHours = closingHours;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
