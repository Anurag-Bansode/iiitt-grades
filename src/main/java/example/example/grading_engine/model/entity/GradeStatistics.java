package example.example.grading_engine.model.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "grade_statistics")
public class GradeStatistics {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(name = "mean", nullable = false, precision = 10, scale = 4)
    private BigDecimal mean;

    @Column(name = "std_deviation", nullable = false, precision = 10, scale = 4)
    private BigDecimal stdDeviation;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public GradeStatistics() {
    }

    public GradeStatistics(
            UUID id,
            LocalDateTime createdAt,
            BigDecimal mean,
            BigDecimal stdDeviation
    ) {
        this.createdAt=createdAt;
        this.id = id;
        this.mean = mean;
        this.stdDeviation = stdDeviation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BigDecimal getMean() {
        return mean;
    }

    public void setMean(BigDecimal mean) {
        this.mean = mean;
    }

    public BigDecimal getStdDeviation() {
        return stdDeviation;
    }

    public void setStdDeviation(BigDecimal stdDeviation) {
        this.stdDeviation = stdDeviation;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GradeStatistics that = (GradeStatistics) o;
        return Objects.equals(id, that.id) && Objects.equals(mean, that.mean) && Objects.equals(stdDeviation, that.stdDeviation);
    }


    @Override
    public String toString() {
        return "GradeStatistics{" +
                "id=" + id +
                ", mean=" + mean +
                ", stdDeviation=" + stdDeviation +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UUID id;
        private BigDecimal mean;
        private BigDecimal stdDeviation;
        private LocalDateTime createdAt;

        public Builder Id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder mean(BigDecimal mean) {
            this.mean = mean;
            return this;
        }

        public Builder stdDeviation(BigDecimal stdDeviation) {
            this.stdDeviation = stdDeviation;
            return this;
        }

        public GradeStatistics build() {
            return new GradeStatistics(
                    id,
                    createdAt,
                    mean,
                    stdDeviation
            );
        }
    }
}
