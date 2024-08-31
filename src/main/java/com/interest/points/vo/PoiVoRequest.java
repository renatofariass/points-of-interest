package com.interest.points.vo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PoiVoRequest {
    @NotNull
    private String name;
    @NotNull
    private int x;
    @NotNull
    private int y;
    private String openingHours;
    private String closingHours;
    @NotNull
    private int influenceRadius;
    @NotEmpty
    private List<String> categories = new ArrayList<>();;

    public PoiVoRequest() {
    }

    public PoiVoRequest(String name, int x, int y, String openingHours, String closingHours, int influenceRadius,
                        List<String> categories) {
        this.name = name;
        this.x = x;
        this.y = y;
        setOpeningHours(openingHours);
        setClosingHours(closingHours);
        this.influenceRadius = influenceRadius;
        this.categories = categories;
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
        if (openingHours == null || openingHours.isBlank()) {
            this.openingHours = "ALL DAY";
        } else {
            this.openingHours = openingHours;
        }
    }

    public String getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(String closingHours) {
        if (closingHours == null || closingHours.isBlank()) {
            this.closingHours = "NOT APPLICABLE";
        } else {
            this.closingHours = closingHours;
        }
    }

    @NotNull
    public int getInfluenceRadius() {
        return influenceRadius;
    }

    public void setInfluenceRadius(@NotNull int influenceRadius) {
        this.influenceRadius = influenceRadius;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}

