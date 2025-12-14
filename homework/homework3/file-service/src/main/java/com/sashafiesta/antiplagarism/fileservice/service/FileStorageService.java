package com.sashafiesta.antiplagiarism.fileservice.service;

import com.sashafiesta.antiplagiarism.fileservice.dto.AnalysisRequest;
import com.sashafiesta.antiplagiarism.fileservice.entity.Submission;
import com.sashafiesta.antiplagiarism.fileservice.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileStorageService {

    private final SubmissionRepository submissionRepository;
    private final Path fileStorageLocation;
    private final String analysisServiceUrl;
    private final RestTemplate restTemplate;

    public FileStorageService(SubmissionRepository submissionRepository, @Value("${file.upload-dir}") String uploadDir, @Value("${file.analysis-service-url}") String analysisServiceUrl) {
        this.submissionRepository = submissionRepository;
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.analysisServiceUrl = analysisServiceUrl;
        this.restTemplate = new RestTemplate();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create directory", ex);
        }
    }

    public UUID storeFile(MultipartFile file, String studentName, String taskId) {
        UUID submissionId = UUID.randomUUID();
        String fileName = submissionId.toString() + "_" + file.getOriginalFilename();
        try {
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            String fileHash = calculateSHA256(file.getBytes());
            Submission submission = Submission.builder().id(submissionId).studentName(studentName)
                                                        .taskId(taskId).filePath(targetLocation.toString())
                                                        .uploadDate(LocalDateTime.now()).fileHash(fileHash)
                                                        .build();
            submissionRepository.save(submission);
            sendToAnalysis(new AnalysisRequest(submissionId, studentName, taskId, fileHash));
            return submissionId;
        } catch (IOException e) {
            throw new RuntimeException("Could not store", e);
        }
    }

    private String calculateSHA256(byte[] data) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    sb.append('0');
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not found... bruh what kind of JVM are we running on???", e);
        }
    }

    private void sendToAnalysis(AnalysisRequest request) {
        try {
            restTemplate.postForLocation(analysisServiceUrl, request);
        } catch (Exception e) {
            System.err.println("Analysis Service unreachable, we made a fu..nky-wacky! Stupid error:" + e.getMessage());
        }
    }
}