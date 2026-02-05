package example.example.grading_engine.repository;

import example.example.grading_engine.dto.StudentCsvRow;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class StudentCreationRepository {

    private final JdbcTemplate jdbcTemplate;

    public StudentCreationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public UUID createStudent(StudentCsvRow row) {
        return jdbcTemplate.queryForObject(
                "SELECT create_student_user(?, ?, ?, ?)",
                UUID.class,
                row.fullName(),
                row.email(),
                row.rollNumber(),
                row.classCode()
        );
    }
}