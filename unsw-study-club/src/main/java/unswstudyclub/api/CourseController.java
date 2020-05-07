package unswstudyclub.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Course;
import unswstudyclub.model.Person;
import unswstudyclub.service.CourseService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/course")
@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    public int addCourse(@Valid @NonNull @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping
    public List<Course> selectAllCourse() {
        return courseService.selectAllCourse();
    }

    @GetMapping(path="/id/{id}")
    public Course selectCourseById(@PathVariable("id") UUID id) {
        return courseService.selectCourseById(id)
                .orElse(null);
    }

    @GetMapping(path="/code/{code}")
    public Course selectCourseByCode(@PathVariable("code") String code){
        return courseService.selectCourseByCode(code)
                .orElse(null);
    }

    @DeleteMapping(path="/{code}")
    public int deleteCourseByCode(@PathVariable("code") String code) {
        return courseService.deleteCourseByCode(code);
    }

    @PutMapping(path="/{code}")
    public int updateCourseByCode(@PathVariable("code") String code, @Valid @NonNull @RequestBody Course newCourse) {
        return courseService.updateCourseById(code, newCourse);
    }


}
