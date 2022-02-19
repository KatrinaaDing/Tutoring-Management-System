package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Tutor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TutorService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public TutorService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }

    public int addTutor(UUID personId, UUID courseId) {
        return unswStudyClubDao.addTutor(personId, courseId);
    }

    public List<Tutor> selectAllTutor() {
        return unswStudyClubDao.selectAllTutor();
    }

    public Optional<Tutor> selectTutorById(UUID id) {
        return unswStudyClubDao.selectTutorById(id);
    }

    public int removeTutor(UUID tutorId, UUID courseId) {
        return unswStudyClubDao.removeTutor(tutorId, courseId);
    }


}
