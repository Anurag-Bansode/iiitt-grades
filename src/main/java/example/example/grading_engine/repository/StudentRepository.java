package example.example.grading_engine.repository;

import example.example.grading_engine.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentRepository
        extends JpaRepository<Student, UUID> {

    Optional<Student> findByRollNumber(String rollNo);
}