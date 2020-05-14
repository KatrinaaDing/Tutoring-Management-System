package unswstudyclub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Tutor;
import unswstudyclub.model.Tutor;
import unswstudyclub.service.TutorService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("api/v1")
@RestController
public class TutorController {

    private final TutorService tutorService;

    @Autowired
    public TutorController(TutorService tutorService) {
        this.tutorService = tutorService;
    }

    @PostMapping(path="/tutor")
    public int addTutor(@RequestParam("id") UUID personId,
                        @RequestParam("course") UUID courseId) {
        return tutorService.addTutor(personId, courseId);
    }

    @GetMapping(path="/tutor")
    public List<Tutor> selectAllTutor(){
        return tutorService.selectAllTutor();
    }

    @GetMapping(path="/tutor/{id}")
    public Optional<Tutor> selectTutorById(@PathVariable("id") UUID id){
        return tutorService.selectTutorById(id);
    }


    @DeleteMapping(path="/tutor")
    public int removeTutor(@NotNull @RequestParam(name = "id") UUID tutorId,
                             @NotNull @RequestParam(name = "course") UUID courseId) {
        return tutorService.removeTutor(tutorId, courseId);
    }


}
