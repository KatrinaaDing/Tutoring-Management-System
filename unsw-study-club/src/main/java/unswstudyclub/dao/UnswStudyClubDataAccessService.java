package unswstudyclub.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import unswstudyclub.model.Course;
import unswstudyclub.model.Person;
import unswstudyclub.model.Study;

import javax.swing.text.html.Option;
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
        return jdbcTemplate.update("INSERT INTO Person VALUES (?, ?, ?, ?, ?, ?, ?, ?)" ,
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
        Person person = jdbcTemplate.queryForObject(
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
        return Optional.ofNullable(person);
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
    public Optional<Study> selectStudentById(UUID id) {
        Study study = null;
        Person person = selectPersonById(id).orElse(null);
        if (person != null){
            study = new Study(person);
            List<Course> courses = jdbcTemplate.query(
                                    "SELECT c.id, c.code, c.name, c.handbook, s.start_date " +
                                            "FROM Student s " +
                                            "JOIN Course c " +
                                            "ON s.study = c.id",
                                    (rs, i) -> {
                                        UUID courseId = UUID.fromString(rs.getString("id"));
                                        String code = rs.getString("code");
                                        String name = rs.getString("name");
                                        String handbook = rs.getString("handbook");
                                        Timestamp startDate = rs.getTimestamp("start_date");
                                        return new Course(courseId, code, name, handbook);
                                    }
            );
            study.setCourses(courses);
        }

        return Optional.ofNullable(study);
    }

    @Override
    public List<Study> selectAllStudent(){
        final String sql = "SELECT DISTINCT id FROM Student";
        return jdbcTemplate.query(sql, (resultSet, i) -> {

            Optional<Study> s =  selectStudentById(UUID.fromString(resultSet.getString("id")));
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
}
