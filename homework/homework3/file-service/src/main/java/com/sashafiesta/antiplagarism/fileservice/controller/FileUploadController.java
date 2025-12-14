package com.sashafiesta.antiplagiarism.fileservice.controller;

import com.sashafiesta.antiplagiarism.fileservice.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("student_name") String studentName, @RequestParam("task_id") String taskId) {
        UUID submissionId = fileStorageService.storeFile(file, studentName, taskId);
        return ResponseEntity.ok("File uploaded. Beware... ID: " + submissionId);
    }
}
