package example.example.grading_engine.dto;

import example.example.grading_engine.enums.marksvalidation.MarkComponentType;

import java.util.List;

public record SubjectMarks_GradingRequest(
        String classCode,
        String subjectCode,
        List<MarkComponentType> markTypes
) {
}
