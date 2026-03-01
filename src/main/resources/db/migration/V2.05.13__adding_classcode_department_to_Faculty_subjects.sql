-- Add new columns
ALTER TABLE faculty_subjects
    ADD COLUMN IF NOT EXISTS class_code VARCHAR(20);

ALTER TABLE faculty_subjects
    ADD COLUMN IF NOT EXISTS is_active BOOLEAN DEFAULT true;


-- Make columns NOT NULL (only if already safe)
ALTER TABLE faculty_subjects
    ALTER COLUMN faculty_id SET NOT NULL,
    ALTER COLUMN subject_id SET NOT NULL,
    ALTER COLUMN academic_year SET NOT NULL,
    ALTER COLUMN semester SET NOT NULL,
    ALTER COLUMN semester_type SET NOT NULL,
    ALTER COLUMN class_code SET NOT NULL;


-- Add semester range check
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'check_semester'
    ) THEN
ALTER TABLE faculty_subjects
    ADD CONSTRAINT check_semester
        CHECK (semester BETWEEN 1 AND 8);
END IF;
END$$;


-- Add faculty FK
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'fk_faculty'
    ) THEN
ALTER TABLE faculty_subjects
    ADD CONSTRAINT fk_faculty
        FOREIGN KEY (faculty_id)
            REFERENCES users(id);
END IF;
END$$;


-- Add subject FK
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint
        WHERE conname = 'fk_subject'
    ) THEN
ALTER TABLE faculty_subjects
    ADD CONSTRAINT fk_subject
        FOREIGN KEY (subject_id)
            REFERENCES subjects(id);
END IF;
END$$;


-- Unique index for active assignment
CREATE UNIQUE INDEX IF NOT EXISTS unique_active_assignment
    ON faculty_subjects(subject_id, academic_year, semester, semester_type, class_code)
    WHERE is_active = true;