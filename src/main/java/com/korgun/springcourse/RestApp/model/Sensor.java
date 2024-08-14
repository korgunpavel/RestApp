package com.korgun.springcourse.RestApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

@Entity
@Table(name = "Sensor")
public class Sensor implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Sensor name should be not empty")
    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters")
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotEmpty(message = "Sensor name should be not empty") @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Sensor name should be not empty") @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters") String name) {
        this.name = name;
    }
}
