package example.example.grading_engine.dto;

public record StudentCsvRow(
        String fullName,
        String email,
        String rollNumber
) {}