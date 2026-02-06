package example.example.grading_engine.service.impl;

import example.example.grading_engine.dto.SubjectMarks_GradingRequest;
import example.example.grading_engine.dto.SubjectMarks_GradingResponse;
import example.example.grading_engine.enums.marksvalidation.MarkComponentType;
import example.example.grading_engine.model.entity.Mark;
import example.example.grading_engine.policy.GradingPolicy;
import example.example.grading_engine.policy.PolicyContext;
import example.example.grading_engine.policy.PolicyRegistry;
import example.example.grading_engine.repository.MarksRepository;
import example.example.grading_engine.util.MarksCalculationUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MarksService {

    private final MarksRepository marksRepository;
    private final PolicyRegistry policyRegistry;

    public MarksService(MarksRepository marksRepository, PolicyRegistry policyRegistry) {
        this.marksRepository = marksRepository;
        this.policyRegistry = policyRegistry;
    }

    public SubjectMarks_GradingResponse getGrading(@Valid SubjectMarks_GradingRequest request) {

        List<MarkComponentType> types = request.markTypes();

        if (types == null || types.isEmpty()) {
            types = Arrays.asList(MarkComponentType.values());
        }

        List<Mark> marks = marksRepository.findByEnrollment_Student_ClassCodeAndEnrollment_Subject_SubjectCodeAndMarksTypeIn(
                request.classCode(),
                request.subjectCode(),
                types
        );

//        Mock data (uncomment to test without DB)
//        List<Mark> marks = new ArrayList<>();
//
//        for (int i = 1; i <= 5; i++) {
//            Mark internal = new Mark();
//            internal.setId(UUID.randomUUID());
//            internal.setMarksType(MarkComponentType.INTERNAL);
//            internal.setMarks(BigDecimal.valueOf(20 + i));
//
//            Mark endsem = new Mark();
//            endsem.setId(UUID.randomUUID());
//            endsem.setMarksType(MarkComponentType.CT1);
//            endsem.setMarks(BigDecimal.valueOf(50 + i));
//
//            // enrollment + nested objects
//            Student student = new example.example.grading_engine.model.entity.Student();
//            student.setId(UUID.randomUUID());
//            student.setRollNumber("CS22B10" + i);
//
//            StudentEnrollment enrollment = new StudentEnrollment();
//            enrollment.setStudent(student);
//
//            AcademicSession session = new AcademicSession();
//            session.setGradingPolicyVer("V1_STDDEV");
//
//            enrollment.setSession(session);
//
//            internal.setEnrollment(enrollment);
//            endsem.setEnrollment(enrollment);
//
//            marks.add(internal);
//            marks.add(endsem);
//        }

        Map<UUID, StudentBucket> byStudent = new LinkedHashMap<>();
        for (Mark m : marks) {
            if (m == null || m.getEnrollment() == null || m.getEnrollment().getStudent() == null) continue;
            UUID studentId = m.getEnrollment().getStudent().getId();
            String roll = m.getEnrollment().getStudent().getRollNumber();
            String policy = m.getEnrollment().getSession().getGradingPolicyVer();
            // policy version captured once (same for all students in this request)

            StudentBucket bucket = byStudent.computeIfAbsent(studentId, id -> new StudentBucket(id, roll));
            bucket.marks.put(m.getMarksType(), m.getMarks());
        }

        List<SubjectMarks_GradingResponse.StudentInitialGrade> entries = new ArrayList<>();
        List<BigDecimal> totals = new ArrayList<>();
        for (StudentBucket sb : byStudent.values()) {
            EnumMap<MarkComponentType, BigDecimal> complete =
                    MarksCalculationUtils.buildCompleteMarks(sb.marks, types);

            BigDecimal total = MarksCalculationUtils.calculateTotal(complete);
            BigDecimal average = MarksCalculationUtils.calculateAverage(total, complete.size());

            totals.add(total);

            entries.add(new SubjectMarks_GradingResponse.StudentInitialGrade(
                    sb.studentId,
                    sb.rollNumber,
                    Map.copyOf(complete),
                    total,
                    average
            ));
        }

        BigDecimal mean = MarksCalculationUtils.calculateMean(totals);
        BigDecimal stddev = MarksCalculationUtils.calculateStdDev(totals, mean);


        // Resolve grading policy (assumes same policy for the entire class & subject)
        String policyVersion = marks.isEmpty()
                ? null
                : marks.getFirst().getEnrollment().getSession().getGradingPolicyVer();

        if (policyVersion == null) {
            throw new IllegalStateException("No grading policy version found for this request");
        }

        GradingPolicy policy = policyRegistry.get(policyVersion);

        PolicyContext context = new PolicyContext(
                request.classCode(),
                request.subjectCode(),
                entries,
                mean,
                stddev
        );

        return policy.apply(context);
    }

    // simple bucket class used while building the response
    private static class StudentBucket {
        final UUID studentId;
        final String rollNumber;
        final EnumMap<MarkComponentType, BigDecimal> marks = new EnumMap<>(MarkComponentType.class);

        StudentBucket(UUID studentId, String rollNumber) {
            this.studentId = studentId;
            this.rollNumber = rollNumber;
        }
    }

}
