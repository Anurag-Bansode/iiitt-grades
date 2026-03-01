package example.example.grading_engine.service.impl;
import example.example.grading_engine.dto.CreateFacultyAssignmentRequest;
import example.example.grading_engine.enums.userauthentication.UserRole;
import example.example.grading_engine.model.entity.FacultyAssignment;
import example.example.grading_engine.repository.FacultyAssignmentRepository;
import example.example.grading_engine.repository.SubjectRepository;
import example.example.grading_engine.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Service
public class FacultyAssignmentService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final FacultyAssignmentRepository facultyAssignmentRepository;

    public FacultyAssignmentService(UserRepository userRepository,
                                    SubjectRepository subjectRepository,
                                    FacultyAssignmentRepository facultyAssignmentRepository) {
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.facultyAssignmentRepository = facultyAssignmentRepository;
    }

    @Transactional
    public void createAssignment(CreateFacultyAssignmentRequest request, UUID hodId) {

        var hod = userRepository.findById(hodId)
                .orElseThrow(() -> new RuntimeException("HOD not found"));

        if (hod.getRole() != UserRole.HOD) {
            throw new RuntimeException("Only HOD can assign subjects");
        }

        var subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        var faculty = userRepository.findById(request.getFacultyId())
                .orElseThrow(() -> new RuntimeException("Faculty not found"));

        // 🔒 Department validation
        if (!subject.getDepartment().getId().equals(hod.getDepartmentId())) {
            throw new RuntimeException("Subject not in your department");
        }

        if (!faculty.getDepartmentId().equals(hod.getDepartmentId())) {
            throw new RuntimeException("Faculty not in your department");
        }

        FacultyAssignment assignment = new FacultyAssignment();
        assignment.setFacultyId(request.getFacultyId());
        assignment.setSubjectId(request.getSubjectId());
        assignment.setAcademicYear(request.getAcademicYear());
        assignment.setSemester(request.getSemester());
        assignment.setSemesterType(request.getSemesterType());
        assignment.setClassCode(request.getClassCode());
        assignment.setIsActive(true);

        facultyAssignmentRepository.save(assignment);
    }
}
