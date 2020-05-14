package unswstudyclub.dao;
// DAO: Data Access Object.
// It’s a design pattern in which a data access object (DAO) is an object that provides an abstract interface

import com.fasterxml.jackson.annotation.JsonProperty;
import unswstudyclub.model.Course;
import unswstudyclub.model.Person;
import unswstudyclub.model.Study;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UnswStudyClubDao {

    // Person

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

    // Course

    int insertCourse(UUID id, Course course);

    // generate a random id and insert the person with the id
    default int insertCourse(Course course) {
        UUID id = UUID.randomUUID();
        return insertCourse(id, course);
    }

    List<Course> selectAllCourse();
    Optional<Course> selectCourseByCode(String code);
    Optional<Course> selectCourseById(UUID id);
    int deleteCourseByCode(String code);
    int updateCourseById(String code,Course newCourse);

    // Student

    int addStudent(UUID personId, UUID courseId);
    int removeStudent(UUID personId, UUID courseId);

    public List<Study> selectAllStudent();
    Optional<Study> selectStudentById(UUID id);
}
