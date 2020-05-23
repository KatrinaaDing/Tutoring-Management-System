package unswstudyclub.api;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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


}
