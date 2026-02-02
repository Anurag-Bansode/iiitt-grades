package example.example.grading_engine.controller;

import example.example.grading_engine.dto.StudentCsvRow;
import example.example.grading_engine.service.impl.StudentImportService;
import example.example.grading_engine.util.CsvStudentParser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('FACULTY','HOD')")
@RequestMapping("/api/faculty/internal")
public class StudentCsvController {

    private final StudentImportService importService;
    private final CsvStudentParser parser;

    public StudentCsvController(
            StudentImportService importService,
            CsvStudentParser parser
    ) {
        this.importService = importService;
        this.parser = parser;
    }

    @PostMapping(value = "/students/csv", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadCsv(
            @RequestParam("file") MultipartFile file
    ) {
        List<StudentCsvRow> rows = parser.parse(file);
        importService.importStudents(rows);
        return ResponseEntity.ok("CSV imported successfully");
    }
}