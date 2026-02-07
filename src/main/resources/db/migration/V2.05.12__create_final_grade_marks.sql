CREATE TABLE final_grade_marks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    final_grade_id UUID NOT NULL
        REFERENCES final_grades(id)
        ON DELETE CASCADE,

    mark_type mark_component_type NOT NULL,
    marks NUMERIC CHECK (marks BETWEEN 0 AND 100),

    CONSTRAINT uq_final_grade_mark_type
        UNIQUE (final_grade_id, mark_type)
);