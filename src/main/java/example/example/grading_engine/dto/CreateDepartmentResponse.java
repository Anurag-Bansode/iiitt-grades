package example.example.grading_engine.dto;

import java.util.UUID;

public record CreateDepartmentResponse(
        UUID id,
        String code,
        String name
) {}