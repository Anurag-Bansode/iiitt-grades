package example.example.grading_engine.service.impl;

import example.example.grading_engine.dto.AddAcademicSessionRequest;
import example.example.grading_engine.dto.AddAcademicSessionResponse;
import example.example.grading_engine.model.entity.AcademicSession;
import example.example.grading_engine.repository.AcademicRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AcademicService {

    private final AcademicRepository academicRepository;

    public AcademicService(AcademicRepository academicRepository) {
        this.academicRepository = academicRepository;
    }


    public AddAcademicSessionResponse addAcademicSession(AddAcademicSessionRequest request) {

        if(academicRepository.existsByAcademicYearAndSemester(
                request.academicYear(), request.semester())) {
            throw new IllegalArgumentException("Academic session already exists");
        }

        AcademicSession academicSession = AcademicSession.builder()
                .id(UUID.randomUUID())
                .academicYear(request.academicYear())
                .semester(request.semester())
                .regulationVersion(request.regulationVersion())
                .gradingPolicyVer(request.gradingPolicyVersion())
                .build();

        AcademicSession saved = academicRepository.save(academicSession);

        return new AddAcademicSessionResponse(
                saved.getId(),
                saved.getAcademicYear(),
                saved.getSemester(),
                saved.getRegulationVersion(),
                saved.getGradingPolicyVer()
        );
    }

}
