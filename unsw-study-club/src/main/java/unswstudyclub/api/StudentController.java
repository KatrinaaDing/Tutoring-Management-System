package unswstudyclub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Student;
import unswstudyclub.service.StudentService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("api")
@RestController
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping(path="/student")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public int addStudent(@NotNull @RequestParam(name = "id") UUID studentId,
                          @NotNull @RequestParam(name = "course") UUID courseId) {
        return studentService.addStudent(studentId, courseId);
    }

    @GetMapping(path="/student")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public List<Student> selectAllStudent(){
        return studentService.selectAllStudent();
    }

    @GetMapping(path="/student/{id}")
    public Optional<Student> selectStudentById(@NotNull @PathVariable("id") UUID id){
        return studentService.selectStudentById(id);
    }


    @DeleteMapping(path="/student")
    @PreAuthorize("hasAuthority('student:write')")
    public int removeStudent(@NotNull @RequestParam(name = "id") UUID studentId,
                             @NotNull @RequestParam(name = "course") UUID courseId) {
        return studentService.removeStudent(studentId, courseId);
    }


}
