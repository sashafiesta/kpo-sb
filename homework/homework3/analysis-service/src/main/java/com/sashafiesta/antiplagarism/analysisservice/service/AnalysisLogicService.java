package com.sashafiesta.antiplagiarism.analysisservice.service;

import com.sashafiesta.antiplagiarism.analysisservice.dto.AnalysisRequest;
import com.sashafiesta.antiplagiarism.analysisservice.entity.Fingerprint;
import com.sashafiesta.antiplagiarism.analysisservice.entity.Report;
import com.sashafiesta.antiplagiarism.analysisservice.repository.FingerprintRepository;
import com.sashafiesta.antiplagiarism.analysisservice.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnalysisLogicService {

    private final FingerprintRepository fingerprintRepository;
    private final ReportRepository reportRepository;

    @Transactional
    public void performAnalysis(AnalysisRequest request) {
        Optional<Fingerprint> existingWork = fingerprintRepository
                .findByTaskIdAndFileHashAndStudentNameNot(request.getTaskId(), request.getFileHash(), request.getStudentName());
        boolean isPlagiarism = existingWork.isPresent();
        String verdict = isPlagiarism ? "Plagiarism detected. Identical to work by: " + existingWork.get().getStudentName() + ". Throw them to the pool with sharks! :3" : "No plagiarism detected. This student is a lucky survivor.";
        Report report = Report.builder()
                .submissionId(request.getSubmissionId()).isPlagiarized(isPlagiarism)
                .verdictMessage(verdict).createdAt(LocalDateTime.now())
                .build();
        reportRepository.save(report);
        Fingerprint fingerprint = Fingerprint.builder()
                .submissionId(request.getSubmissionId()).studentName(request.getStudentName())
                .taskId(request.getTaskId()).fileHash(request.getFileHash())
                .build();
        fingerprintRepository.save(fingerprint);
    }
}
