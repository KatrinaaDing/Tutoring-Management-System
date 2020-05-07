package unswstudyclub.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import unswstudyclub.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class CourseDataAccessService implements CourseDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CourseDataAccessService(JdbcTemplate jdbctemplate) {
        this.jdbcTemplate = jdbctemplate;
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
}
