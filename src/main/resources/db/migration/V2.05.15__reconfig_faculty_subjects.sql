-- Drop old composite primary key
ALTER TABLE faculty_subjects
DROP CONSTRAINT faculty_subjects_pkey;

-- Make sure UUID generator exists
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Add new id column
ALTER TABLE faculty_subjects
    ADD COLUMN id UUID DEFAULT gen_random_uuid();

-- Make id primary key
ALTER TABLE faculty_subjects
    ADD PRIMARY KEY (id);