package example.example.grading_engine.service.impl;

import example.example.grading_engine.dto.*;
import example.example.grading_engine.repository.StudentResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentResultService {

    @Autowired
    private StudentResultRepository repository;

    public StudentResult getResult(String rollNo) {

        List<Object[]> data = repository.getStudentResults(rollNo);

        if (data == null || data.isEmpty()) {
            throw new RuntimeException("No result found for roll number: " + rollNo);
        }

        StudentResult result = new StudentResult();

        // ✅ convert String → int (DO NOT change DTO)
        result.setRollNumber(Integer.parseInt(rollNo));
        result.setSubjects(new ArrayList<>());

        for (Object[] row : data) {

            result.setName((String) row[0]);

            SubjectResult sub = new SubjectResult();
            sub.setSubjectCode((String) row[1]);
            sub.setSubjectName((String) row[2]);
            sub.setGrade((String) row[3]);

            result.getSubjects().add(sub);
        }

        return result;
    }
}