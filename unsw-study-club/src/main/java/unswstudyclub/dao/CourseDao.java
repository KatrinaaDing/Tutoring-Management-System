package unswstudyclub.dao;

import unswstudyclub.model.Course;
import unswstudyclub.model.Person;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseDao {

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

}
