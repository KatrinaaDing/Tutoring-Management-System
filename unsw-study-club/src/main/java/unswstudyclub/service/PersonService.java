package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public PersonService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }


    public int addPerson(Person person) {
        return unswStudyClubDao.insertPerson(person);
    }
    public List<Person> getAllPeople() {
        return unswStudyClubDao.selectAllPeople();
    }

    public Optional<Person> getPersonById(UUID id) {
        return unswStudyClubDao.selectPersonById(id);
    }

    public int deletePersonById(UUID id) {
        return unswStudyClubDao.deletePersonById(id);
    }

    public int updatePersonById(UUID id, Person newPerson) {
        return unswStudyClubDao.updatePersonById(id, newPerson);
    }
}
