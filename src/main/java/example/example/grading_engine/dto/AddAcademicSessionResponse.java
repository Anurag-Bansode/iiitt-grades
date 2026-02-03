package example.example.grading_engine.dto;

import java.util.UUID;

public record AddAcademicSessionResponse(

        UUID id,
        String academicYear,
        Integer semester,
        String regulationVersion,
        String gradingPolicyVersion
) {
}
