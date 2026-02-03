package example.example.grading_engine.service.impl;

import example.example.grading_engine.dto.CreateDepartmentRequest;
import example.example.grading_engine.dto.DepartmentResponse;
import example.example.grading_engine.model.entity.Department;
import example.example.grading_engine.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DepartmentService  {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {

        if (departmentRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Department with this code already exists");
        }

        Department department = Department.builder()
                .id(UUID.randomUUID())
                .code(request.code().toUpperCase())
                .name(request.name())
                .build();

        Department saved = departmentRepository.save(department);

        return new DepartmentResponse(
                saved.getId(),
                saved.getCode(),
                saved.getName()
        );
    }
}