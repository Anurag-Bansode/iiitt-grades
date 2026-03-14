package example.example.grading_engine.service.impl;

import example.example.grading_engine.model.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EnrollmentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void enrollStudents(String classCode, UUID subjectId, UUID sessionId) {

        Subject subject = entityManager.find(Subject.class, subjectId);
        AcademicSession academicSession = entityManager.find(AcademicSession.class, sessionId);

        List<Student> students = entityManager
                .createQuery("FROM Student WHERE classCode = :code", Student.class)
                .setParameter("code", classCode)
                .getResultList();

        for (Student student : students) {

            StudentEnrollment enrollment = new StudentEnrollment();

            enrollment.setId(UUID.randomUUID());
            enrollment.setStudent(student);
            enrollment.setSubject(subject);
            enrollment.setSession(academicSession);

            entityManager.persist(enrollment);
        }
    }
}