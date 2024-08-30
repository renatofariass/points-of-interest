package com.interest.points.repository;

import com.interest.points.model.Category;
import com.interest.points.model.Poi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoiRepository extends JpaRepository<Poi, Long> {
}
