ALTER TABLE users
    ADD COLUMN department_id UUID;

ALTER TABLE users
    ADD CONSTRAINT fk_users_department
        FOREIGN KEY (department_id)
            REFERENCES department(id);