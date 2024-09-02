package com.interest.points.repository;

import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {
    @Query(value = "SELECT p FROM Poi p WHERE p.x >= :xMin AND p.x <= :xMax AND p.y >= :yMin AND p.y <= :yMax")
    List<Poi> findPoisWithinProximity(int xMin, int yMin, int xMax, int yMax);
}
