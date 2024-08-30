package com.interest.points.controller;

import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import com.interest.points.service.PoiService;
import com.interest.points.vo.PoiVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pois")
public class PoiController {
    @Autowired
    private PoiService poiService;

    @GetMapping
    public List<Poi> getAllPois() {
        return poiService.getAllPois();
    }

    @GetMapping("/proximity")
    public List<Poi> findPoisByProximity(
            @RequestParam(name = "x") int x,
            @RequestParam(name = "y") int y,
            @RequestParam(name = "distance") int distance,
            @RequestParam(name = "categories", required = false) List<String> categories) {
        if (categories == null || categories.isEmpty()) {
            return poiService.findPoisByProximity(x, y, distance);
        } else {
            return poiService.findPoisByCategoryAndProximity(categories, x, y, distance);
        }
    }

    @PostMapping
    public Poi createPoi(@Valid @RequestBody PoiVO obj) {
        return poiService.createPoi(obj);
    }
}
