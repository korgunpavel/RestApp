package com.korgun.springcourse.RestApp.repositories;

import com.korgun.springcourse.RestApp.model.Measurements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurements, Integer> {

    Optional<Measurements> findBySensorName(String sensor);
}
