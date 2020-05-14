package unswstudyclub.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Study;
import unswstudyclub.service.StudentAndTutorService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestMapping("api/v1")
@RestController
public class StudentAndTutorController {

    private final StudentAndTutorService studentAndTutorService;

    @Autowired
    public StudentAndTutorController(StudentAndTutorService studentAndTutorService) {
        this.studentAndTutorService = studentAndTutorService;
    }

    @PostMapping(path="/student")
    public int addStudent(@NotNull @RequestParam(name = "id") UUID studentId,
                          @NotNull @RequestParam(name = "course") UUID courseId) {
        return studentAndTutorService.addStudent(studentId, courseId);
    }

    @GetMapping(path="/student")
    public List<Study> selectAllStudent(){
        return studentAndTutorService.selectAllStudent();
    }

    @GetMapping(path="/student/{id}")
    public Optional<Study> selectStudentById(@PathVariable("id") UUID id){
        return studentAndTutorService.selectStudentById(id);
    }


    @DeleteMapping(path="/student")
    public int removeStudent(@NotNull @RequestParam(name = "id") UUID studentId,
                             @NotNull @RequestParam(name = "course") UUID courseId) {
        return studentAndTutorService.removeStudent(studentId, courseId);
    }


}
