package example.example.grading_engine.repository;

import example.example.grading_engine.model.entity.AcademicSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AcademicRepository extends JpaRepository<AcademicSession, UUID> {

    boolean existsByAcademicYearAndSemester(String academicYear, Integer semester);

    Optional<AcademicSession> findByAcademicYearAndSemester(String academicYear, Integer semester);
}