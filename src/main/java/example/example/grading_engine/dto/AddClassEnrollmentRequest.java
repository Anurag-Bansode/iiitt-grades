package example.example.grading_engine.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class AddClassEnrollmentRequest {

        private String classCode;
        private UUID subjectId;
        private UUID sessionId;

        public String getClassCode() {
            return classCode;
        }

        public void setClassCode(String classCode) {
            this.classCode = classCode;
        }

        public UUID getSubjectId() {
            return subjectId;
        }

        public void setSubjectId(UUID subjectId) {
            this.subjectId = subjectId;
        }

        public UUID getSessionId() {
            return sessionId;
        }

        public void setSessionId(UUID sessionId) {
            this.sessionId = sessionId;
        }
    }