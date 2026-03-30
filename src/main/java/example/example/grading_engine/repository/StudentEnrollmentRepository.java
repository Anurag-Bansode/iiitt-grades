package example.example.grading_engine.repository;

import example.example.grading_engine.model.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentEnrollmentRepository
        extends JpaRepository<StudentEnrollment, UUID> {

    Optional<StudentEnrollment>
    findByStudentIdAndSubjectIdAndSessionId(
            UUID studentId,
            UUID subjectId,
            UUID sessionId
    );
}
