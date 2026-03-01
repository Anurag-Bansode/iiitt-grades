package example.example.grading_engine.model.entity;

import example.example.grading_engine.enums.academicstructure.SemesterType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="faculty_subjects")
public class FacultyAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "faculty_id", nullable = false)
    private UUID facultyId;

    @Column(name = "subject_id", nullable = false)
    private UUID subjectId;

    @Column(name = "academic_year", nullable = false)
    private String academicYear;

    @Column(nullable = false)
    private Integer semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester_type", nullable = false)
    private SemesterType semesterType;

    @Column(name = "class_code", nullable = false)
    private String classCode;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt = LocalDateTime.now();

    public FacultyAssignment() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(UUID facultyId) {
        this.facultyId = facultyId;
    }

    public UUID getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(UUID subjectId) {
        this.subjectId = subjectId;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public SemesterType getSemesterType(){
        return semesterType;
    }

    public void setSemesterType(SemesterType semesterType) {
        this.semesterType = semesterType;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(LocalDateTime assignedAt) {
        this.assignedAt = assignedAt;
    }
}