package com.sashafiesta.antiplagiarism.analysisservice.controller;

import com.sashafiesta.antiplagiarism.analysisservice.entity.Report;
import com.sashafiesta.antiplagiarism.analysisservice.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;

    @GetMapping("/{submissionId}")
    public ResponseEntity<Report> getReport(@PathVariable UUID submissionId) {
        return reportRepository.findById(submissionId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
