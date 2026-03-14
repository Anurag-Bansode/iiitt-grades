package example.example.grading_engine.service.impl;

import example.example.grading_engine.dto.CreateSubjectRequest;
import example.example.grading_engine.dto.CreateSubjectResponse;
import example.example.grading_engine.model.entity.Department;
import example.example.grading_engine.model.entity.Subject;
import example.example.grading_engine.repository.DepartmentRepository;
import example.example.grading_engine.repository.SubjectRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    public SubjectService(
            SubjectRepository subjectRepository,
            DepartmentRepository departmentRepository
    ) {
        this.subjectRepository = subjectRepository;
        this.departmentRepository = departmentRepository;
    }

    public CreateSubjectResponse createSubject(CreateSubjectRequest request) {

        Department department = departmentRepository
                .findByCode(request.departmentCode())
                .orElseThrow(() ->
                        new IllegalArgumentException("Department not found"));

        Subject subject = Subject.builder()
                .id(UUID.randomUUID())
                .department(department)
                .subjectCode(request.subjectCode())
                .subjectName(request.subjectName())
                .credits(request.credits())
                .build();

        Subject saved = subjectRepository.save(subject);

        return new CreateSubjectResponse(
                saved.getId(),
                saved.getSubjectCode(),
                saved.getSubjectName(),
                saved.getCredits()
        );
    }
}