package unswstudyclub.api;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping(path="login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping(path="courses")
    public String getCourses() {
        return "courses";
    }

    @GetMapping(path="case/{title}")
    public String getCourse(@PathVariable("title") String title, Model model) {
        model.addAttribute("title", title);
        return "case";
    }

}
