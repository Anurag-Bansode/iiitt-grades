package example.example.grading_engine.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/teacher")
public class TeacherAuthController {

//    private final TeacherRegistrationService service;
//
//    public TeacherAuthController(TeacherRegistrationService service) {
//        this.service = service;
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerTeacher(
//            @Valid @RequestBody TeacherRegisterRequest request) {
//
//        service.registerTeacher(request);
//        return ResponseEntity.ok("Registration submitted. Await HOD approval.");
//    }
}