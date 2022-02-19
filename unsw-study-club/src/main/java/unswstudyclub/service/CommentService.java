package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Comment;

import java.util.UUID;

@Service
public class CommentService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public CommentService(UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }


    public int addComment(Comment c) {
        return unswStudyClubDao.addComment(c);
    }

    public Comment getCommentById(UUID id) {
        return unswStudyClubDao.getCommentById(id);
    }

    public int updateCommentById(UUID id, Comment c) {
        return unswStudyClubDao.updateCommentById(id, c);
    }

    public int deleteCommentById(UUID id) {
        return unswStudyClubDao.deleteCommentById(id);
    }
}
