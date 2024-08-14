package com.korgun.springcourse.RestApp.repositories;

import com.korgun.springcourse.RestApp.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

}
