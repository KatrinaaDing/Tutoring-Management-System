package unswstudyclub.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import unswstudyclub.model.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class UnswStudyClubDataAccessService implements UnswStudyClubDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UnswStudyClubDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        // exp and join_date will be automatically initialized
        return jdbcTemplate.update("INSERT INTO Person VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                id,
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPassword(),
                person.getNickName(),
                person.getProfileImage(),
                person.getGender()
        );
    }

    @Override
    public List<Person> selectAllPeople() {
        final String query = "SELECT * FROM Person";
        // the query returns a list of Person object (1 row => 1 Person)
        return jdbcTemplate.query(query, (resultSet, i) -> {
            // get the value from column, turn the id string to UUID type
            UUID id = UUID.fromString(resultSet.getString("id"));
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            String nickName = resultSet.getString("nick_name");
            String profileImage = resultSet.getString("profile_image");
            String gender = resultSet.getString("gender");
            int exp = resultSet.getInt("exp");
            Timestamp joinDate = resultSet.getTimestamp("join_date");
            return new Person(id, firstName, lastName, email, password, nickName, profileImage, gender.charAt(0) , joinDate);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT * FROM Person WHERE id = ?";
        Person people = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    // get the value from column, turn the id string to UUID type
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");
                    String nickName = resultSet.getString("nick_name");
                    String profileImage = resultSet.getString("profile_image");
                    String gender = resultSet.getString("gender");
                    int exp = resultSet.getInt("exp");
                    Timestamp joinDate = resultSet.getTimestamp("join_date");
                    return new Person(id, firstName, lastName, email, password, nickName, profileImage, gender.charAt(0), joinDate);
                });

        // return null if the person not found
        return Optional.ofNullable(people);
    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "DELETE FROM Person WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return jdbcTemplate.update(
                "UPDATE Person SET " +
                        "first_name = ?," +
                        "last_name = ?," +
                        "email = ?," +
                        "password = ?," +
                        "nick_name = ?," +
                        "profile_image = ?," +
                        "gender = ?," +
                        "exp = ?" +
                        "WHERE id = ?",
                person.getFirstName(),
                person.getLastName(),
                person.getEmail(),
                person.getPassword(),
                person.getNickName(),
                person.getProfileImage(),
                person.getGender(),
                person.getExp(),
                id
        );
    }


    @Override
    public int insertCourse(UUID id, Course course) {
        return jdbcTemplate.update("INSERT INTO Course VALUES (?, ?, ?, ?)",
                id,
                course.getCode(),
                course.getName(),
                course.getHandbook()
        );
    }


    @Override
    public List<Course> selectAllCourse() {
        final String sql = "SELECT * FROM Course";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            UUID id = UUID.fromString(resultSet.getString("id"));
            String code = resultSet.getString("code");
            String name = resultSet.getString("name");
            String handbook = resultSet.getString("handbook");
            return new Course(id, code, name, handbook);
        });
    }

    @Override
    public Optional<Course> selectCourseByCode(String code) {
        final String sql = "SELECT * FROM Course WHERE code = ?";
        Course course = jdbcTemplate.queryForObject(
                sql,
                new Object[]{code},
                (resultSet, i) -> {
                    UUID id = UUID.fromString(resultSet.getString("id"));
                    String name = resultSet.getString("name");
                    String handbook = resultSet.getString("handbook");
                    return new Course(id, code, name, handbook);
                }
        );
        return Optional.ofNullable(course);
    }

    @Override
    public Optional<Course> selectCourseById(UUID id) {
        final String sql = "SELECT * FROM Course WHERE code = ?";
        Course course = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) -> {
                    String code = resultSet.getString("code");
                    String name = resultSet.getString("name");
                    String handbook = resultSet.getString("handbook");
                    return new Course(id, code, name, handbook);
                }
        );
        return Optional.ofNullable(course);
    }

    @Override
    public int deleteCourseByCode(String code) {
        final String sql = "DELETE FROM Course WHERE code = ?";
        return jdbcTemplate.update(sql, code);
    }

    @Override
    public int updateCourseById(String code, Course newCourse) {
        return jdbcTemplate.update(
                "UPDATE Course SET" +
                        "id = ?," +
                        "code = ?," +
                        "name = ?," +
                        "handbook = ?",
                newCourse.getId(),
                newCourse.getCode(),
                newCourse.getName(),
                newCourse.getHandbook()
        );
    }

    @Override
    public int addStudent(UUID personId, UUID courseId) {
        return jdbcTemplate.update(
                "INSERT INTO Student VALUES (?, ?)",
                personId,
                courseId
        );
    }

    @Override
    public Optional<Student> selectStudentById(UUID id) {
        Student student = null;
        Person person = selectPersonById(id).orElse(null);
        if (person != null){
            student = new Student(person);
            List<Course> courses = jdbcTemplate.query(
                                    "SELECT c.id, c.code, c.name, c.handbook " +
                                            "FROM Student s " +
                                            "JOIN Course c " +
                                            "ON s.study = c.id " +
                                            "WHERE s.id = '" + id + "'",
                                    (rs, i) -> {
                                        UUID courseId = UUID.fromString(rs.getString("id"));
                                        String code = rs.getString("code");
                                        String name = rs.getString("name");
                                        String handbook = rs.getString("handbook");
                                        return new Course(courseId, code, name, handbook);
                                    }
            );
            student.setCourses(courses);
        }

        return Optional.ofNullable(student);
    }

    @Override
    public List<Student> selectAllStudent(){
        final String sql = "SELECT DISTINCT id FROM Student";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            Optional<Student> s = selectStudentById(UUID.fromString(resultSet.getString("id")));
            return s.get();
        });
    }

    @Override
    public int removeStudent(UUID personId, UUID courseId) {
        return jdbcTemplate.update(
                "DELETE FROM Student WHERE id = ? and study = ?",
                personId,
                courseId
        );
    }

    @Override
    public int addTutor(UUID personId, UUID courseId) {
        return jdbcTemplate.update(
                "INSERT INTO Tutor VALUES (?, ?)",
                personId,
                courseId
        );
    }

    @Override
    public int removeTutor(UUID personId, UUID courseId) {
        return jdbcTemplate.update(
                "DELETE FROM Tutor WHERE id = ? and study = ?",
                personId,
                courseId
        );
    }

    @Override
    public List<Tutor> selectAllTutor() {
        final String sql = "SELECT DISTINCT id FROM Tutor";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            Optional<Tutor> t = selectTutorById(UUID.fromString(resultSet.getString("id")));
            return t.get();
        });
    }

    @Override
    public Optional<Tutor> selectTutorById(UUID id) {
        Tutor tutor = null;
        Person person = selectPersonById(id).orElse(null);
        if (person != null){
            tutor = new Tutor(person);
            List<Course> courses = jdbcTemplate.query(
                    "SELECT c.id, c.code, c.name, c.handbook " +
                            "FROM Tutor t " +
                            "JOIN Course c " +
                            "ON t.teach = c.id " +
                            "WHERE t.id = '" + id + "'",
                    (rs, i) -> {
                        UUID courseId = UUID.fromString(rs.getString("id"));
                        String code = rs.getString("code");
                        String name = rs.getString("name");
                        String handbook = rs.getString("handbook");
                        return new Course(courseId, code, name, handbook);
                    }
            );
            tutor.setCourses(courses);
        }
        return Optional.ofNullable(tutor);
    }

    @Override
    public int addAdmin(UUID id, Admin admin) {
        return jdbcTemplate.update("INSERT INTO Admin VALUES (?, ?, ?)",
                                    id,
                                    admin.getEmail(),
                                    admin.getPassword()
        );
    }

    @Override
    public int deleteAdminById(UUID id) {
        return jdbcTemplate.update("DELETE FROM Admin WHERE id = ?", id);
    }

    @Override
    public List<Admin> selectAllAdmin() {
        final String query = "SELECT * FROM Admin";
        return jdbcTemplate.query(query, (rs, i) -> {
           UUID id = UUID.fromString(rs.getString("id"));
           String email = rs.getString("email");
           String password = rs.getString("password");
           return new Admin(id, email, password);
        });
    }

    @Override
    public Optional<Admin> selectAdminById(UUID id) {
        final String sql = "SELECT * FROM Admin WHERE id = ?";
        Admin admins = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, i) -> {
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    return new Admin(id, email, password);
                }
        );
        return Optional.ofNullable(admins);
    }

    @Override
    public int updateAdminById(UUID id, Admin admin) {
        return jdbcTemplate.update(
                "UPDATE Admin SET " +
                        "email = ?, " +
                        "password = ? " +
                        "WHERE id = ?",
                admin.getEmail(),
                admin.getPassword(),
                id
        );
    }

    @Override
    public int uploadVideo(UUID id, Video video) {
        return jdbcTemplate.update("INSERT INTO Video VALUES (?, ?, ?, ?, ?, ?)",
                id,
                video.getLink(),
                video.getTitle(),
                video.getDescription(),
                video.getCourse(),
                video.getUploader()
        );
    }


    @Override
    public List<Video> selectVideoByCourseCode(String code) {
        final String sql = "SELECT * FROM Video WHERE course = '" + code + "'";
        return jdbcTemplate.query(sql, (rs, i) -> {
            UUID id = UUID.fromString(rs.getString("id"));
            String link = rs.getString("link");
            String title = rs.getString("title");
            String description = rs.getString("description");
            String course = rs.getString("course");
            UUID uploader = UUID.fromString(rs.getString("uploader"));
            Timestamp uploadDate = rs.getTimestamp("upload_date");
            return new Video(id, link, title, description, course, uploader, uploadDate);
        });
    }

    @Override
    public int deleteVideoById(UUID id) {
        return jdbcTemplate.update("DELETE FROM Video WHERE id = ?", id);
    }

    @Override
    public int updateVideoById(UUID id, Video newVideo) {
        return jdbcTemplate.update(
                "UPDATE Video SET " +
                        "link = ?, " +
                        "title = ?, " +
                        "description = ?," +
                        "course = ? " +
                        "WHERE id = ? ",
                newVideo.getLink(),
                newVideo.getTitle(),
                newVideo.getDescription(),
                newVideo.getCourse(),
                id
        );
    }
}
