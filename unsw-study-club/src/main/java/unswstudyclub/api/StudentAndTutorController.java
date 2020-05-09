package unswstudyclub.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Study;
import unswstudyclub.service.StudentAndTutorService;

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

    @PostMapping(path="/student/add")
    public int addStudent(@JsonProperty("id") UUID studentId, @JsonProperty("course") UUID courseId) {
        return studentAndTutorService.addStudent(studentId, courseId);
    }

    @GetMapping
    public List<?> selectAllStudent(){
        return null;

    }

    @GetMapping(path="/student/{id}")
    public Optional<Study> selectStudentById(@PathVariable("id") UUID id){
        return studentAndTutorService.selectStudentById(id);
    }
//    Map<String, String> selectAllStudent() {
//        HashMap<String, String> map = new HashMap<>();
//
//        map.put("key", "value");
//        map.put("foo", "bar");
//        map.put("aa", "bb");
//        return map;


    @DeleteMapping(path="/student/delete")
    public int removeStudent(@JsonProperty("id") UUID studentId, @JsonProperty("course") UUID courseId) {
        return studentAndTutorService.removeStudent(studentId, courseId);
    }


}
