package com.sashafiesta.antiplagiarism.analysisservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class AnalysisRequest {
    private UUID submissionId;
    private String studentName;
    private String taskId;
    private String fileHash;
}
