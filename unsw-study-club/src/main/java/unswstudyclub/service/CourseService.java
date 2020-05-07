package unswstudyclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unswstudyclub.dao.CourseDao;
import unswstudyclub.model.Course;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseDao courseDao;

    @Autowired
    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }


    public int addCourse(Course course) {
        return courseDao.insertCourse(course);
    }

    public List<Course> selectAllCourse() {
        return courseDao.selectAllCourse();
    }

    public Optional<Course> selectCourseByCode(String code) {
        return courseDao.selectCourseByCode(code);
    }

    public Optional<Course> selectCourseById(UUID id) {
        return courseDao.selectCourseById(id);
    }

    public int deleteCourseByCode(String code) {
        return courseDao.deleteCourseByCode(code);
    }

    public int updateCourseById(String code, Course newCourse) {
        return updateCourseById(code, newCourse);
    }


}
