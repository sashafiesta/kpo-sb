package com.sashafiesta.antiplagiarism.fileservice.repository;

import com.sashafiesta.antiplagiarism.fileservice.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, UUID> {
}
