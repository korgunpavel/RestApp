package com.korgun.springcourse.RestApp.util;

public class PersonNotCreatedException extends RuntimeException{
    public PersonNotCreatedException (String message){
        super(message);
    }
}
