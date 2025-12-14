package com.sashafiesta.antiplagiarism.analysisservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "submission_fingerprint")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fingerprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private UUID submissionId;
    private String studentName;
    private String taskId;
    private String fileHash;
}
