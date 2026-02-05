package example.example.grading_engine.policy;

import example.example.grading_engine.dto.SubjectMarks_GradingResponse;

import java.math.BigDecimal;
import java.util.List;

public class PolicyContext {

    private final String classCode;
    private final String subjectCode;

    private final List<SubjectMarks_GradingResponse.StudentInitialGrade> students;

    private final BigDecimal mean;
    private final BigDecimal stddev;

    public PolicyContext(
            String classCode,
            String subjectCode,
            List<SubjectMarks_GradingResponse.StudentInitialGrade> students,
            BigDecimal mean,
            BigDecimal stddev
    ) {
        this.classCode = classCode;
        this.subjectCode = subjectCode;
        this.students = students;
        this.mean = mean;
        this.stddev = stddev;
    }

    public String getClassCode() { return classCode; }
    public String getSubjectCode() { return subjectCode; }
    public List<SubjectMarks_GradingResponse.StudentInitialGrade> getStudents() { return students; }
    public BigDecimal getMean() { return mean; }
    public BigDecimal getStddev() { return stddev; }
}