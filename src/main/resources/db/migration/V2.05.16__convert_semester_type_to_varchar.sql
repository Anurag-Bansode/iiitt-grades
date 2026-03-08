ALTER TABLE faculty_subjects
ALTER COLUMN semester_type TYPE VARCHAR(10)
    USING semester_type::text;