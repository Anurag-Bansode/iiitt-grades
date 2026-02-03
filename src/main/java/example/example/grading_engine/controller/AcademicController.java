package example.example.grading_engine.controller;


import example.example.grading_engine.dto.AddAcademicSessionRequest;
import example.example.grading_engine.dto.AddAcademicSessionResponse;
import example.example.grading_engine.service.impl.AcademicService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hod/academic")
public class AcademicController {

    private final AcademicService academicService;

    public AcademicController(AcademicService academicService) {
        this.academicService = academicService;
    }

    @PostMapping("/addAcademicSession")
    public ResponseEntity<AddAcademicSessionResponse> addAcademicSession(
            @Valid @RequestBody AddAcademicSessionRequest request
    ){
        AddAcademicSessionResponse response = academicService.addAcademicSession(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
