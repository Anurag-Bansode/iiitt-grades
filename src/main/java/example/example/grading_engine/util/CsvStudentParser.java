package example.example.grading_engine.util;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.databind.MappingIterator;
import example.example.grading_engine.dto.StudentCsvRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CsvStudentParser {

    public List<StudentCsvRow> parse(MultipartFile file) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema schema = CsvSchema.emptySchema().withHeader();

            MappingIterator<StudentCsvRow> iterator =
                    mapper
                            .readerFor(StudentCsvRow.class)
                            .with(schema)
                            .readValues(file.getInputStream());

            List<StudentCsvRow> rows = iterator.readAll();

            validate(rows);
            return rows;

        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid CSV file", e);
        }
    }

    private void validate(List<StudentCsvRow> rows) {
        Set<String> emails = new HashSet<>();
        Set<String> rolls = new HashSet<>();

        for (StudentCsvRow r : rows) {
            if (!emails.add(r.email())) {
                throw new IllegalArgumentException(
                        "Duplicate email in CSV: " + r.email()
                );
            }
            if (!rolls.add(r.rollNumber())) {
                throw new IllegalArgumentException(
                        "Duplicate roll number in CSV: " + r.rollNumber()
                );
            }
        }
    }
}