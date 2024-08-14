package com.korgun.springcourse.RestApp.controllers;

import com.korgun.springcourse.RestApp.dto.PersonDTO;
import com.korgun.springcourse.RestApp.model.Person;
import com.korgun.springcourse.RestApp.services.PeopleService;
import com.korgun.springcourse.RestApp.util.PersonErrorException;
import com.korgun.springcourse.RestApp.util.PersonNotCreatedException;
import com.korgun.springcourse.RestApp.util.PersonNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PersonDTO> people(){
        return peopleService.findAll()
                .stream()
                .map(this::convertToPersonDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public PersonDTO findOne(@PathVariable("id") int id){
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder sbErrors = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error: errors){
                sbErrors.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new PersonNotCreatedException(sbErrors.toString().trim());
        }

        peopleService.save(convertToPerson(personDTO));
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorException> handleException(PersonNotFoundException e){
        PersonErrorException personErrorException = new PersonErrorException(
                "Person with that id wasn't found",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(personErrorException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorException> handleException(PersonNotCreatedException e){
        PersonErrorException personErrorException = new PersonErrorException(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(personErrorException, HttpStatus.BAD_REQUEST);
    }

    public Person convertToPerson(PersonDTO personDTO){
        return modelMapper.map(personDTO, Person.class);
    }

    public PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
