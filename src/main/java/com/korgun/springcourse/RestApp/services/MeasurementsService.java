package com.korgun.springcourse.RestApp.services;

import com.korgun.springcourse.RestApp.model.Measurements;
import com.korgun.springcourse.RestApp.model.Sensor;
import com.korgun.springcourse.RestApp.repositories.MeasurementsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {

    private final MeasurementsRepository measurementsRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    public List<Measurements> findAll(){
        return measurementsRepository.findAll();
    }

    public Optional<Measurements> getSensorName(String name){
        return measurementsRepository.findBySensorName(name);
    }

    public Integer count(){
        return Math.toIntExact(measurementsRepository.findAll()
                .stream()
                .filter(Measurements::isRaining)
                .count());
    }

    @Transactional
    public void save(Measurements measurements){
        measurements.setCreatedAt(LocalDateTime.now());
        measurements.setSensor(sensorService.getSensorByName(measurements.getSensor().getName()).get());
        measurementsRepository.save(measurements);
    }

}
