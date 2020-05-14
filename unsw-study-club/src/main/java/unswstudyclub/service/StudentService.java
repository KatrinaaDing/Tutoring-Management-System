package unswstudyclub.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public StudentService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }

    public int addStudent(UUID personId, UUID courseId) {
        return unswStudyClubDao.addStudent(personId, courseId);
    }

    public List<Student> selectAllStudent(){
        return unswStudyClubDao.selectAllStudent();
    }

    public int removeStudent(UUID personId, UUID courseId) {
        return unswStudyClubDao.removeStudent(personId, courseId);
    }

    public Optional<Student> selectStudentById(UUID id) {
        return unswStudyClubDao.selectStudentById(id);
    }
}
