package example.example.grading_engine.controller;

import example.example.grading_engine.dto.CreateDepartmentRequest;
import example.example.grading_engine.dto.CreateDepartmentResponse;
import example.example.grading_engine.service.impl.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hod/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping("/addDepartment")
    public ResponseEntity<CreateDepartmentResponse> createDepartment(
            @Valid @RequestBody CreateDepartmentRequest request
    ) {
        CreateDepartmentResponse response = departmentService.createDepartment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}