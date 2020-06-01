package unswstudyclub.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import unswstudyclub.model.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static unswstudyclub.security.ApplicationUserRole.STUDENT;

@Repository("postgres")
public class UnswStudyClubDataAccessService implements UnswStudyClubDao {

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UnswStudyClubDataAccessService(JdbcTemplate jdbcTemplate, PasswordEncoder passwordEncoder) {
        this.jdbcTemplate = jdbcTemplate;
        this.passwordEncoder = passwordEncoder;
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
    public int updateCourseByCode(String code, Course newCourse) {
        return jdbcTemplate.update(
                "UPDATE Course SET" +
                        "code = ?," +
                        "name = ?," +
                        "handbook = ? " +
                        "WHERE code = ?",
                newCourse.getId(),
                newCourse.getCode(),
                newCourse.getName(),
                newCourse.getHandbook(),
                code
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

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String email) {
        final String sql = "SELECT * FROM Person WHERE email = ?";
        ApplicationUser appUser = jdbcTemplate.queryForObject(
                sql,
                new Object[] {email},
                (rs, i) -> {
                    String password = rs.getString("password");
                    return new ApplicationUser(STUDENT.getGrantedAuthorities(),
                            passwordEncoder.encode(password),
                            email,
                            true,
                            true,
                            true,
                            true);
                }
        );
        return Optional.ofNullable(appUser);
    }

    @Override
    public int addCase(UUID id, Case c) {
        return jdbcTemplate.update(
                "INSERT INTO Case_Study VALUES (?, ?, ?, ?, ?)",
                id,
                c.getSubject(),
                c.getTitle(),
                c.getDescription(),
                c.getDate()
        );
    }

    @Override
    public List<Case> getAllCases() {
        return jdbcTemplate.query(
                "SELECT * FROM Case_Study",
                (rs, i) -> {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String subject = rs.getString("subject");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Timestamp date = rs.getTimestamp("start_date");

                    return new Case(id, subject, title, description, date);
                }
        );
    }

    @Override
    public Optional<Case> getCaseById(UUID id) {
        Case c = jdbcTemplate.queryForObject(
                "SELECT * FROM Case_Study WHERE id = ?",
                new Object[]{id},
                (rs, i) -> {
                    String subject = rs.getString("subject");
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Timestamp date = rs.getTimestamp("start_date");

                    return new Case(id, subject, title, description, date);
                }
        );
        return Optional.ofNullable(c);
    }

    @Override
    public Optional<Case> getCaseByTitle(String title) {
        Case c = jdbcTemplate.queryForObject(
                "SELECT * FROM Case_Study WHERE title = ?",
                new Object[]{title},
                (rs, i) -> {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String subject = rs.getString("subject");
                    String description = rs.getString("description");
                    Timestamp date = rs.getTimestamp("start_date");

                    return new Case(id, subject, title, description, date);
                }

        );
        return Optional.ofNullable(c);
    }


    @Override
    public int addSubtitle(UUID id, Subtitle subtitle) {
        return jdbcTemplate.update(
                "INSERT INTO Subtitle VALUES (?, ?, ?, ?)",
                id,
                subtitle.getCaseTitle(),
                subtitle.getPart(),
                subtitle.getContent()
        );
    }

    @Override
    public List<Subtitle> getSubtitleByCase(String caseTitle) {
        return jdbcTemplate.query(
                "SELECT * FROM Subtitle WHERE caseTitle = ?",
                new Object[]{caseTitle},
                (rs, i) -> {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String caseTtile = rs.getString("case_title");
                    String part = rs.getString("part");
                    String content = rs.getString("content");

                    Subtitle s = new Subtitle(id, caseTitle, part, content);
                    ArrayList<Comment> cs = new ArrayList<Comment>(getCommentsBySubtitle(id));
                    s.setComments(cs);
                    return s;
                }
        );
    }

    @Override
    public int updateSubtitleById(UUID id, Subtitle subtitle) {
        return jdbcTemplate.update(
                "UPDATE Subtitle SET content = ?",
                subtitle.getContent()
        );
    }

    @Override
    public int deleteSubtitleById(UUID id) {
        return jdbcTemplate.update(
                "DELETE FROM Subtitle WHERE id = ?",
                id
        );
    }

    @Override
    public int addComment(UUID id, Comment c) {
        String subtitleText = jdbcTemplate.queryForObject(
                "SELECT id FROM Subtitle WHERE content = ?",
                new Object[]{c.getSubtitle()},
                String.class
        );

        String uploader = jdbcTemplate.queryForObject(
                "SELECT first_name||' '||last_name " +
                        "FROM Person " +
                        "WHERE id = ?",
                new Object[]{c.getUploader()},
                String.class
        );

        return jdbcTemplate.update("INSERT INTO Comment VALUES (?, ?, ?, ?, ?)",
                id,
                subtitleText,
                uploader,
                c.getContent()
        );
    }

    @Override
    public Comment getCommentById(UUID id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, subtitle_text, uploader_text, content, post_date " +
                        "From Comment_View " +
                        "WHERE id = ?",
                new Object[]{id},
                (rs, i) -> {
                    String subtitle = rs.getString("subtitle");
                    String content = rs.getString("content");
                    String uploader = rs.getString("uploader_text");
                    Timestamp postDate = rs.getTimestamp("post_date");
                    return new Comment(id, subtitle, content, uploader, postDate);
                }
        );
    }

    @Override
    public int updateCommentById(UUID id, Comment c) {
        return jdbcTemplate.update(
                "UPDATE Comment SET " +
                        "content = ? " +
                        "WHERE id = ?",
                    c.getContent(),
                    id
                );
    }

    @Override
    public int deleteCommentById(UUID id) {
        return 0;
    }

    private List<Comment> getCommentsBySubtitle(UUID subtitleId) {
        return jdbcTemplate.query(
                "SELECT id, subtitle_text, uploader_text, content, post_date " +
                        "FROM Comment_View " +
                        "WHERE subtitle_id = ?",
                new Object[]{subtitleId},
                (rs, i) -> {
                    UUID id = UUID.fromString(rs.getString("id"));
                    String uploader = rs.getString("uploader");
                    String subtitleText = rs.getString("subtitle_text");
                    String content = rs.getString("content");
                    Timestamp post_date = rs.getTimestamp("post_date");

                    Comment c  =  new Comment(id, subtitleText, content, uploader, post_date);
                    c.setPostDate(post_date);
                    return c;
                }
        );
    }

}
