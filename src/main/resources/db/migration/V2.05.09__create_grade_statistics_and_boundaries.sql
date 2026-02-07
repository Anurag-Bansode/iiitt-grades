-- =========================
-- Grade statistics
-- =========================
CREATE TABLE grade_statistics (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    mean NUMERIC(10,4) NOT NULL,
    std_deviation NUMERIC(10,4) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =========================
-- Grade boundaries (S → F)
-- =========================
CREATE TABLE grade_boundaries (
                                  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                  statistics_id UUID NOT NULL
                                      REFERENCES grade_statistics(id)
                                          ON DELETE CASCADE,

                                  grade_letter grade_letter NOT NULL,

                                  min_score NUMERIC(5,2) NOT NULL,
                                  max_score NUMERIC(5,2) NOT NULL,

                                  CONSTRAINT uq_statistics_grade_letter
                                      UNIQUE (statistics_id, grade_letter),

                                  CHECK (min_score <= max_score)
);