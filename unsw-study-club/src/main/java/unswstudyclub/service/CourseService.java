package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.UnswStudyClubDao;
import unswstudyclub.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final UnswStudyClubDao unswStudyClubDao;

    @Autowired
    public CourseService(@Qualifier("postgres") UnswStudyClubDao UnswStudyClubDao) {
        this.unswStudyClubDao = UnswStudyClubDao;
    }


    public int addCourse(Course course) {
        return unswStudyClubDao.insertCourse(course);
    }

    public List<Course> selectAllCourse() {
        return unswStudyClubDao.selectAllCourse();
    }

    public Optional<Course> selectCourseByCode(String code) {
        return unswStudyClubDao.selectCourseByCode(code);
    }

    public Optional<Course> selectCourseById(UUID id) {
        return unswStudyClubDao.selectCourseById(id);
    }

    public int deleteCourseByCode(String code) {
        return unswStudyClubDao.deleteCourseByCode(code);
    }

    public int updateCourseById(String code, Course newCourse) {
        return updateCourseById(code, newCourse);
    }


}
