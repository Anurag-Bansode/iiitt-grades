package example.example.grading_engine.service.impl;

import example.example.grading_engine.dto.StudentCsvRow;
import example.example.grading_engine.repository.StudentCreationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentImportService {

    private final StudentCreationRepository repository;

    public StudentImportService(StudentCreationRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void importStudents(List<StudentCsvRow> rows) {
        for (StudentCsvRow row : rows) {
            repository.createStudent(row);
        }
    }
}