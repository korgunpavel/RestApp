package com.korgun.springcourse.RestApp.controllers;

import com.korgun.springcourse.RestApp.dto.MeasurementsDTO;
import com.korgun.springcourse.RestApp.model.Measurements;
import com.korgun.springcourse.RestApp.services.MeasurementsService;
import com.korgun.springcourse.RestApp.util.*;
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
@RequestMapping("/measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private final MeasurementsService measurementsService;
    private final MeasurementsValidator measurementsValidator;

    @Autowired
    public MeasurementsController(ModelMapper modelMapper, MeasurementsService measurementsService, MeasurementsValidator measurementsValidator) {
        this.modelMapper = modelMapper;
        this.measurementsService = measurementsService;
        this.measurementsValidator = measurementsValidator;
    }

    @GetMapping
    public List<MeasurementsDTO> measurements(){
        return measurementsService.findAll()
                .stream()
                .map(this::convertToMeasurementsDTO)
                .toList();
    }

    @GetMapping("/rainyDaysCount")
    public Integer rainyDaysCount(){
        return measurementsService.count();
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurements(@RequestBody @Valid MeasurementsDTO measurementsDTO,
                                                      BindingResult bindingResult){
        Measurements measurements = convertToMeasurements(measurementsDTO);

        measurementsValidator.validate(measurements, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder sbErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                sbErrors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new SensorNotFoundException(sbErrors.toString().trim());
        }

        measurementsService.save(measurements);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse sensorErrorResponse = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(sensorErrorResponse, HttpStatus.NOT_FOUND);
    }


    public Measurements convertToMeasurements(MeasurementsDTO measurementsDTO){
        return modelMapper.map(measurementsDTO, Measurements.class);
    }

    public MeasurementsDTO convertToMeasurementsDTO(Measurements measurements){
        return modelMapper.map(measurements, MeasurementsDTO.class);
    }
}
