CREATE TABLE grade_submissions (
                                   id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                                   statistics_id UUID NOT NULL
                                       REFERENCES grade_statistics(id),

                                   status submission_status NOT NULL,

                                   policy_version TEXT NOT NULL,

                                   submitted_at TIMESTAMP,
                                   locked_at TIMESTAMP,
                                   last_edited_at TIMESTAMP NOT NULL DEFAULT NOW()
);