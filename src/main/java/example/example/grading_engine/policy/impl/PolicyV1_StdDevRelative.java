package example.example.grading_engine.policy.impl;

import example.example.grading_engine.dto.SubjectMarks_GradingResponse;
import example.example.grading_engine.policy.GradingPolicy;
import example.example.grading_engine.policy.PolicyContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PolicyV1_StdDevRelative implements GradingPolicy {

    @Override
    public String policyVersion() {
        return "V1_STDDEV";
    }

    @Override
    public SubjectMarks_GradingResponse apply(PolicyContext ctx) {

        BigDecimal mean = ctx.getMean();
        BigDecimal std = ctx.getStddev();

        // ---- PASS CUTOFF ----
        BigDecimal min35 = BigDecimal.valueOf(35);
        BigDecimal meanHalf = mean.divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
        BigDecimal maxBy3 = BigDecimal.valueOf(100).divide(BigDecimal.valueOf(3), 2, RoundingMode.HALF_UP);

        BigDecimal passCutoff = min35.min(meanHalf).min(maxBy3);

        for (var student : ctx.getStudents()) {
            BigDecimal total = student.total();

            if (total.compareTo(passCutoff) < 0) {
                student.assignGrade("F");
                continue;
            }

            // ---- GRADE SLABS ----
            if (total.compareTo(mean.add(std.multiply(BigDecimal.valueOf(1.5)))) >= 0) {
                student.assignGrade("S");
            } else if (total.compareTo(mean.add(std.multiply(BigDecimal.valueOf(0.5)))) >= 0) {
                student.assignGrade("A");
            } else if (total.compareTo(mean.subtract(std.multiply(BigDecimal.valueOf(0.5)))) >= 0) {
                student.assignGrade("B");
            } else if (total.compareTo(mean.subtract(std.multiply(BigDecimal.valueOf(1.5)))) >= 0) {
                student.assignGrade("C");
            } else {
                student.assignGrade("F");
            }
        }

        return new SubjectMarks_GradingResponse(
                ctx.getStudents(),
                mean,
                std
        );
    }
}