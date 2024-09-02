package com.interest.points.service;

import com.interest.points.exceptions.ResourceNotFoundException;
import com.interest.points.mapper.ModelMapperConverter;
import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import com.interest.points.repository.CategoryRepository;
import com.interest.points.repository.PoiRepository;
import com.interest.points.utils.PoiUtils;
import com.interest.points.vo.poi.PoiVORequest;
import com.interest.points.vo.poi.PoiVOResponse;
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

    public List<PoiVOResponse> findAllPois() {
        return ModelMapperConverter.parseListObjects(poiRepository.findAll(), PoiVOResponse.class);
    }

    public List<PoiVOResponse> findNearbyPois(List<String> categories, int x, int y,
                                              int maxDistance) {

        List<Poi> poisWithinProximity = poiRepository.findPoisWithinProximity(
                x - maxDistance, y - maxDistance,  x + maxDistance, y + maxDistance);

        var poisList = poisWithinProximity.stream()
                .filter(poi -> categories == null || categories.isEmpty() ||
                        poi.getCategories().stream().anyMatch(category -> categories.contains(category.getName())))
                .filter(poi -> PoiUtils.distance(x, y, poi.getX(), poi.getY()) <= maxDistance)
                .filter(poi -> PoiUtils.isOpenAt(LocalTime.now(), poi.getOpeningHours(), poi.getClosingHours()))
                .collect(Collectors.toList());

        return ModelMapperConverter.parseListObjects(poisList, PoiVOResponse.class);
    }

    public List<PoiVOResponse> findNearbyPoisWithoutTimeFilter(List<String> categories, int x, int y,
                                                               int maxDistance) {
        List<Poi> poisWithinProximity = poiRepository.findPoisWithinProximity(
                x - maxDistance, y - maxDistance,  x + maxDistance, y + maxDistance);

        var poisList = poisWithinProximity.stream()
                .filter(poi -> PoiUtils.distance(x, y, poi.getX(), poi.getY()) <= maxDistance)
                .filter(poi -> categories == null || categories.isEmpty() ||
                        poi.getCategories().stream().anyMatch(category -> categories.contains(category.getName())))
                .collect(Collectors.toList());

        return ModelMapperConverter.parseListObjects(poisList, PoiVOResponse.class);
    }

    public PoiVOResponse createPoi(PoiVORequest poiVoRequest) {
        Set<Category> categories = poiVoRequest.getCategories().stream()
                .map(categoryName -> categoryRepository.findByName(categoryName)
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found: " + categoryName)))
                .collect(Collectors.toSet());

        Poi poi = ModelMapperConverter.parseObject(poiVoRequest, Poi.class);
        poi.setCategories(categories);

        return ModelMapperConverter.parseObject(poiRepository.save(poi), PoiVOResponse.class);
    }
}
