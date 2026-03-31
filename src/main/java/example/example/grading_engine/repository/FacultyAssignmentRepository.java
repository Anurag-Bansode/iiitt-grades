package example.example.grading_engine.repository;

import example.example.grading_engine.model.entity.FacultyAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface FacultyAssignmentRepository
        extends JpaRepository<FacultyAssignment, UUID> {

    boolean existsByFacultyIdAndSubjectIdAndAcademicYearAndSemesterAndClassCodeAndIsActiveTrue(
            UUID facultyId,
            UUID subjectId,
            String academicYear,
            Integer semester,
            String classCode
    );
}