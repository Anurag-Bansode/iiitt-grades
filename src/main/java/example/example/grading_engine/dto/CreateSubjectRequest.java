package example.example.grading_engine.dto;

public record CreateSubjectRequest(
        String departmentCode,
        String subjectCode,
        String subjectName,
        Integer credits

) {
}