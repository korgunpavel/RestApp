package com.korgun.springcourse.RestApp.util;

import com.korgun.springcourse.RestApp.model.Measurements;
import com.korgun.springcourse.RestApp.services.MeasurementsService;
import com.korgun.springcourse.RestApp.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeasurementsValidator implements Validator {

    private final SensorService sensorService;

    public MeasurementsValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Measurements.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurements measurements = (Measurements) target;


        if(sensorService.getSensorByName(measurements.getSensor().getName()).isEmpty()){
            errors.rejectValue("sensor","","This Sensor not registered");
        }
    }



}
