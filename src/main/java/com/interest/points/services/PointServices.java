package com.interest.points.services;

import com.interest.points.exceptions.ResourceNotFoundException;
import com.interest.points.mapper.ModelMapperConverter;
import com.interest.points.model.Category;
import com.interest.points.model.Point;
import com.interest.points.repositories.CategoryRepository;
import com.interest.points.repositories.PointRepository;
import com.interest.points.utils.PointUtils;
import com.interest.points.vos.point.PointVORequest;
import com.interest.points.vos.point.PointVOResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PointServices {
    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<PointVOResponse> findAllPoints() {
        return ModelMapperConverter.parseListObjects(pointRepository.findAll(), PointVOResponse.class);
    }

    public List<PointVOResponse> findNearbyPoints(List<String> categories, int x, int y,
                                                int maxDistance) {

        List<Point> pointsWithinProximity = pointRepository.findPointsWithinProximity(
                x - maxDistance, y - maxDistance,  x + maxDistance, y + maxDistance);

        var pointsList = pointsWithinProximity.stream()
                .filter(point -> categories == null || categories.isEmpty() ||
                        categories.stream().anyMatch(category -> categories.contains(point.getCategory().getName())))
                .filter(point -> PointUtils.distance(x, y, point.getX(), point.getY()) <= maxDistance)
                .filter(point -> PointUtils.isOpenAt(LocalTime.now(), point.getOpeningHours(), point.getClosingHours()))
                .collect(Collectors.toList());

        return ModelMapperConverter.parseListObjects(pointsList, PointVOResponse.class);
    }

    public List<PointVOResponse> findNearbyPointsWithoutTimeFilter(List<String> categories, int x, int y,
                                                                 int maxDistance) {
        List<Point> pointsWithinProximity = pointRepository.findPointsWithinProximity(
                x - maxDistance, y - maxDistance,  x + maxDistance, y + maxDistance);

        var pointsList = pointsWithinProximity.stream()
                .filter(point -> PointUtils.distance(x, y, point.getX(), point.getY()) <= maxDistance)
                .filter(point -> categories == null || categories.isEmpty() ||
                        categories.stream().anyMatch(category -> categories.contains(point.getCategory().getName())))
                .collect(Collectors.toList());

        return ModelMapperConverter.parseListObjects(pointsList, PointVOResponse.class);
    }

    public PointVOResponse createPoint(PointVORequest pointVoRequest) {
        Category category = categoryRepository.findByName(pointVoRequest.getCategory())
                        .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Point point = ModelMapperConverter.parseObject(pointVoRequest, Point.class);
        point.setCategory(category);

        return ModelMapperConverter.parseObject(pointRepository.save(point), PointVOResponse.class);
    }
}
