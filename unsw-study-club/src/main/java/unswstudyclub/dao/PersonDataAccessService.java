package unswstudyclub.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import unswstudyclub.model.Person;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgres")
public class PersonDataAccessService  implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
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
            return new Person(id, firstName, lastName, email, password, nickName, profileImage, gender.charAt(0) , exp, joinDate);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT * FROM person WHERE id = ?";
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
                    return new Person(id, firstName, lastName, email, password, nickName, profileImage, gender.charAt(0) , exp, joinDate);
                });

        // return null if the person not found
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        return 0;
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        return 0;
    }
}
