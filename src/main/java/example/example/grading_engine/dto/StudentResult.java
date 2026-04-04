package example.example.grading_engine.dto;

import java.util.List;

public class StudentResult {

    private String name;
    private int rollNumber;
    private List<SubjectResult> subjects;

    // Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public List<SubjectResult> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectResult> subjects) {
        this.subjects = subjects;
    }
}