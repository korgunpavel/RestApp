package com.korgun.springcourse.RestApp.controllers;

import com.korgun.springcourse.RestApp.dto.SensorDTO;
import com.korgun.springcourse.RestApp.model.Sensor;
import com.korgun.springcourse.RestApp.services.SensorService;
import com.korgun.springcourse.RestApp.util.SensorErrorResponse;
import com.korgun.springcourse.RestApp.util.SensorNotCreatedException;
import com.korgun.springcourse.RestApp.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final ModelMapper modelMapper;
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorController(ModelMapper modelMapper, SensorService sensorService, SensorValidator sensorValidator) {
        this.modelMapper = modelMapper;
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult){
        sensorValidator.validate(sensorDTO,bindingResult);

        if(bindingResult.hasErrors()){
            StringBuilder sbErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                sbErrors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new SensorNotCreatedException(sbErrors.toString().trim());
        }

        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.BAD_REQUEST);
    }

    public Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    public SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
