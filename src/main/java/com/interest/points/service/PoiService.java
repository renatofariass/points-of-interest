package com.interest.points.service;

import com.interest.points.exceptions.ResourceNotFoundException;
import com.interest.points.mapper.ModelMapperConverter;
import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import com.interest.points.repository.CategoryRepository;
import com.interest.points.repository.PoiRepository;
import com.interest.points.utils.PoiUtils;
import com.interest.points.vo.PoiVoRequest;
import com.interest.points.vo.PoiVoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PoiService {
    @Autowired
    private PoiRepository poiRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<PoiVoResponse> findAllPois() {
        return ModelMapperConverter.parseListObjects(poiRepository.findAll(), PoiVoResponse.class);
    }

    public List<PoiVoResponse> findPoisByProximity(List<String> categories, int refX, int refY,
                                                              int maxDistance) {
        var poisList = poiRepository.findAll().stream()
                .filter(poi -> categories.isEmpty() ||
                        poi.getCategories().stream().anyMatch(category -> categories.contains(category.getName())))
                .filter(poi -> PoiUtils.isWithinProximity(refX, refY, poi))
                .filter(poi -> PoiUtils.isOpenAt(LocalTime.now(), poi.getOpeningHours(), poi.getClosingHours()))
                .collect(Collectors.toList());

        return ModelMapperConverter.parseListObjects(poisList, PoiVoResponse.class);
    }

    public List<PoiVoResponse> findPoisByProximityWithoutTimeFilter(List<String> categories, int refX, int refY, int maxDistance) {
        var poisList = poiRepository.findAll().stream()
                .filter(poi -> PoiUtils.isWithinProximity(refX, refY, poi))
                .filter(poi -> categories.isEmpty() ||
                        poi.getCategories().stream().anyMatch(category -> categories.contains(category.getName())))
                .collect(Collectors.toList());

        return ModelMapperConverter.parseListObjects(poisList, PoiVoResponse.class);
    }

    public PoiVoResponse createPoi(PoiVoRequest poiVoRequest) {
        Set<Category> categories = poiVoRequest.getCategories().stream()
                .map(categoryName -> categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName)))
                .collect(Collectors.toSet());

        Poi poi = ModelMapperConverter.parseObject(poiVoRequest, Poi.class);
        poi.setCategories(categories);

        return ModelMapperConverter.parseObject(poiRepository.save(poi), PoiVoResponse.class);
    }
}
