package unswstudyclub.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Course;
import unswstudyclub.model.Person;
import unswstudyclub.service.CourseService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v6")
@RestController
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping(path="/course")
    public int addCourse(@Valid @NonNull @RequestBody Course course) {
        return courseService.addCourse(course);
    }

    @GetMapping(path="/course")
    public List<Course> selectAllCourse() {
        return courseService.selectAllCourse();
    }

    @GetMapping(path="/course/id/{id}")
    public Course selectCourseById(@NotNull  @PathVariable("id") UUID id) {
        return courseService.selectCourseById(id)
                .orElse(null);
    }

    @GetMapping(path="/course/code/{code}")
    public Course selectCourseByCode(@NotNull @PathVariable("code") String code){
        return courseService.selectCourseByCode(code)
                .orElse(null);
    }

    @DeleteMapping(path="/course/{code}")
    public int deleteCourseByCode(@PathVariable("code") String code) {
        return courseService.deleteCourseByCode(code);
    }

    @PutMapping(path="/course/{code}")
    public int updateCourseByCode(@PathVariable("code") String code, @Valid @NonNull @RequestBody Course newCourse) {
        return courseService.updateCourseByCode(code, newCourse);
    }


}
