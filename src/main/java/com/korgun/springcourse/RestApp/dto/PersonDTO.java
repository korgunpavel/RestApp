package com.korgun.springcourse.RestApp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class PersonDTO {

    @NotEmpty(message = "Name should be not empty")
    private String name;

    @Min(value = 0, message = "Age greater than 0")
    private int age;

    @Email
    @NotEmpty(message = "Email should be not empty")
    private String email;

    public @NotEmpty(message = "Name should be not empty") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Name should be not empty") String name) {
        this.name = name;
    }

    @Min(value = 0, message = "Age greater than 0")
    public int getAge() {
        return age;
    }

    public void setAge(@Min(value = 0, message = "Age greater than 0") int age) {
        this.age = age;
    }

    public @Email @NotEmpty(message = "Email should be not empty") String getEmail() {
        return email;
    }

    public void setEmail(@Email @NotEmpty(message = "Email should be not empty") String email) {
        this.email = email;
    }
}
