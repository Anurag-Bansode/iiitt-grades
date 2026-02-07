package example.example.grading_engine.model.entity;

import example.example.grading_engine.enums.marksvalidation.MarkComponentType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
        name = "final_grade_marks",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"final_grade_id", "mark_type"})
        }
)
public class FinalGradeMarks {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_grade_id", nullable = false)
    private FinalGrade finalGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "mark_type", nullable = false)
    private MarkComponentType markType;

    @Column(name = "marks", precision = 5, scale = 2)
    private BigDecimal marks;

    public FinalGradeMarks() {
    }

    public FinalGradeMarks(
            UUID id,
            FinalGrade finalGrade,
            MarkComponentType markType,
            BigDecimal marks
    ) {
        this.id = id;
        this.finalGrade = finalGrade;
        this.markType = markType;
        this.marks = marks;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FinalGrade getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(FinalGrade finalGrade) {
        this.finalGrade = finalGrade;
    }

    public MarkComponentType getMarkType() {
        return markType;
    }

    public void setMarkType(MarkComponentType markType) {
        this.markType = markType;
    }

    public BigDecimal getMarks() {
        return marks;
    }

    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "FinalGradeMarks{" +
                "id=" + id +
                ", markType=" + markType +
                ", marks=" + marks +
                '}';
    }

    public static class Builder {
        private UUID id;
        private FinalGrade finalGrade;
        private MarkComponentType markType;
        private BigDecimal marks;

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder finalGrade(FinalGrade finalGrade) {
            this.finalGrade = finalGrade;
            return this;
        }

        public Builder markType(MarkComponentType markType) {
            this.markType = markType;
            return this;
        }

        public Builder marks(BigDecimal marks) {
            this.marks = marks;
            return this;
        }

        public FinalGradeMarks build() {
            return new FinalGradeMarks(
                    id,
                    finalGrade,
                    markType,
                    marks
            );
        }
    }
}
