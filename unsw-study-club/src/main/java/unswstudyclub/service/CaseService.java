package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Case;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CaseService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public CaseService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }

    public int addCase(Case c) {
        return unswStudyClubDao.addCase(c);
    }

    public List<Case> getAllCases() {
        return unswStudyClubDao.getAllCases();
    }

    public Optional<Case> getCaseById(UUID id) {
        return unswStudyClubDao.getCaseById(id);
    }

    public Optional<Case> getCaseByTitle(String title) {
        return unswStudyClubDao.getCaseByTitle(title);
    }
}
