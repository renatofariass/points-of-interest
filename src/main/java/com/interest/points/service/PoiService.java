package com.interest.points.service;

import com.interest.points.exceptions.ResourceNotFoundException;
import com.interest.points.mapper.ModelMapperConverter;
import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import com.interest.points.repository.CategoryRepository;
import com.interest.points.repository.PoiRepository;
import com.interest.points.vo.PoiVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PoiService {
    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Poi> getAllPois() {
        return poiRepository.findAll();
    }

    public List<Poi> findPoisByProximity(int refX, int refY, int maxDistance) {
        return poiRepository.findAll().stream()
                .filter(poi -> distance(refX, refY, poi.getX(), poi.getY()) <= maxDistance)
                .collect(Collectors.toList());
    }

    public List<Poi> findPoisByCategoryAndProximity(List<String> categories, int refX, int refY, int maxDistance) {
        return poiRepository.findAll().stream()
                .filter(poi -> poi.getCategories().stream()
                        .anyMatch(category -> categories.contains(category.getName())))
                .filter(poi -> distance(refX, refY, poi.getX(), poi.getY()) <= maxDistance)
                .collect(Collectors.toList());
    }

    public Poi createPoi(PoiVO poiVO) {

        Set<Category> categories = poiVO.getCategories().stream()
                .map(categoryName -> categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName)))
                .collect(Collectors.toSet());

        Poi poi = ModelMapperConverter.parseObject(poiVO, Poi.class);
        poi.setCategories(categories);

        return poiRepository.save(poi);
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
