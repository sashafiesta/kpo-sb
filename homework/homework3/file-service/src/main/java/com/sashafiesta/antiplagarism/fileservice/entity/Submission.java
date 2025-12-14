package com.sashafiesta.antiplagiarism.fileservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "file_metadata")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    @Id
    private UUID id;
    private String studentName;
    private String taskId;
    private String filePath;
    private LocalDateTime uploadDate;
    private String fileHash;
}
