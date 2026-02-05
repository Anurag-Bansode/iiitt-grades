package example.example.grading_engine.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDepartmentRequest(
        @NotBlank String code,
        @NotBlank String name
) {}