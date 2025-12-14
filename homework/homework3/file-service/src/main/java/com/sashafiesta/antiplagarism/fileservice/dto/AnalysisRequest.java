package com.sashafiesta.antiplagiarism.fileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class AnalysisRequest {
    private UUID submissionId;
    private String studentName;
    private String taskId;
    private String fileHash;
}
