package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Video;

import java.util.List;
import java.util.UUID;

@Service
public class VideoService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public VideoService(@Qualifier("postgres") UnswStudyClubDao unswStudyClubDao) {
        this.unswStudyClubDao = unswStudyClubDao;
    }

    public int uploadVideo(Video video){
        return unswStudyClubDao.uploadVideo(video);
    }

    public List<Video> selectVideoByCourseCode(String code) {
        return unswStudyClubDao.selectVideoByCourseCode(code);
    }

    public int deleteVideoById(UUID id) {
        return unswStudyClubDao.deleteVideoById(id);
    }

    public int updateVideoById(UUID id, Video newVideo) {
        return unswStudyClubDao.updateVideoById(id, newVideo);
    }
}
