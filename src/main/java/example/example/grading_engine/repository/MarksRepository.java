package example.example.grading_engine.repository;

import example.example.grading_engine.enums.marksvalidation.MarkComponentType;
import example.example.grading_engine.model.entity.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface MarksRepository extends JpaRepository<Mark, UUID> {
    List<Mark> findByEnrollment_Student_ClassCodeAndEnrollment_Subject_SubjectCodeAndMarksTypeIn(
            String classCode,
            String subjectCode,
            Collection<MarkComponentType> markTypes
    );
}
