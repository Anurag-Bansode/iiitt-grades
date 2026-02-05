package example.example.grading_engine.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/faculty/marks")
public class MarksController {

    @PostMapping("/getClassMarks")
    public String getClassMarks() {
        return "";
    }

}
