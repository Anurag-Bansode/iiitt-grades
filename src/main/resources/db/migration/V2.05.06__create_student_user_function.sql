CREATE OR REPLACE FUNCTION create_student_user(
    p_full_name   TEXT,
    p_email       TEXT,
    p_roll_number TEXT
) RETURNS UUID AS $$
DECLARE
    v_user_id UUID;
BEGIN
    -- 1. Create user
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

    -- 2. Create student row
    INSERT INTO students (id, roll_number)
    VALUES (v_user_id, p_roll_number);

    -- 3. Return created user id
    RETURN v_user_id;

EXCEPTION
    WHEN unique_violation THEN
        RAISE EXCEPTION
            'Duplicate email or roll number (email=%, roll=%)',
            p_email, p_roll_number;
END;
$$ LANGUAGE plpgsql;