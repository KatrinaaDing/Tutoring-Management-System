package unswstudyclub.dao;
// DAO: Data Access Object.
// It’s a design pattern in which a data access object (DAO) is an object that provides an abstract interface

import com.fasterxml.jackson.annotation.JsonProperty;
import unswstudyclub.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {

    int insertPerson(UUID id, Person person);

    // generate a random id and insert the person with the id
    default int insertPerson(Person person) {
        UUID id = UUID.randomUUID();
        return insertPerson(id, person);
    }

    List<Person> selectAllPeople();
    Optional<Person> selectPersonById(UUID id);
    int deletePersonById(UUID id);
    int updatePersonById(UUID id, Person person);

}
