package example.example.grading_engine.controller;

import example.example.grading_engine.dto.StudentMarksPerSubjectDTO;
import example.example.grading_engine.service.impl.SubjectMarksQueryService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('HOD','FACULTY')")
@RequestMapping("/api/grades/get-marks-per-subject")
public class GradeController {

    private final SubjectMarksQueryService subjectMarksQueryService;

    public GradeController(SubjectMarksQueryService subjectMarksQueryService) {
        this.subjectMarksQueryService = subjectMarksQueryService;
    }

    @GetMapping("/ping")
    public String ping() {
        return "Login Successful";
    }

    @GetMapping
    public List<StudentMarksPerSubjectDTO> getClassMarks(
            @RequestParam("subjectCode") String subjectCode,
            @RequestParam("academicYear") String academicYear,
            @RequestParam("semester") Integer semester) {


        return subjectMarksQueryService.getClassMarksForSubject(subjectCode, semester, academicYear);
    }
}
