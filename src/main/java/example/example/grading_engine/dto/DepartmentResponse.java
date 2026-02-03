package example.example.grading_engine.dto;

import java.util.UUID;

public record DepartmentResponse(
        UUID uuid,
        String code,
        String name
) {}