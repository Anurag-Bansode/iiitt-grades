
package example.example.grading_engine.dto;

import example.example.grading_engine.enums.marksvalidation.MarkComponentType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record SubjectMarks_GradingResponse(
        List<StudentInitialGrade> students,
        BigDecimal meanTotal,
        BigDecimal standardDeviationTotal
) {
    public static class StudentInitialGrade {

        private final UUID studentId;
        private final String rollNumber;
        private final Map<MarkComponentType, BigDecimal> marksByType;
        private final BigDecimal total;
        private final BigDecimal average;
        private String grade;

        public StudentInitialGrade(
                UUID studentId,
                String rollNumber,
                Map<MarkComponentType, BigDecimal> marksByType,
                BigDecimal total,
                BigDecimal average
        ) {
            this.studentId = studentId;
            this.rollNumber = rollNumber;
            this.marksByType = marksByType;
            this.total = total;
            this.average = average;
        }

        public UUID studentId() { return studentId; }
        public String rollNumber() { return rollNumber; }
        public Map<MarkComponentType, BigDecimal> marksByType() { return marksByType; }
        public BigDecimal total() { return total; }
        public BigDecimal average() { return average; }
        public String grade() { return grade; }

        public void assignGrade(String grade) {
            this.grade = grade;
        }
    }
}
