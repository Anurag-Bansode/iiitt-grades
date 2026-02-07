package example.example.grading_engine.policy.impl;

import example.example.grading_engine.dto.SubjectMarks_GradingResponse;
import example.example.grading_engine.enums.grading.GradeLetter;
import example.example.grading_engine.policy.GradingPolicy;
import example.example.grading_engine.policy.PolicyContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

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

        // Pre-compute grade boundaries and map to store grades
        Map<GradeLetter, BigDecimal> gradeBoundaries = new LinkedHashMap<>();

        BigDecimal sCutoff = mean.add(std.multiply(BigDecimal.valueOf(1.5)));
        BigDecimal aCutoff = mean.add(std.multiply(BigDecimal.valueOf(0.5)));
        BigDecimal bCutoff = mean.subtract(std.multiply(BigDecimal.valueOf(0.5)));
        BigDecimal cCutoff = mean.subtract(std.multiply(BigDecimal.valueOf(1.5)));
        BigDecimal dCutoff = mean.subtract(std.multiply(BigDecimal.valueOf(2.0)));
        BigDecimal eCutoff = mean.subtract(std.multiply(BigDecimal.valueOf(2.5)));;

        gradeBoundaries.put(GradeLetter.S, sCutoff);
        gradeBoundaries.put(GradeLetter.A, aCutoff);
        gradeBoundaries.put(GradeLetter.B, bCutoff);
        gradeBoundaries.put(GradeLetter.C, cCutoff);
        gradeBoundaries.put(GradeLetter.D, dCutoff);
        gradeBoundaries.put(GradeLetter.E, eCutoff);
        gradeBoundaries.put(GradeLetter.F, passCutoff);

        Map<java.util.UUID, GradeLetter> studentGrades = new java.util.HashMap<>();

        for (var student : ctx.getStudents()) {
            BigDecimal total = student.getTotal();

            GradeLetter grade;
            if (total.compareTo(passCutoff) < 0) {
                grade = GradeLetter.F;
            } else if (total.compareTo(sCutoff) >= 0) {
                grade = GradeLetter.S;
            } else if (total.compareTo(aCutoff) >= 0) {
                grade = GradeLetter.A;
            } else if (total.compareTo(bCutoff) >= 0) {
                grade = GradeLetter.B;
            } else if (total.compareTo(cCutoff) >= 0) {
                grade = GradeLetter.C;
            } else if (total.compareTo(dCutoff) >= 0) {
                grade = GradeLetter.D;
            } else if (total.compareTo(eCutoff) >= 0) {
                grade = GradeLetter.E;
            } else {
                grade = GradeLetter.F;
            }

            student.setGrade(grade);
            studentGrades.put(student.getStudentId(), grade);
        }

        return new SubjectMarks_GradingResponse(
                ctx.getStudents(),
                mean,
                std,
                gradeBoundaries
        );
    }
}
