package example.example.grading_engine.controller;

import example.example.grading_engine.dto.StudentResult;
import example.example.grading_engine.service.impl.StudentResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentViewController {

    @Autowired
    private StudentResultService studentResultService;

    @GetMapping("/view")
    public ResponseEntity<String> view() {
        return ResponseEntity.ok("Student access granted");
    }

    @GetMapping("/result/{rollNo}")
    public StudentResult getResult(@PathVariable String rollNo) {
        return studentResultService.getResult(rollNo);
    }
}