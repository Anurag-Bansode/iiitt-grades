package example.example.grading_engine.dto;

import java.util.UUID;

public record CreateSubjectResponse(
        UUID id,
        String subjectCode,
        String subjectName,
        Integer credits
) {}