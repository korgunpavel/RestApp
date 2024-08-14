package com.korgun.springcourse.RestApp.dto;

import com.korgun.springcourse.RestApp.model.Sensor;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class MeasurementsDTO {

    @Min(-100)
    @Max(100)
    @NotNull
    private double value;

    @NotNull
    private boolean raining;

    @NotNull
    private Sensor sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
