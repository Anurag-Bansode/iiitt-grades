package example.example.grading_engine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddAcademicSessionRequest(
        @NotBlank String academicYear,
        @NotNull Integer semester,
        @NotBlank String regulationVersion,
        @NotBlank String gradingPolicyVersion
) {}
