package com.interest.points.controller;

import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import com.interest.points.service.PoiService;
import com.interest.points.vo.PoiVO;
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
            @RequestParam(name = "distance") int distance) {
        return poiService.findPoisByProximity(x, y, distance);
    }

    @PostMapping
    public Poi createPoi(@RequestBody PoiVO obj) {
        return poiService.createPoi(obj);
    }
}
