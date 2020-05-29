package unswstudyclub.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import unswstudyclub.model.Subtitle;
import unswstudyclub.service.SubtitleService;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v6")
@RestController
public class SubtitleController {

    private final SubtitleService subtitleService;

    @Autowired
    public SubtitleController(SubtitleService subtitleService) {
        this.subtitleService = subtitleService;
    }

    @PostMapping(path="/subtitle")
    public int addSubtitle(@RequestBody Subtitle subtitle) {
        return subtitleService.addSubtitle(subtitle);
    }

    @GetMapping(path="/subtitle/{case}")
    public List<Subtitle> getSubtitleByCase(@PathVariable("case") String caseTitle) {
        return subtitleService.getSubtitleByCase(caseTitle);
    }

    @PutMapping(path="/subtitle/{id}")
    public int updateSubtitleById(@PathVariable("id") UUID id, @RequestBody Subtitle subtitle){
        return subtitleService.updateSubtitleById(id, subtitle);
    }

    @DeleteMapping(path="/subtitle/{id}")
    public int deleteSubtitleById(@PathVariable("id") UUID id){
        return subtitleService.deleteSubtitleById(id);
    }

}
