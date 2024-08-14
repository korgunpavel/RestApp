package com.korgun.springcourse.RestApp.util;

public class SensorNotCreatedException extends RuntimeException {
    public SensorNotCreatedException(String message) {
        super(message);
    }
}
