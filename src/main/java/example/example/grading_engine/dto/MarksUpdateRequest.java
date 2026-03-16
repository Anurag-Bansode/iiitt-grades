package example.example.grading_engine.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record MarksUpdateRequest(
        @NotNull UUID markId,
        @NotNull BigDecimal marks
) {}