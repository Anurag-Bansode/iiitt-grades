package example.example.grading_engine.policy;

import example.example.grading_engine.dto.SubjectMarks_GradingResponse;

public interface GradingPolicy {

    String policyVersion();

    SubjectMarks_GradingResponse apply(PolicyContext context);
}