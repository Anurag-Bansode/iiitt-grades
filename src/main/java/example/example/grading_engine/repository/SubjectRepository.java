package example.example.grading_engine.repository;

import example.example.grading_engine.model.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {
    Optional<Subject> findBySubjectNameAndSubjectCode(
            String subjectName,
            String subjectCode
    );
}