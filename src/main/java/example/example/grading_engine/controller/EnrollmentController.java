package example.example.grading_engine.controller;

import example.example.grading_engine.dto.AddClassEnrollmentRequest;
import example.example.grading_engine.service.impl.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/hod/subject_enrollments")
@PreAuthorize("hasRole('HOD')")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/assign")
    public ResponseEntity<String> enrollStudents(@RequestBody AddClassEnrollmentRequest request) {

        enrollmentService.enrollStudents(
                request.getClassCode(),
                request.getSubjectId(),
                request.getSessionId()
        );

        return ResponseEntity.ok("Students enrolled successfully");
    }
}