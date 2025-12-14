package com.sashafiesta.antiplagiarism.analysisservice.repository;

import com.sashafiesta.antiplagiarism.analysisservice.entity.Fingerprint;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FingerprintRepository extends JpaRepository<Fingerprint, Long> {
    Optional<Fingerprint> findByTaskIdAndFileHashAndStudentNameNot(String taskId, String fileHash, String studentName);
}
