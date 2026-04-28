package example.example.grading_engine.controller;

import example.example.grading_engine.dto.SubjectMarks_GradingRequest;
import example.example.grading_engine.dto.SubjectMarks_GradingResponse;
import example.example.grading_engine.dto.MarksUpdateRequest;
import example.example.grading_engine.model.entity.Mark;
import example.example.grading_engine.service.impl.MarksService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faculty/marks")
public class MarksController {

    private final MarksService marksService;

    public MarksController(MarksService marksService) {
        this.marksService = marksService;
    }

    // ✅ 1. VIEW MARKS (NEW - IMPORTANT)
    @GetMapping("/view")
    public ResponseEntity<List<Mark>> getMarks(
            @RequestParam String classCode,
            @RequestParam String subjectCode
    ) {
        List<Mark> marks = marksService.getMarks(classCode, subjectCode);
        return ResponseEntity.ok(marks);
    }

    // ✅ 2. GET GRADING (EXISTING - KEEP SAME)
    @PostMapping("/getGrading")
    public ResponseEntity<SubjectMarks_GradingResponse> getGrading(
            @Valid @RequestBody SubjectMarks_GradingRequest request
    ) {
        SubjectMarks_GradingResponse response = marksService.getGrading(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // ✅ 3. UPDATE MARKS (EXISTING - KEEP SAME)
    @PutMapping("/update")
    public ResponseEntity<Mark> updateMarks(
            @Valid @RequestBody MarksUpdateRequest request
    ) {
        Mark updatedMark = marksService.updateMarks(request);
        return ResponseEntity.ok(updatedMark);
    }
}