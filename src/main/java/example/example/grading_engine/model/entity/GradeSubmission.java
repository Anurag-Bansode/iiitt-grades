package example.example.grading_engine.model.entity;


import example.example.grading_engine.enums.workflowapproval.SubmissionStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "grade_submissions")
public class GradeSubmission {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "statistics_id", nullable = false)
    private GradeStatistics statistics;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SubmissionStatus status;

    @Column(name = "policy_version", nullable = false)
    private String policyVersion;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

    @Column(name = "locked_at")
    private LocalDateTime lockedAt;

    @Column(name = "last_edited_at", nullable = false)
    private LocalDateTime lastEditedAt;

    public GradeSubmission() {
    }

    public GradeSubmission(
            UUID id,
            GradeStatistics statistics,
            SubmissionStatus status,
            String policyVersion,
            LocalDateTime submittedAt,
            LocalDateTime lockedAt,
            LocalDateTime lastEditedAt
    ) {
        this.id = id;
        this.statistics = statistics;
        this.status = status;
        this.policyVersion = policyVersion;
        this.submittedAt = submittedAt;
        this.lockedAt = lockedAt;
        this.lastEditedAt = lastEditedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public GradeStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(GradeStatistics statistics) {
        this.statistics = statistics;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public void setStatus(SubmissionStatus status) {
        this.status = status;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(LocalDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    public LocalDateTime getLastEditedAt() {
        return lastEditedAt;
    }

    public void setLastEditedAt(LocalDateTime lastEditedAt) {
        this.lastEditedAt = lastEditedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeSubmission that = (GradeSubmission) o;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "GradeSubmission{" +
                "id=" + id +
                ", status=" + status +
                ", policyVersion='" + policyVersion + '\'' +
                ", submittedAt=" + submittedAt +
                ", lockedAt=" + lockedAt +
                ", lastEditedAt=" + lastEditedAt +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private GradeStatistics statistics;
        private SubmissionStatus status;
        private String policyVersion;
        private LocalDateTime submittedAt;
        private LocalDateTime lockedAt;
        private LocalDateTime lastEditedAt;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder statistics(GradeStatistics statistics) {
            this.statistics = statistics;
            return this;
        }

        public Builder status(SubmissionStatus status) {
            this.status = status;
            return this;
        }

        public Builder policyVersion(String policyVersion) {
            this.policyVersion = policyVersion;
            return this;
        }

        public Builder submittedAt(LocalDateTime submittedAt) {
            this.submittedAt = submittedAt;
            return this;
        }

        public Builder lockedAt(LocalDateTime lockedAt) {
            this.lockedAt = lockedAt;
            return this;
        }

        public Builder lastEditedAt(LocalDateTime lastEditedAt) {
            this.lastEditedAt = lastEditedAt;
            return this;
        }

        public GradeSubmission build() {
            return new GradeSubmission(
                    id,
                    statistics,
                    status,
                    policyVersion,
                    submittedAt,
                    lockedAt,
                    lastEditedAt
            );
        }
    }
}
