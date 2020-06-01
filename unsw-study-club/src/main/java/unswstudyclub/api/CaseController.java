package unswstudyclub.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Case;
import unswstudyclub.service.AdminService;
import unswstudyclub.service.CaseService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("api/v6")
@RestController
public class CaseController {

    private final CaseService caseService;

    @Autowired
    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @PostMapping(path="/case")
    public int addCase(@Valid @NonNull @RequestBody Case c) {
        return caseService.addCase(c);
    }

    @GetMapping(path="/case")
    public List<Case> getAllCases(){
        return caseService.getAllCases();
    }

    @GetMapping(path="/case/id/{id}")
    public Case getCaseById(@PathVariable("id") UUID id){
        return caseService.getCaseById(id).orElse(null);
    }

    @GetMapping(path="/case/title/{title}")
    public Case getCaseByTitle(@PathVariable("title") String title){
        return caseService.getCaseByTitle(title).orElse(null);
    }

}
