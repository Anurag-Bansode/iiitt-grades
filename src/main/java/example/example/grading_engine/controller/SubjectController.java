package example.example.grading_engine.controller;

import example.example.grading_engine.dto.CreateSubjectRequest;
import example.example.grading_engine.dto.CreateSubjectResponse;
import example.example.grading_engine.service.impl.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasRole('HOD')")
@RequestMapping("/api/hod/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/addSubject")
    public ResponseEntity<CreateSubjectResponse> addSubject(
            @RequestBody CreateSubjectRequest request
    ) {
        return ResponseEntity.ok(subjectService.createSubject(request));
    }
}