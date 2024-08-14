package com.korgun.springcourse.RestApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters")
    private String name;

    public @NotEmpty(message = "Sensor name should be not empty") @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Sensor name should be not empty") @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters") String name) {
        this.name = name;
    }
}
