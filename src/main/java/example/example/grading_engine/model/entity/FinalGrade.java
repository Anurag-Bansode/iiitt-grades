package example.example.grading_engine.model.entity;


import example.example.grading_engine.enums.grading.GradeLetter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "final_grades",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"enrollment_id", "submission_id"})
        }
)
public class FinalGrade {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enrollment_id", nullable = false)
    private StudentEnrollment enrollment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "submission_id", nullable = false)
    private GradeSubmission submission;

    @Enumerated(EnumType.STRING)
    @Column(name = "grade_letter", nullable = false)
    private GradeLetter gradeLetter;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public FinalGrade() {
    }

    public FinalGrade(
            UUID id,
            StudentEnrollment enrollment,
            GradeSubmission submission,
            GradeLetter gradeLetter,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.enrollment = enrollment;
        this.submission = submission;
        this.gradeLetter = gradeLetter;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public StudentEnrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(StudentEnrollment enrollment) {
        this.enrollment = enrollment;
    }

    public GradeSubmission getSubmission() {
        return submission;
    }

    public void setSubmission(GradeSubmission submission) {
        this.submission = submission;
    }

    public GradeLetter getGradeLetter() {
        return gradeLetter;
    }

    public void setGradeLetter(GradeLetter gradeLetter) {
        this.gradeLetter = gradeLetter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FinalGrade that = (FinalGrade) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "FinalGrade{" +
                "id=" + id +
                ", gradeLetter=" + gradeLetter +
                ", createdAt=" + createdAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private StudentEnrollment enrollment;
        private GradeSubmission submission;
        private GradeLetter gradeLetter;
        private LocalDateTime createdAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder enrollment(StudentEnrollment enrollment) {
            this.enrollment = enrollment;
            return this;
        }

        public Builder submission(GradeSubmission submission) {
            this.submission = submission;
            return this;
        }

        public Builder gradeLetter(GradeLetter gradeLetter) {
            this.gradeLetter = gradeLetter;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public FinalGrade build() {
            return new FinalGrade(
                    id,
                    enrollment,
                    submission,
                    gradeLetter,
                    createdAt
            );
        }
    }
}
