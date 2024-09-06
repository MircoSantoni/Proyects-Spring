package com.mirco.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mirco.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.mirco.curso.springboot.jpa.springboot_jpa.dto.PersonDto;


public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("Select p from Person p where p.name between 'j' and 'p' ")
    List<Person> findAllBetweenNames();

    @Query("Select p from Person as p where id between 2 and 5")
    List<Person> findAllBetweenId();

    @Query("Select name from Person as p where p.id = ?1")
    String getNameById(Long id);

    @Query("Select id from Person as p where p.id = ?1")
    Long getById(Long id);

    @Query("Select upper(p.name || ' ' || p.lastname) from Person as p")
    List<String> findAllFullNameConcatUpper();

    @Query("Select lower(concat(p.name , ' ' , p.lastname)) from Person as p")
    List<String> findAllFullNameConcatLower();

//    @Query("Select concat(p.name , ' ' , p.lastname) from Person as p")
    @Query("Select p.name || ' ' || p.lastname from Person as p")
    List<String> findAllFullNameConcat();

    @Query("Select concat(p.name , ' ' , p.lastname) from Person as p where p.id = ?1")
    String getFullNameById(Long id);

    @Query("select p from Person p where p.id=?1")
    Optional<Person> findOne(Long id);
    
    @Query("select p from Person p where p.name=?1")
    Optional<Person> findOneName(String name);

    @Query("select p from Person p where p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);

    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("select p from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Person> buscarByProgrammingLanguage(String programmingLanguage, String name);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonData();

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id =?1")
    Object obtenerPersonDataFullById(Long id);

    @Query("select p.name, p.programmingLanguage from Person p where p.name=?1")
    List<Object[]> obtenerPersonData(String name);
    
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1 and p.name=?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);
    
    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=?1")
    List<Object[]> obtenerPersonDataByProgrammingLanguage(String programmingLanguage);

    @Query("select p, p.programmingLanguage from Person p ")
    List<Object[]> findAllMixPerson();

    @Query("select new Person(p.name, p.lastname) from Person as p ")
    List<Person> findAllObjectPersonPersonalized();

    @Query("select new com.mirco.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) from Person as p ")
    List<PersonDto> findAllPersonDto();

    @Query("select p.name from Person as p")
    List<String> findAllNames();

    @Query("select distinct (p.name) from Person as p")
    List<String> findAllNamesDistinct();

}
