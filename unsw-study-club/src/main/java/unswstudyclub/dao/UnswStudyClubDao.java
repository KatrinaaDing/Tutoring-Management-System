package unswstudyclub.dao;
// DAO: Data Access Object.
// It’s a design pattern in which a data access object (DAO) is an object that provides an abstract interface

import unswstudyclub.model.*;

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
    public List<Student> selectAllStudent();
    Optional<Student> selectStudentById(UUID id);

    // Tutor
    int addTutor(UUID personId, UUID courseId);
    int removeTutor(UUID personId, UUID courseId);
    public List<Tutor> selectAllTutor();
    Optional<Tutor> selectTutorById(UUID id);


    // Admin

    int addAdmin(UUID id, Admin admin);

    default int addAdmin(Admin admin) {
        UUID id = UUID.randomUUID();
        return addAdmin(id, admin);
    }

    int deleteAdminById(UUID id);
    List<Admin> selectAllAdmin();
    Optional<Admin> selectAdminById(UUID id);
    int updateAdminById(UUID id, Admin admin);

    // Video

    int uploadVideo(UUID id, Video video);

    default int uploadVideo(Video video) {
        UUID id = UUID.randomUUID();
        return uploadVideo(id, video);
    }

    List<Video> selectVideoByCourseCode(String code);
    int deleteVideoById(UUID id);
    int updateVideoById(UUID id, Video newVideo);

    // auth

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);

    // Case

    int addCase(UUID id, Case c);

    default int addCase(Case c) {
        UUID id = UUID.randomUUID();
        return addCase(id, c);
    }

    List<Case> getAllCases();
    Optional<Case> getCaseById(UUID id);
    Optional<Case> getCaseByTitle(String title);
}
