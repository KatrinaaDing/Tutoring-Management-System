package unswstudyclub.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Person;
import unswstudyclub.service.PersonService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v6")
@RestController
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(path="/person")
    public int addPerson(@Valid @NonNull @RequestBody Person person){
        return personService.addPerson(person);
    }

    @GetMapping(path="/person")
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }

    @GetMapping(path="/person/{id}")
    public Person getPersonById(@NotNull  @PathVariable("id") UUID id) {
        return personService.getPersonById(id)
                .orElse(null);
    }

    @DeleteMapping(path="/person/{id}")
    public void deletePersonById(@PathVariable("id") UUID id) {
        personService.deletePersonById(id);
    }

    @PutMapping(path="/person/{id}")
    public void updatePersonById(@PathVariable("id") UUID id, @Valid @NonNull @RequestBody Person newPerson) {
        personService.updatePersonById(id, newPerson);
    }

}
