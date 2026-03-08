package example.example.grading_engine.controller;
import example.example.grading_engine.dto.CreateFacultyAssignmentRequest;
import example.example.grading_engine.service.impl.FacultyAssignmentService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hod")
public class HodFacultyController {

    private final FacultyAssignmentService facultyAssignmentService;

    public HodFacultyController(FacultyAssignmentService facultyAssignmentService) {
        this.facultyAssignmentService = facultyAssignmentService;
    }

    @PostMapping("/assign-faculty")
    public String assignFaculty(
            @RequestBody CreateFacultyAssignmentRequest request,
            @RequestHeader("X-USER-ID") UUID hodId) {

        facultyAssignmentService.createAssignment(request, hodId);

        return "Faculty assigned successfully";
    }
}
