-- sql
-- Flyway migration: `V2.05.07__add_class_code_and_update_create_student_user.sql`
-- 1) Add class_code column to students
ALTER TABLE students
    ADD COLUMN IF NOT EXISTS class_code TEXT;

-- 2) Replace the create_student_user function to accept class_code and insert it into students
CREATE OR REPLACE FUNCTION create_student_user(
    p_full_name   TEXT,
    p_email       TEXT,
    p_roll_number TEXT,
    p_class_code  TEXT DEFAULT NULL
) RETURNS UUID AS $$
DECLARE
    v_user_id UUID;
BEGIN
    -- Create user
    INSERT INTO users (
        full_name,
        email,
        role,
        auth_provider,
        is_active,
        academic_status
    )
    VALUES (
        p_full_name,
        p_email,
        'STUDENT',
        'GOOGLE',
        true,
        'ACTIVE'
    )
    RETURNING id INTO v_user_id;

    -- Create student row including class_code
    INSERT INTO students (id, roll_number, class_code)
    VALUES (v_user_id, p_roll_number, p_class_code);

    -- Return created user id
    RETURN v_user_id;

EXCEPTION
    WHEN unique_violation THEN
        RAISE EXCEPTION
            'Duplicate email or roll number (email=%, roll=%)',
            p_email, p_roll_number;
END;
$$ LANGUAGE plpgsql;