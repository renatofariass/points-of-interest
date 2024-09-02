package com.interest.points.repositories;

import com.interest.points.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {
    @Query(value = "SELECT p FROM Point p WHERE p.x >= :xMin AND p.x <= :xMax AND p.y >= :yMin AND p.y <= :yMax")
    List<Point> findPointsWithinProximity(int xMin, int yMin, int xMax, int yMax);
}
