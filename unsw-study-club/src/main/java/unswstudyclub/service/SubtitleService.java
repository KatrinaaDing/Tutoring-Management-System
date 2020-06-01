package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Subtitle;

import java.util.List;
import java.util.UUID;

@Service
public class SubtitleService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public SubtitleService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }


    public int addSubtitle(Subtitle subtitle) {
        return unswStudyClubDao.addSubtitle(subtitle);
    }

    public List<Subtitle> getSubtitleByCase(String caseTitle) {
        return unswStudyClubDao.getSubtitleByCase(caseTitle);
    }

    public int updateSubtitleById(UUID id, Subtitle subtitle) {
        return unswStudyClubDao.updateSubtitleById(id, subtitle);
    }

    public int deleteSubtitleById(UUID id) {
        return unswStudyClubDao.deleteSubtitleById(id);
    }
}
