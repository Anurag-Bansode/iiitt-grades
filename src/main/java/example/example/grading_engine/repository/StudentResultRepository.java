package example.example.grading_engine.repository;

import example.example.grading_engine.model.entity.FinalGrade;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentResultRepository extends JpaRepository<FinalGrade, Long> {

    @Query(value = """
        SELECT st.name, sub.subject_code, sub.subject_name, fg.grade
        FROM student st
        JOIN final_grade fg ON st.id = fg.student_id
        JOIN subject sub ON fg.subject_id = sub.id
        WHERE st.roll_number = :rollNo
    """, nativeQuery = true)
    List<Object[]> getStudentResults(@Param("rollNo") String rollNo);
}