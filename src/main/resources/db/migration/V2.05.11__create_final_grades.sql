CREATE TABLE final_grades (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

    enrollment_id UUID NOT NULL
        REFERENCES student_enrollments(id),

    submission_id UUID NOT NULL
        REFERENCES grade_submissions(id),

    grade_letter grade_letter NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT NOW(),

    CONSTRAINT uq_final_grade_per_student
        UNIQUE (enrollment_id, submission_id)
);